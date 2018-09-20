package solidityunit.runner;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import solidityunit.annotations.Account;
import solidityunit.annotations.Contract;
import solidityunit.annotations.Safe;
import solidityunit.annotations.SolidityConfig;
import solidityunit.constants.Config;
import solidityunit.internal.sorter.SafeMethodSorter;
import solidityunit.internal.utilities.PropertiesReader;

public class SolidityUnitRunner extends BlockJUnit4ClassRunner {
	
	/** Logger instance */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	//propriedades carregadas do arquivo de properties
	Properties testProperties;
	
	//instancias do web3j
	Web3j web3j;
	Admin web3Admin;
	
	//credencial da conta de testes
	Credentials mainAccountCredentials;
	
	//contas cadastradas no properties
	Map<String,Credentials> accounts;
	
	//contratos feitos deploy (para reutilizar)
	Map<Class, String> contractsAddress;
	
	//controla se o @Before ja rodou ao menos uma vez
	boolean firstBeforeExecution;
	
	public SolidityUnitRunner(Class<?> klass) throws InitializationError {
		super(klass);
		
		try {
			this.firstBeforeExecution = true;
			this.accounts = new HashMap<>();
			this.contractsAddress = new HashMap<>();
			
			this.testProperties = new PropertiesReader().loadProperties("solidity-unit.properties");
			
			//parse das contas no properties
			for (Object o: this.testProperties.keySet()) {
				if ( o.toString().startsWith("account.") && o.toString().endsWith(".id") ) {
					String [] vet = o.toString().split("\\.");
					//identificador da conta
					String identificador = vet[1];
					
					String id = this.testProperties.getProperty( String.format("account.%s.id", identificador) );
					String pk = this.testProperties.getProperty( String.format("account.%s.privatekey", identificador) );
					
					accounts.put(identificador, Credentials.create(pk));
				}
			}
			
		} catch (IOException e) {
			 throw new InitializationError(new IOException("Erro ao ler arquivo de properties", e));
		}
		
		this.createWeb3Instance();
		
	}
	
    private void doInjections( Object testObject ) throws IllegalArgumentException, IllegalAccessException {
    	
    	Field [] fields = testObject.getClass().getDeclaredFields();
    	
    	for (Field f: fields) {
    		if ( f.isAnnotationPresent(Inject.class) ) {
    			f.setAccessible(true);
    			
    			//pontos de injecao
    			if ( f.getType().equals(Web3j.class)  ) {
    				f.set(testObject, web3j);
    				log.debug("Injeção de Web3j efetuada");
    			}
    			else if ( f.getType().equals(Admin.class)  ) {
    				f.set(testObject, web3Admin);
    				log.debug("Injeção de Web3Admin efetuada");
    			}
    		}
    		
    		//configs
    		if ( f.isAnnotationPresent(SolidityConfig.class) ) {
    			f.setAccessible(true);
    			
    			SolidityConfig config = f.getAnnotation(SolidityConfig.class);
    			
    			//dependendo do tipo da config, injeta um objeto diferente
    			if ( Config.MAIN_ACCOUNT_ID.equals(config.value()) && f.getType().equals(String.class) ) {
    				f.set( testObject, testProperties.getProperty(Config.MAIN_ACCOUNT_ID) );
    				log.debug("Injeção de Main Account ID efetuada");
    			}
    			else if ( Config.MAIN_ACCOUNT_PRIVATE_KEY.equals(config.value()) && f.getType().equals(String.class) ) {
    				f.set( testObject, testProperties.getProperty(Config.MAIN_ACCOUNT_PRIVATE_KEY) );
    				log.debug("Injeção de Main Account Private Key efetuada");
    			}
    			
    			//credentials
    			else if ( Config.MAIN_ACCOUNT_CREDENTIALS.equals(config.value()) && f.getType().equals(Credentials.class) ) {
    				f.set( testObject, this.mainAccountCredentials );
    				log.debug("Injeção de Main Account Credentials efetuada");
    			}
    			
    			//default accounts
    			else if ( Config.DEFAULT_ACCOUNTS.equals(config.value()) && f.getType().equals(List.class)  ) {
    				try {
						PersonalListAccounts pla = web3Admin.personalListAccounts().send();
						f.set( testObject, pla.getAccountIds() );
						log.debug("Injeção de Default Accounts efetuada");
					} catch (IOException e) {
						throw new IllegalArgumentException("Erro ao injetar DEFAULT ACCOUNTS", e);
					}
    			}
    			
    		}
    		
    		//accounts
    		if ( f.isAnnotationPresent(Account.class) ) {
    			f.setAccessible(true);
    			
    			Account account = f.getAnnotation(Account.class);
    			String id = account.id();
    			
    			if( f.getType().equals(Credentials.class) ) { 
    				f.set(testObject, this.accounts.get(id));
    			}
    			
    			
    		}
    	}
    	
    }
    
