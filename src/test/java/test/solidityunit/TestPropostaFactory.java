package test.solidityunit;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.Properties;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import solidityunit.constants.Config;
import solidityunit.internal.utilities.PropertiesReader;
import test.solidityunit.generated.Democracia;

public class TestPropostaFactory {
	
	//propriedades carregadas do arquivo de properties
	static Properties testProperties;
	
	//instancias do web3j
	static Web3j web3j;
	static Admin web3Admin;

	public static TransactionReceipt criarProposta( Democracia democracia, String titulo, String descricao, Date validoAte, int totalVotos ) throws Exception {
		RemoteCall<TransactionReceipt> call = democracia.criarProposta(titulo, descricao, BigInteger.valueOf(validoAte.getTime()), BigInteger.valueOf(totalVotos) );
		TransactionReceipt receipt = call.send();
		return receipt;
	}
	
	public static TransactionReceipt criarVoto( Democracia democracia, int indexProposta, BigInteger voto ) throws Exception {
		RemoteCall<TransactionReceipt> call = democracia.votar(BigInteger.valueOf(indexProposta), voto);
		TransactionReceipt receipt = call.send();
		return receipt;
	}
	
	public static TransactionReceipt criarVoto( Democracia democracia, String privateKey, int indexProposta, BigInteger voto ) throws Exception {
		Democracia d = loadFromAddress(democracia, privateKey);
		RemoteCall<TransactionReceipt> call = d.votar(BigInteger.valueOf(indexProposta), voto);
		TransactionReceipt receipt = call.send();
		return receipt;
	}
	
	public static Democracia loadFromAddress( Democracia d, String privateKey ) {
		init();
		
		Credentials credentials = Credentials.create(privateKey);
		return Democracia.load(d.getContractAddress(), web3j, credentials, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
	}
	
	private static void init() {
		if (testProperties == null) {
			try {
				testProperties = new PropertiesReader().loadProperties("solidity-unit.properties");
				web3Admin = Admin.build(new HttpService(testProperties.getProperty(Config.WEB3_HOST))); 
		    	web3j = Web3j.build(new HttpService(testProperties.getProperty(Config.WEB3_HOST)));
		    	
			} catch (IOException e) {
				 throw new RuntimeException(new IOException("Erro ao ler arquivo de properties", e));
			}
		}
	}
	
}

