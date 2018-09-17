package test.solidityunit;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import solidityunit.annotations.Contract;
import solidityunit.annotations.SolidityConfig;
import solidityunit.constants.Config;
import solidityunit.runner.SolidityUnitRunner;
import test.solidityunit.entity.Proposta;
import test.solidityunit.generated.Democracia;

@RunWith(SolidityUnitRunner.class)
public class TestDemocracia {

	@SolidityConfig(Config.MAIN_ACCOUNT_ID)
	String MAIN_ACCOUNT;
	
	@Contract
	Democracia democracia;
	
	private static final int TOTAL_PROPOSTAS = 5;
	
	private static final BigInteger VOTO_FAVOR = new BigInteger("1");
	private static final BigInteger VOTO_CONTRA = new BigInteger("2");
	
	@Before
	public void setUp() throws Exception {
		
		//cria 5 propostas
		int total = TOTAL_PROPOSTAS;
		
		for ( int i = 1; i <= total; i++ ) {
			TransactionReceipt receipt =
					TestPropostaFactory.criarProposta(this.democracia, 
													"Proposta de Voto " + i, 
													"Aqui vai o texto da minha proposta número " + i, 
													new Date(), 
													(100 * i) );
			Assert.assertNotNull( receipt );
		}
		
		TestPropostaFactory.criarVoto(this.democracia, 1, VOTO_FAVOR);
		TestPropostaFactory.criarVoto(this.democracia, 2, VOTO_CONTRA);
	}
	
	
	@Test
	public void verifica_se_o_total_de_propostas_esta_correto() throws Exception  {
		BigInteger total = this.democracia.getTotaldePropostas().send();
		Assert.assertEquals(TOTAL_PROPOSTAS, total.intValue() );
	}
	
	@Test
	public void busca_a_primeira_proposta_cadastrada() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(0) ).send() );
		
		Assert.assertNotNull( p );
		Assert.assertEquals("Proposta de Voto 1", p.getTitulo() );
		Assert.assertEquals("Aqui vai o texto da minha proposta número 1", p.getDescricao() );
		Assert.assertEquals(MAIN_ACCOUNT.toLowerCase(), p.getCriador() );
		Assert.assertEquals(100l, p.getTotalVotos() );
		Assert.assertEquals(0l, p.getVotosFavor() );
		Assert.assertEquals(0l, p.getVotosContra());
		Assert.assertEquals(1, p.getStatus() );
	}
	
	@Test
	public void busca_a_segunda_proposta_cadastrada() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(1) ).send() );
		
		Assert.assertNotNull( p );
		Assert.assertEquals("Proposta de Voto 2", p.getTitulo() );
		Assert.assertEquals("Aqui vai o texto da minha proposta número 2", p.getDescricao() );
		Assert.assertEquals(MAIN_ACCOUNT.toLowerCase(), p.getCriador() );
		Assert.assertEquals(200l, p.getTotalVotos() );
		Assert.assertEquals(0l, p.getVotosFavor() );
		Assert.assertEquals(0l, p.getVotosContra());
		Assert.assertEquals(1, p.getStatus() );
	}
	
	@Test
	public void busca_a_terceira_proposta_cadastrada() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(2) ).send() );
		
		Assert.assertNotNull( p );
		Assert.assertEquals("Proposta de Voto 3", p.getTitulo() );
		Assert.assertEquals("Aqui vai o texto da minha proposta número 3", p.getDescricao() );
		Assert.assertEquals(MAIN_ACCOUNT.toLowerCase(), p.getCriador() );
		Assert.assertEquals(300l, p.getTotalVotos() );
		Assert.assertEquals(0l, p.getVotosFavor() );
		Assert.assertEquals(0l, p.getVotosContra());
		Assert.assertEquals(1, p.getStatus() );
	}
	
	@Test
	public void efetua_um_voto_na_primeira_proposta() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(0) ).send() );
		Assert.assertNotNull( p );
		
		this.democracia.votar(p.getIndex(), VOTO_FAVOR);
	}
	
	@Test
	public void efetua_dois_votos_com_a_mesma_carteira_na_primeira_proposta() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(0) ).send() );
		Assert.assertNotNull( p );
		
		this.democracia.votar(p.getIndex(), VOTO_FAVOR).send();
		this.democracia.votar(p.getIndex(), VOTO_CONTRA).send();
		
		Proposta fim = new Proposta( this.democracia.getProposta( BigInteger.valueOf(0) ).send() );
		Assert.assertEquals(fim.getVotosContra(), 1);
		Assert.assertEquals(fim.getVotosFavor(), 1);
	}
	
	@Test
	public void efetua_dois_votos_com_a_mesma_carteira_em_propostas_diferentes() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(0) ).send() );
		Assert.assertNotNull( p );
		
		this.democracia.votar(p.getIndex(), VOTO_FAVOR).send();
		
		Democracia d = TestPropostaFactory.loadFromAddress( this.democracia, "16ee8e72c7abefc1e30b5e8339abc755e350690f75b96dec75886355f203200c");
		Assert.assertNotNull( d );
		
		d.votar(p.getIndex(), VOTO_CONTRA).send();
		
		Proposta fim = new Proposta( this.democracia.getProposta( BigInteger.valueOf(0) ).send() );
		Assert.assertEquals(fim.getVotosContra(), 1);
		Assert.assertEquals(fim.getVotosFavor(), 1);
	}
}
