package test.solidityunit;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import test.solidityunit.entity.Proposta;
import test.solidityunit.generated.Democracia;

public class TestDemocracia {
	
	Web3j web3j;
	Admin web3Admin;
	
	List<String> ACCOUNTS;
	String MAIN_ACCOUNT;
	Credentials MAIN_ACCOUNT_CREDENTIAL;
	
	String MAIN_ACCOUNT_PRIVATE_KEY = "29af556b884070fd5227dfda5e84a6d5f2addec908c55c5f38343e931d3594d9";
	
	
	@Before
	public void setUp() throws IOException {
		
		web3Admin = Admin.build(new HttpService("http://localhost:7545")); 
		web3j = Web3j.build(new HttpService("http://localhost:7545"));
		
		PersonalListAccounts pla = web3Admin.personalListAccounts().send();
		ACCOUNTS = pla.getAccountIds();
		if (ACCOUNTS != null && !ACCOUNTS.isEmpty()) {
			MAIN_ACCOUNT = ACCOUNTS.get(0);
			
			//chave privada da carteira
			MAIN_ACCOUNT_CREDENTIAL = Credentials.create(MAIN_ACCOUNT_PRIVATE_KEY);
		}
	}
	
	@Test
	public void teste_transfere_1_ether_da_conta_principal() throws IOException, InterruptedException, ExecutionException, TransactionException {
		
		TransactionReceipt transferReceipt = Transfer.sendFunds(web3j, MAIN_ACCOUNT_CREDENTIAL,	ACCOUNTS.get(1), BigDecimal.valueOf(1), Convert.Unit.ETHER)
														.sendAsync().get();
		
		Assert.assertNotNull( transferReceipt.getTransactionHash() );
	}
	
	@Test
	public void efetua_deploy_do_contrato() throws Exception  {
		Democracia d = Democracia.deploy(web3j, MAIN_ACCOUNT_CREDENTIAL, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT).send();
		Assert.assertNotNull( d );
	}
	
	@Test
	public void cadastra_uma_nova_proposta() throws Exception  {
		Democracia d = Democracia.deploy(web3j, MAIN_ACCOUNT_CREDENTIAL, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT).send();
		Assert.assertNotNull( d );
		
		long timestamp = new Date().getTime();
		RemoteCall<TransactionReceipt> call = d.criarProposta("Proposta de Voto", "Aqui vai o texto da minha proposta JAVA", BigInteger.valueOf(timestamp), BigInteger.valueOf(100) );
		TransactionReceipt receipt = call.send();
		Assert.assertNotNull( receipt );
		
		BigInteger total = d.getTotaldePropostas().send();
		Assert.assertEquals(1, total.intValue() );
		
		Proposta p = new Proposta( d.getProposta( BigInteger.valueOf(0) ).send() );
		Assert.assertNotNull( p );
		Assert.assertEquals("Proposta de Voto", p.getTitulo() );
		
		System.out.println( p );
	}
}
