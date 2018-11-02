package test.solidityunit;


import java.math.BigInteger;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import test.solidityunit.entity.Proposta;
import test.solidityunit.generated.Democracia;

public class TestContratoDemocracia {

	Web3j web3j; 
	Credentials mainAccountCredentials; 

	Democracia democracia;

	@Before
	public void setUp() throws Exception  {
		web3j = Web3j.build(new HttpService("http://localhost:7545")); 
		mainAccountCredentials = Credentials.create("abcd1234"); 
		
		democracia = Democracia.deploy(web3j, mainAccountCredentials, DefaultGasProvider.GAS_PRICE, 
										DefaultGasProvider.GAS_LIMIT ).send(); 
		
		democracia.criarProposta("Proposta de Voto", "Texto da Proposta", 
					BigInteger.valueOf(new Date().getTime()), BigInteger.valueOf(100) ).send();
		
		democracia.votar(BigInteger.valueOf(1), BigInteger.valueOf(1)).send();
	}
	
	@Test
	public void verifica_proposta_cadastrada() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(1) ).send() );
		Assert.assertNotNull( p );
		Assert.assertEquals("Proposta de Voto", p.getTitulo() );
	}
	
	@Test
	public void verifica_voto_cadastrado() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(1) ).send() );
		Assert.assertNotNull( p );
		Assert.assertEquals( 1, p.getTotalVotos() );
	}

}



