package solidityunit.runner;

import java.util.List;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Properties;

import javax.inject.Inject;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import solidityunit.annotations.Contract;
import solidityunit.annotations.SolidityConfig;
import solidityunit.constants.Config;
import solidityunit.internal.utilities.PropertiesReader;

//public class SolidityUnitRunner extends CdiRunner {


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

	public SolidityUnitRunner(Class<?> klass) throws InitializationError {
		super(klass);
		
		try {
			this.testProperties = new PropertiesReader().loadProperties("solidity-unit.properties");
			
		} catch (IOException e) {
			 throw new InitializationError(new IOException("Erro ao ler arquivo de properties", e));
		}
		
		this.createWeb3Instance();
		
//		System.setProperty("org.jboss.logging.provider", "slf4j");
//		System.setProperty("org.slf4j.simpleLogger.log.org.jboss.weld", "debug");
	}
	
    @Override
    public Object createTest() throws Exception {
        Object obj = super.createTest();
        
        //injeção na munheca aqui: fazer com weld DEPOIS
        this.doInjections(obj);
        this.doContractInjections(obj);
        
        return obj;
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
    	}
    	
    }
    
    private void doContractInjections( Object testObject ) throws IllegalArgumentException, IllegalAccessException {
    	Field [] fields = testObject.getClass().getDeclaredFields();
    	
    	for (Field f: fields) {
    		if ( f.isAnnotationPresent(Contract.class) ) {
    			f.setAccessible(true);
    			
    			try {
    				
    				//tipo de classe do contrato
    				Class contractClass = f.getType();
					
    				//metodo estático de deploy do contrato
    				Method m = contractClass.getMethod("deploy", Web3j.class, Credentials.class, BigInteger.class, BigInteger.class);
					Object remoteCall = m.invoke(null, this.web3j, this.mainAccountCredentials, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
					
					//chama o metodo send() para criar a instancia do contrato
					if( remoteCall instanceof RemoteCall ) {
						Object instance = ((RemoteCall)remoteCall).send();
						f.set(testObject, instance);
					}
					
					log.info( String.format("Deploy do Contrato [%s] efetuado", contractClass.getSimpleName() ) );
					
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
}
