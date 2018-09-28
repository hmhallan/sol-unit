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
import solidityunit.runner.SolidityUnitRunner;
import test.solidityunit.entity.Proposta;
import test.solidityunit.factory.TestDemocraciaFactory;
import test.solidityunit.generated.Democracia;

@RunWith(SolidityUnitRunner.class)
public class TestDemocracia {

	@Contract
	Democracia democracia;
	
	@Account(id="main")
	Credentials mainAccount;
	
	@Account(id="1")
	Credentials account1;
	
	@Account(id="2")
	Credentials account2;
	
	@Account(id="3")
	Credentials account3;
	
	@Account(id="4")
	Credentials account4;
	
	private static final int TOTAL_PROPOSTAS = 5;
	
	private static final BigInteger VOTO_FAVOR = new BigInteger("1");
	private static final BigInteger VOTO_CONTRA = new BigInteger("2");
	
	private static final int PROPOSTA_1 = 0;
	private static final int PROPOSTA_2 = 1;
	private static final int PROPOSTA_3 = 2;
	private static final int PROPOSTA_4 = 3;
	private static final int PROPOSTA_5 = 4;
	
	@Before
	public void setUp() throws Exception {
		
		//seta o contrato da conta principal
		TestDemocraciaFactory.setMainAddressContract(this.democracia);
		
		//cria 5 propostas
		int total = TOTAL_PROPOSTAS;
		
		for ( int i = 1; i <= total; i++ ) {
			TransactionReceipt receipt =
					TestDemocraciaFactory.criarProposta("Proposta de Voto " + i, 
														"Aqui vai o texto da minha proposta número " + i, 
														new Date(), 
														(100 * i) );
			Assert.assertNotNull( receipt );
		}
		
		TestDemocraciaFactory.criarVoto(PROPOSTA_3, VOTO_FAVOR);
		TestDemocraciaFactory.criarVoto(PROPOSTA_2, VOTO_CONTRA);
		
		TestDemocraciaFactory.criarVoto(this.account2, PROPOSTA_2, VOTO_CONTRA);
		TestDemocraciaFactory.criarVoto(this.account2, PROPOSTA_3, VOTO_CONTRA);
		
		TestDemocraciaFactory.criarVoto(this.account3, PROPOSTA_2, VOTO_FAVOR);
		
		TestDemocraciaFactory.criarVoto(PROPOSTA_4, VOTO_FAVOR);
		TestDemocraciaFactory.criarVoto(this.account1, PROPOSTA_4, VOTO_FAVOR);
		TestDemocraciaFactory.criarVoto(this.account2, PROPOSTA_4, VOTO_FAVOR);
		TestDemocraciaFactory.criarVoto(this.account3, PROPOSTA_4, VOTO_FAVOR);
		TestDemocraciaFactory.criarVoto(this.account4, PROPOSTA_4, VOTO_CONTRA);
		
		TestDemocraciaFactory.criarVoto(PROPOSTA_5, VOTO_CONTRA);
		TestDemocraciaFactory.criarVoto(this.account1, PROPOSTA_5, VOTO_CONTRA);
		TestDemocraciaFactory.criarVoto(this.account2, PROPOSTA_5, VOTO_CONTRA);
		TestDemocraciaFactory.criarVoto(this.account3, PROPOSTA_5, VOTO_CONTRA);
		TestDemocraciaFactory.criarVoto(this.account4, PROPOSTA_5, VOTO_FAVOR);
		
		//totais
		//proposta 2: 2 contra, 1 favor
		//proposta 3: 1 favor, 1 contra
		//proposta 4: 1 contra, 4 favor
		//proposta 5: 4 contra, 1 favor
	}
	
	
	@Test
	public void verifica_se_o_total_de_propostas_esta_correto() throws Exception  {
		BigInteger total = this.democracia.getTotaldePropostas().send();
		Assert.assertEquals(TOTAL_PROPOSTAS, total.intValue() );
	}
	
