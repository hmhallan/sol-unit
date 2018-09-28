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
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import solidityunit.annotations.Account;
import solidityunit.annotations.Contract;
import solidityunit.annotations.Safe;
import solidityunit.constants.Config;
import solidityunit.internal.sorter.SafeMethodSorter;
import solidityunit.internal.utilities.PropertiesReader;

public class SolidityUnitRunner extends BlockJUnit4ClassRunner {
	
	/** Logger instance */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	//properties loaded from file solidity-unit.properties
	Properties testProperties;
	
	//web3j and web3admin instances
	Web3j web3j;
	Admin web3Admin;
	
	//credencial da conta de testes
	Credentials mainAccountCredentials;
	
	//accounts from properties file
	Map<String,Credentials> accounts;
	
	//deployed contracts (for a possible reuse)
	Map<Class<?>, String> contractsAddress;
	
	//control variable from @Before, that needs to run once for @Safe methods
	boolean firstBeforeExecution;
	
	public SolidityUnitRunner(Class<?> klass) throws InitializationError {
		super(klass);
		
		this.firstBeforeExecution = true;
		this.accounts = new HashMap<>();
		this.contractsAddress = new HashMap<>();
		
		try {
			//loads properties from disk
			this.testProperties = new PropertiesReader().loadProperties("solidity-unit.properties");
		} catch (IOException e) {
			 throw new InitializationError(new IOException("Error reading [solidity-unit.properties] file", e));
		}
		
		this.readAccountsFromProperties();
		
		this.createWeb3InstanceFromProperties();
	}
	
	/**
     * Creates a test to be excecuted
     * @param method method annotated with @Test 
     * @return Object that will be execute the test method
     * @throws Exception for some error
     */
    public Object createTest(FrameworkMethod method) throws Exception {
    	//stantard creation from JUnit
        Object obj = super.createTest();
        
        //Inject test dependencies on test object
        this.doInjections(obj);
        
        //Inject contracts (if it's @Safe and is not the first run, load, otherwise do a new deploy )
        this.doContractInjections(obj, method);
        
        return obj;
    }
	
    /**
     * Injects accounts and web3j/web3admin dependencies on the Test Object 
     * @param testObject object that will be execute the test method
     * @throws IllegalArgumentException for some error
     * @throws IllegalAccessException for some error
     */
    private void doInjections( Object testObject ) throws IllegalArgumentException, IllegalAccessException {
    	
    	Field [] fields = testObject.getClass().getDeclaredFields();
    	
    	for (Field f: fields) {
    		
    		//inject web3 and web3admin if needed
    		if ( f.isAnnotationPresent(Inject.class) ) {
    			this.injectWeb3(f, testObject);
    		}
    		
    		//inject accounts if needed
    		if ( f.isAnnotationPresent(Account.class) ) {
    			this.injectAccounts(f, testObject);
    		}
    	}
    	
    }
    
    /**
     * Inject contracts in the Test Object <br>
     * Contracts are identified by the annotation @Contract
     * 
     * For @Safe methods, the contract is loaded <br>
     * Otherwise, it's a new deploy for each test method
     * 
     * @param testObject object that will be execute the test method
     * @param actualMethod object representing the actual test method
     * @throws IllegalArgumentException for some error
     * @throws IllegalAccessException for some error
     */
    private void doContractInjections( Object testObject, FrameworkMethod actualMethod ) throws IllegalArgumentException, IllegalAccessException {
    	Field [] fields = testObject.getClass().getDeclaredFields();
    	
    	for (Field f: fields) {
    		if ( f.isAnnotationPresent(Contract.class) ) {
    			this.deployOrLoadContract(f, testObject, actualMethod);
    		}
    	}
    }
    
    /**
     * Method that create instances for Web3j, Web3admin and main account credentials, <br>
     * based on properties file
     */
    private void createWeb3InstanceFromProperties() {
    	this.web3Admin = Admin.build(new HttpService(this.testProperties.getProperty(Config.WEB3_HOST))); 
    	this.web3j = Web3j.build(new HttpService(this.testProperties.getProperty(Config.WEB3_HOST)));
    	this.mainAccountCredentials = Credentials.create((this.testProperties.getProperty(Config.MAIN_ACCOUNT_PRIVATE_KEY)));
    }
    
    /**
     * Reads all accounts listed on solidity-unit.properties file
     */
    private void readAccountsFromProperties() {
		for (Object o: this.testProperties.keySet()) {
			if ( o.toString().startsWith("account.") && o.toString().endsWith(".id") ) {
				String [] vet = o.toString().split("\\.");
				
				String account = vet[1];
				//String id = this.testProperties.getProperty( String.format("account.%s.id", account) );
				String pk = this.testProperties.getProperty( String.format("account.%s.privatekey", account) );
				accounts.put(account, Credentials.create(pk));
			}
		}
    }
    