    /**
     * Este método faz a injeção dos contratos <br>
     * 
     * Caso o metodo seja @Safe, é reutilizado o deploy (load ao inves de deploy)
     * 
     * @param testObject
     * @param actualMethod
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void doContractInjections( Object testObject, FrameworkMethod actualMethod ) throws IllegalArgumentException, IllegalAccessException {
    	Field [] fields = testObject.getClass().getDeclaredFields();
    	
    	for (Field f: fields) {
    		if ( f.isAnnotationPresent(Contract.class) ) {
    			f.setAccessible(true);
    			
    			try {
    				
    				//tipo de classe do contrato
    				Class contractClass = f.getType();
    				
    				//busca o endereço do contrato
					String address = this.contractsAddress.get(contractClass);
					
    				//se nao for safe OU nao tem endereço (primeiro deploy)
					Safe safe = actualMethod.getAnnotation(Safe.class);
					
					//entra se: for SAFE mas address é null / nao for SAFE
    				if ((safe != null && address == null || safe == null ) ) {
    					//metodo estático de deploy do contrato
    					Method m = contractClass.getMethod("deploy", Web3j.class, Credentials.class, BigInteger.class, BigInteger.class);
						Object remoteCall = m.invoke(null, this.web3j, this.mainAccountCredentials, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
						
						//chama o metodo send() para criar a instancia do contrato
						if( remoteCall instanceof RemoteCall ) {
							Object instance = ((RemoteCall)remoteCall).send();
							f.set(testObject, instance);
							
							//guarda o endereço do contrato (para possivel reaproveitamento depois)
							Method mAddr = contractClass.getMethod("getContractAddress");
							address = (String) mAddr.invoke(instance);
							this.contractsAddress.put(contractClass, address);
							
							log.info( String.format("Deploy do Contrato [%s] efetuado", contractClass.getSimpleName() ) );
						}
						
    				}
    				else {
    					//metodo estático de carregar contrato com deploy já efetuado
    					Method m = contractClass.getMethod("load", String.class, Web3j.class, Credentials.class, BigInteger.class, BigInteger.class);
						Object instance = m.invoke(null, address, this.web3j, this.mainAccountCredentials, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
						f.set(testObject, instance);
						
						log.info( String.format("Contrato [%s] reaproveitado", contractClass.getSimpleName() ) );
    				}
					
					
				} catch (Exception e) {
					throw new IllegalArgumentException("Erro ao efetuar deploy do Contrato", e);
				}
    		}
    		
    	}
    }
    
    private void createWeb3Instance() {
    	this.web3Admin = Admin.build(new HttpService(this.testProperties.getProperty(Config.WEB3_HOST))); 
    	this.web3j = Web3j.build(new HttpService(this.testProperties.getProperty(Config.WEB3_HOST)));
    	this.mainAccountCredentials = Credentials.create((this.testProperties.getProperty(Config.MAIN_ACCOUNT_PRIVATE_KEY)));
    }
    
    //***************************************************************
    //
    //   overrides do junit
    //
    //***************************************************************
    
    public Object createTest(FrameworkMethod method) throws Exception {
        Object obj = super.createTest();
        
        //injeção aqui
        this.doInjections(obj);
        
        //passa o metodo (se for safe, reutiliza deploy)
        this.doContractInjections(obj, method);
        
        return obj;
    }
    
    /**
     * Returns the methods that run tests. Default implementation returns all
     * methods annotated with {@code @Test} 
     */
    @Override
    protected List<FrameworkMethod> computeTestMethods() {
    	
    	List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(Test.class);
    	
    	//troca a lista (pq o junit vem 'unmodifiable list' (WHY MR ANDERSON?)
    	List<FrameworkMethod> newList = new ArrayList<>();
    	methods.forEach(newList::add);
    	
    	//ordena pela anotação "safe"
    	Collections.sort(newList, new SafeMethodSorter());
    	
    	//na malandragem, ordenou pelos @Safe antes
        return newList;
    }
    
    @Override
    protected Statement methodBlock(FrameworkMethod method) {
        Object test;
        try {
            test = new ReflectiveCallable() {
                @Override
                protected Object runReflectiveCall() throws Throwable {
                    return createTest( method );
                }
            }.run();
        } catch (Throwable e) {
            return new Fail(e);
        }

        Statement statement = methodInvoker(method, test);
        statement = possiblyExpectingExceptions(method, test, statement);
        statement = withPotentialTimeout(method, test, statement);
        
        //somente roda o @Before caso nao tenha @Safe OU se nunca rodou
        Safe safe = method.getAnnotation(Safe.class);
        if (safe == null || this.firstBeforeExecution ) {
        	statement = withBefores(method, test, statement);
        	this.firstBeforeExecution = false;
        }
        		
        statement = withAfters(method, test, statement);
        statement = withRules(method, test, statement);
        return statement;
    }
    
    
    private Statement withRules(FrameworkMethod method, Object target, Statement statement) {
        List<TestRule> testRules = getTestRules(target);
        Statement result = statement;
        result = withMethodRules(method, testRules, target, result);
        result = withTestRules(method, testRules, result);

        return result;
    }
    
    private Statement withMethodRules(FrameworkMethod method, List<TestRule> testRules, Object target, Statement result) {
        for (org.junit.rules.MethodRule each : getMethodRules(target)) {
            if (!testRules.contains(each)) {
                result = each.apply(result, method, target);
            }
        }
        return result;
    }
    
    private Statement withTestRules(FrameworkMethod method, List<TestRule> testRules, Statement statement) {
        return testRules.isEmpty() ? statement :
                new RunRules(statement, testRules, describeChild(method));
    }
    
    private List<org.junit.rules.MethodRule> getMethodRules(Object target) {
        return rules(target);
    }
}
