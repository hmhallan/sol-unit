package test.solidityunit;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

public class TestWeb3Client {
	
	@Test
	public void buscar_as_contas_disponiveis() throws IOException {
		
		Admin web3Admin = Admin.build(new HttpService("http://localhost:7545")); 
		
		PersonalListAccounts pla = web3Admin.personalListAccounts().send();
		System.out.println(pla.getAccountIds());
	}
	
	@Test
	public void conectar_na_blockchain() throws IOException {
		
		Web3j web3 = Web3j.build(new HttpService("http://localhost:7545")); 
		Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
		String clientVersion = web3ClientVersion.getWeb3ClientVersion();
		
		System.out.println(clientVersion);
	}
	
	@Test
	public void transferir_1_ether_para_outra_carteira() throws IOException, InterruptedException, ExecutionException, TransactionException {
		
		Web3j web3j = Web3j.build(new HttpService("http://localhost:7545"));
		System.out.println("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
		
		//chave privada da carteira
		Credentials credentials = Credentials.create("29af556b884070fd5227dfda5e84a6d5f2addec908c55c5f38343e931d3594d9");
		System.out.println("Credentials loaded");
		
		System.out.println("Sending Ether ..");
		TransactionReceipt transferReceipt = Transfer.sendFunds(
				web3j, credentials,
				"0xBe6622a6A1E2267B293E5dFD4D89295307762B25",  // destinatário
				BigDecimal.valueOf(1), Convert.Unit.ETHER)
				.sendAsync().get();
		System.out.println("Transaction complete : " + transferReceipt.getTransactionHash());
	}

}