	@Test
	public void busca_a_primeira_proposta_cadastrada() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(PROPOSTA_1) ).send() );
		
		Assert.assertNotNull( p );
		Assert.assertEquals("Proposta de Voto 1", p.getTitulo() );
		Assert.assertEquals("Aqui vai o texto da minha proposta número 1", p.getDescricao() );
		Assert.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCriador() );
		Assert.assertEquals(100l, p.getTotalVotos() );
		Assert.assertEquals(0l, p.getVotosFavor() );
		Assert.assertEquals(0l, p.getVotosContra());
		Assert.assertEquals(1, p.getStatus() );
	}
	
	@Test
	public void busca_a_segunda_proposta_cadastrada() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(PROPOSTA_2) ).send() );
		
		Assert.assertNotNull( p );
		Assert.assertEquals("Proposta de Voto 2", p.getTitulo() );
		Assert.assertEquals("Aqui vai o texto da minha proposta número 2", p.getDescricao() );
		Assert.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCriador() );
		Assert.assertEquals(200l, p.getTotalVotos() );
		Assert.assertEquals(1l, p.getVotosFavor() );
		Assert.assertEquals(2l, p.getVotosContra());
		Assert.assertEquals(1, p.getStatus() );
	}
	
	@Test
	public void busca_a_terceira_proposta_cadastrada() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(PROPOSTA_3) ).send() );
		
		Assert.assertNotNull( p );
		Assert.assertEquals("Proposta de Voto 3", p.getTitulo() );
		Assert.assertEquals("Aqui vai o texto da minha proposta número 3", p.getDescricao() );
		Assert.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCriador() );
		Assert.assertEquals(300l, p.getTotalVotos() );
		Assert.assertEquals(1l, p.getVotosFavor() );
		Assert.assertEquals(1l, p.getVotosContra());
		Assert.assertEquals(1, p.getStatus() );
	}
	
	@Test
	public void efetua_um_voto_na_primeira_proposta() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(PROPOSTA_1) ).send() );
		Assert.assertNotNull( p );
		
		this.democracia.votar(p.getIndex(), VOTO_FAVOR);
	}
	
	@Test
	public void efetua_dois_votos_com_a_mesma_carteira_na_primeira_proposta() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(PROPOSTA_1) ).send() );
		Assert.assertNotNull( p );
		
		this.democracia.votar(p.getIndex(), VOTO_FAVOR).send();
		this.democracia.votar(p.getIndex(), VOTO_CONTRA).send();
		
		Proposta fim = new Proposta( this.democracia.getProposta( BigInteger.valueOf(PROPOSTA_1) ).send() );
		Assert.assertEquals(fim.getVotosContra(), 1);
		Assert.assertEquals(fim.getVotosFavor(), 1);
	}
	
	@Test
	public void efetua_dois_votos_com_a_mesma_carteira_em_propostas_diferentes() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(PROPOSTA_1) ).send() );
		Assert.assertNotNull( p );
		
		this.democracia.votar(p.getIndex(), VOTO_FAVOR).send();
		
		Democracia d = TestDemocraciaFactory.loadFromAddress(this.account1);
		Assert.assertNotNull( d );
		
		d.votar(p.getIndex(), VOTO_CONTRA).send();
		
		Proposta fim = new Proposta( this.democracia.getProposta( BigInteger.valueOf(PROPOSTA_1) ).send() );
		Assert.assertEquals(fim.getVotosContra(), 1);
		Assert.assertEquals(fim.getVotosFavor(), 1);
	}
	
	@Test
	public void busca_a_quarta_proposta_cadastrada() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(PROPOSTA_4) ).send() );
		
		Assert.assertNotNull( p );
		Assert.assertEquals("Proposta de Voto 4", p.getTitulo() );
		Assert.assertEquals("Aqui vai o texto da minha proposta número 4", p.getDescricao() );
		Assert.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCriador() );
		Assert.assertEquals(400l, p.getTotalVotos() );
		Assert.assertEquals(4l, p.getVotosFavor() );
		Assert.assertEquals(1l, p.getVotosContra());
		Assert.assertEquals(1, p.getStatus() );
	}
	
	@Test
	public void busca_a_quinta_proposta_cadastrada() throws Exception  {
		Proposta p = new Proposta( this.democracia.getProposta( BigInteger.valueOf(PROPOSTA_5) ).send() );
		
		Assert.assertNotNull( p );
		Assert.assertEquals("Proposta de Voto 5", p.getTitulo() );
		Assert.assertEquals("Aqui vai o texto da minha proposta número 5", p.getDescricao() );
		Assert.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCriador() );
		Assert.assertEquals(500l, p.getTotalVotos() );
		Assert.assertEquals(1l, p.getVotosFavor() );
		Assert.assertEquals(4l, p.getVotosContra());
		Assert.assertEquals(1, p.getStatus() );
	}
}
