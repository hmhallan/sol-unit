package test.solidityunit;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import solidityunit.annotations.Account;
import solidityunit.annotations.Contract;
import solidityunit.annotations.Safe;
import solidityunit.runner.SolidityUnitRunner;
import test.solidityunit.entity.Proposta;
import test.solidityunit.factory.TestDemocraciaFactory;
import test.solidityunit.generated.Democracia;

@RunWith(SolidityUnitRunner.class)
public class TestsDemocracia {

	@Contract
	Democracia democracia;

	private static final BigInteger VOTO_FAVOR = new BigInteger("1");
	private static final BigInteger VOTO_CONTRA = new BigInteger("2");

	private static final int PROPOSTA_1 = 0;

	@Before
	public void setUp() throws Exception {
		TestDemocraciaFactory.criarProposta("Proposta de Voto ", "Texto ", new Date(), 100 );
		TestDemocraciaFactory.criarVoto(PROPOSTA_1, VOTO_FAVOR);
		TestDemocraciaFactory.criarVoto(PROPOSTA_1, VOTO_CONTRA);
	}

	@Test
	@Safe
	public void verifica_se_o_total_de_propostas_esta_correto() throws Exception  {
		BigInteger total = this.democracia.getTotaldePropostas().send();
		Assert.assertEquals(1, total.intValue() );
	}
	
	@Test
	@Safe
	public void verifica_cadastro_de_proposta() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(PROPOSTA_1) ).send() );
		Assert.assertNotNull( p );
		Assert.assertEquals("Proposta de Voto", p.getTitulo() );
	}
}
