package test.solidityunit;

import java.math.BigInteger;
import java.util.Date;

import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import test.solidityunit.generated.Democracia;

public class TestPropostaFactory {

	public static TransactionReceipt criarProposta( Democracia democracia, String titulo, String descricao, Date validoAte, int totalVotos ) throws Exception {
		RemoteCall<TransactionReceipt> call = democracia.criarProposta(titulo, descricao, BigInteger.valueOf(validoAte.getTime()), BigInteger.valueOf(totalVotos) );
		TransactionReceipt receipt = call.send();
		return receipt;
	}
	
}