    //***************************************************************
    //
    //  injections
    //
    //***************************************************************
    /**
     * Inject web3j and web3admin objects, if the annotation @Inject is present on them
     * @param f field on test object (web3j or web3admin)
     * @param testObject object that will be execute the test method
     * @throws IllegalArgumentException for some error
     * @throws IllegalAccessException for some error
     */ 
    private void injectWeb3( Field f, Object testObject ) throws IllegalArgumentException, IllegalAccessException {
		f.setAccessible(true);
		
		//injection points
		if ( f.getType().equals(Web3j.class)  ) {
			f.set(testObject, web3j);
			log.debug("Injected Web3j");
		}
		else if ( f.getType().equals(Admin.class)  ) {
			f.set(testObject, web3Admin);
			log.debug("Injected Web3Admin");
		}
    }
    
    private void injectAccounts( Field f, Object testObject ) throws IllegalArgumentException, IllegalAccessException {
			f.setAccessible(true);
			
			Account account = f.getAnnotation(Account.class);
			String id = account.id();
			
			if( f.getType().equals(Credentials.class) ) { 
				f.set(testObject, this.accounts.get(id));
			}
    }
    
    //***************************************************************
    //
    //  contracts
    //
    //***************************************************************
    /**
     * Inject a contract into a field, deploying a new ou loading an existing
     * @param f field on the test object that will be injected
     * @param testObject object that will be execute the test method
     * @param actualMethod object representing the actual test method
     */
    private void deployOrLoadContract( Field f, Object testObject, FrameworkMethod actualMethod) {
    	f.setAccessible(true);
		try {
			Class<?> contractClass = f.getType();
			String address = this.contractsAddress.get(contractClass);
			
			
			if ( this.needsToDeployNewContract(actualMethod, address) ) {
				this.deployNewContract(contractClass, testObject, f);
			}
			else {
				this.loadExistingContract(address, contractClass, testObject, f);
			}
			
		} catch (Exception e) {
			throw new IllegalArgumentException("Errt injecting Contract", e);
		}
    }
    
    /**
     * Verify if can reuse a contract or not <br>
     * If the method is @Safe AND a contract was already deployed, then load. <br>
     * Otherwise, do a new deploy
     * @param actualMethod object representing the actual test method 
     * @param address address for an existing contract (if there is one)
     * @return true if needs a new deploy, false if can reuse a existing contract
     */
    private boolean needsToDeployNewContract(FrameworkMethod actualMethod, String address ) {
    	Safe safe = actualMethod.getAnnotation(Safe.class);
    	return (safe != null && address == null || safe == null );
    }
    
    private void deployNewContract(Class contractClass, Object testObject, Field f) throws Exception {
    	//call the static method 'deploy' that prepares a new contract deploy
		Method m = contractClass.getMethod("deploy", Web3j.class, Credentials.class, BigInteger.class, BigInteger.class);
		Object remoteCall = m.invoke(null, this.web3j, this.mainAccountCredentials, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
		
		//calls the 'send' method to create a contract instance
		if( remoteCall instanceof RemoteCall ) {
			Object instance = ((RemoteCall)remoteCall).send();
			f.set(testObject, instance);
			
			//keep the address for a possible future reuse, calling the method 'getContractAddress'
			Method mAddr = contractClass.getMethod("getContractAddress");
			String address = (String) mAddr.invoke(instance);
			this.contractsAddress.put(contractClass, address);
			
			log.info( String.format("Contract deployment: [%s]", contractClass.getSimpleName() ) );
		}
    }
    
    private void loadExistingContract(String address, Class contractClass, Object testObject, Field f) throws Exception {
    	//calls the static 'load' method that load a contract by it's address
		Method m = contractClass.getMethod("load", String.class, Web3j.class, Credentials.class, BigInteger.class, BigInteger.class);
		Object instance = m.invoke(null, address, this.web3j, this.mainAccountCredentials, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
		f.set(testObject, instance);
		
		log.info( String.format("Contract loaded: [%s]", contractClass.getSimpleName() ) );
    }
    
    
    //***************************************************************
    //
    //  Fixture rules
    //
    //***************************************************************
    /**
     * Verify if can reuse a @Before fixture or not <br>
     * If the method is @Safe AND is NOT a first execution, then can reuse <br>
     * Otherwise, do a new @Before execution
     * @param actualMethod object representing the actual test method 
     * @return true if needs to run @Before, false if can skip 
     */
    private boolean needsToRunBeforeFixture(FrameworkMethod actualMethod) {
    	Safe safe = actualMethod.getAnnotation(Safe.class);
        return (safe == null || this.firstBeforeExecution );
    }
    
    //***************************************************************
    //
    //  JUnit overrides
    //
    //***************************************************************
    
    /**
     * Returns the methods that run tests. Default implementation returns all
     * methods annotated with {@code @Test} 
     */
    @Override
    protected List<FrameworkMethod> computeTestMethods() {
    	
    	List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(Test.class);
    	
    	//creates a new List (JUnit default is 'unmodifiable list')
    	List<FrameworkMethod> newList = new ArrayList<>();
    	methods.forEach(newList::add);
    	
    	//Order methods to run "@Safe" first
    	Collections.sort(newList, new SafeMethodSorter());
    	
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
        
        //verify the need to run @Before fixture
        if ( this.needsToRunBeforeFixture(method) ) {
        	statement = withBefores(method, test, statement);
        	//if run once, check it
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
