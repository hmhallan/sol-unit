package test.solidityunit.generated;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple8;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.5.0.
 */
public class Democracia extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5061099c806100206000396000f3006080604052600436106100615763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663432332e1811461006657806358a7b3861461008d5780635f7d1713146101bf5780636f4777be1461025f575b600080fd5b34801561007257600080fd5b5061007b610383565b60408051918252519081900360200190f35b34801561009957600080fd5b506100a560043561038a565b60405180898152602001806020018060200188600160a060020a0316600160a060020a0316815260200187815260200186815260200185815260200184815260200183810383528a818151815260200191508051906020019080838360005b8381101561011c578181015183820152602001610104565b50505050905090810190601f1680156101495780820380516001836020036101000a031916815260200191505b5083810382528951815289516020918201918b019080838360005b8381101561017c578181015183820152602001610164565b50505050905090810190601f1680156101a95780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390f35b3480156101cb57600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261007b94369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497505084359550505060209092013591506105469050565b34801561026b57600080fd5b50610277600435610678565b60405180806020018060200187600160a060020a0316600160a060020a03168152602001868152602001858152602001848152602001838103835289818151815260200191508051906020019080838360005b838110156102e25781810151838201526020016102ca565b50505050905090810190601f16801561030f5780820380516001836020036101000a031916815260200191505b5083810382528851815288516020918201918a019080838360005b8381101561034257818101518382015260200161032a565b50505050905090810190601f16801561036f5780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b6000545b90565b60006060806000806000806000808960008054905010151561053a57600080548b9081106103b457fe5b906000526020600020906008020190508981600001826001018360020160009054906101000a9004600160a060020a03168460030154856004015486600501805490508760060180549050868054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104945780601f1061046957610100808354040283529160200191610494565b820191906000526020600020905b81548152906001019060200180831161047757829003601f168201915b5050895460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959c508b9450925084019050828280156105225780601f106104f757610100808354040283529160200191610522565b820191906000526020600020905b81548152906001019060200180831161050557829003601f168201915b50505050509550985098509850985098509850985098505b50919395975091939597565b60006105506107ea565b85815260208082018690526060820185905260808201849052336040830152600160e083018190526000805491820180825590805283518051919385936008027f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e56301926105c09284920190610839565b5060208281015180516105d99260018501920190610839565b50604082015160028201805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055606082015160038201556080820151600482015560a0820151805161063c9160058401916020909101906108b7565b5060c082015180516106589160068401916020909101906108b7565b5060e0919091015160079091015550506000546000190195945050505050565b600080548290811061068657fe5b60009182526020918290206008919091020180546040805160026001841615610100026000190190931692909204601f81018590048502830185019091528082529193509183919083018282801561071f5780601f106106f45761010080835404028352916020019161071f565b820191906000526020600020905b81548152906001019060200180831161070257829003601f168201915b505050505090806001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107bd5780601f10610792576101008083540402835291602001916107bd565b820191906000526020600020905b8154815290600101906020018083116107a057829003601f168201915b505050506002830154600384015460048501546007909501549394600160a060020a039092169390925086565b6101006040519081016040528060608152602001606081526020016000600160a060020a0316815260200160008152602001600081526020016060815260200160608152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061087a57805160ff19168380011785556108a7565b828001600101855582156108a7579182015b828111156108a757825182559160200191906001019061088c565b506108b3929150610925565b5090565b828054828255906000526020600020908101928215610919579160200282015b82811115610919578251825473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039091161782556020909201916001909101906108d7565b506108b392915061093f565b61038791905b808211156108b3576000815560010161092b565b61038791905b808211156108b357805473ffffffffffffffffffffffffffffffffffffffff191681556001016109455600a165627a7a72305820460235e6917fff64a0aa1de8ba9ce4b99aa80bf945ae00847773cd0ac055a7d70029";

    public static final String FUNC_GETTOTALDEPROPOSTAS = "getTotaldePropostas";

    public static final String FUNC_GETPROPOSTA = "getProposta";

    public static final String FUNC_CRIARPROPOSTA = "criarProposta";

    public static final String FUNC_PROPOSTAS = "propostas";

    protected Democracia(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Democracia(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<BigInteger> getTotaldePropostas() {
        final Function function = new Function(FUNC_GETTOTALDEPROPOSTAS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple8<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger>> getProposta(BigInteger index) {
        final Function function = new Function(FUNC_GETPROPOSTA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple8<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple8<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple8<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> criarProposta(String titulo, String descricao, BigInteger dataFinal, BigInteger totalVotos) {
        final Function function = new Function(
                FUNC_CRIARPROPOSTA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(titulo), 
                new org.web3j.abi.datatypes.Utf8String(descricao), 
                new org.web3j.abi.datatypes.generated.Uint256(dataFinal), 
                new org.web3j.abi.datatypes.generated.Uint256(totalVotos)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple6<String, String, String, BigInteger, BigInteger, BigInteger>> propostas(BigInteger param0) {
        final Function function = new Function(FUNC_PROPOSTAS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple6<String, String, String, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple6<String, String, String, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<String, String, String, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, String, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public static RemoteCall<Democracia> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Democracia.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Democracia> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Democracia.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Democracia load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Democracia(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Democracia load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Democracia(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
