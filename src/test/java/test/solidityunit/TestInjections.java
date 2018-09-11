package test.solidityunit;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;

import solidityunit.annotations.Contract;
import solidityunit.annotations.SolidityConfig;
import solidityunit.constants.Config;
import solidityunit.runner.SolidityUnitRunner;
import test.solidityunit.generated.Democracia;

@RunWith(SolidityUnitRunner.class)
public class TestInjections {
	
	@Inject
	Web3j web3j;
	
	@Inject
	Admin web3Admin;
	
	@SolidityConfig(Config.DEFAULT_ACCOUNTS)
	List<String> ACCOUNTS;
	
	@SolidityConfig(Config.MAIN_ACCOUNT_CREDENTIALS)
	Credentials MAIN_ACCOUNT_CREDENTIAL;
	
	@SolidityConfig(Config.MAIN_ACCOUNT_ID)
	String MAIN_ACCOUNT;
	
	@SolidityConfig(Config.MAIN_ACCOUNT_PRIVATE_KEY)
	String MAIN_ACCOUNT_PRIVATE_KEY;
	
	@Contract
	Democracia democracia;

	@Test
	public void verifica_se_todas_as_injecoes_ocorreram_com_sucesso() throws Exception  {
		Assert.assertNotNull( this.web3j );
		Assert.assertNotNull( this.web3Admin );
		Assert.assertNotNull( this.ACCOUNTS );
		Assert.assertNotNull( this.MAIN_ACCOUNT_CREDENTIAL );
		Assert.assertNotNull( this.MAIN_ACCOUNT );
		Assert.assertNotNull( this.MAIN_ACCOUNT_PRIVATE_KEY );
		Assert.assertNotNull( this.democracia );
	}
	
}
