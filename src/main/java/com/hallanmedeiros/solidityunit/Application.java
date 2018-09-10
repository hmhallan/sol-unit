package com.hallanmedeiros.solidityunit;

import java.math.BigDecimal;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

public class Application {

	public static void main(String[] args) throws Exception {
		new Application().run();
	}

	private void run() throws Exception { 
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