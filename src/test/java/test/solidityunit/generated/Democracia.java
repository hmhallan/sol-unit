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
import org.web3j.tuples.generated.Tuple9;
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
    private static final String BINARY = "608060405260008054600160a060020a0319163317905534801561002257600080fd5b506109ff806100326000396000f30060806040526004361061006c5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166341c0e1b58114610071578063432332e11461008857806358a7b386146100af5780635f7d1713146101e85780636f4777be14610288575b600080fd5b34801561007d57600080fd5b506100866103ac565b005b34801561009457600080fd5b5061009d6103cf565b60408051918252519081900360200190f35b3480156100bb57600080fd5b506100c76004356103ec565b604051808a8152602001806020018060200189600160a060020a0316600160a060020a0316815260200188815260200187815260200186815260200185815260200184815260200183810383528b818151815260200191508051906020019080838360005b8381101561014457818101518382015260200161012c565b50505050905090810190601f1680156101715780820380516001836020036101000a031916815260200191505b5083810382528a5181528a516020918201918c019080838360005b838110156101a457818101518382015260200161018c565b50505050905090810190601f1680156101d15780820380516001836020036101000a031916815260200191505b509b50505050505050505050505060405180910390f35b3480156101f457600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261008694369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497505084359550505060209092013591506105b39050565b34801561029457600080fd5b506102a06004356106db565b60405180806020018060200187600160a060020a0316600160a060020a03168152602001868152602001858152602001848152602001838103835289818151815260200191508051906020019080838360005b8381101561030b5781810151838201526020016102f3565b50505050905090810190601f1680156103385780820380516001836020036101000a031916815260200191505b5083810382528851815288516020918201918a019080838360005b8381101561036b578181015183820152602001610353565b50505050905090810190601f1680156103985780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b600054600160a060020a03163314156103cd57600054600160a060020a0316ff5b565b6001546000908110156103e557506001546103e9565b5060005b90565b600060608060008060008060008060008a6001805490501015156105a557600180548c90811061041857fe5b906000526020600020906008020190508a81600001826001018360020160009054906101000a9004600160a060020a031684600301548560040154866005018054905087600601805490508860070154878054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104fd5780601f106104d2576101008083540402835291602001916104fd565b820191906000526020600020905b8154815290600101906020018083116104e057829003601f168201915b50508a5460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959d508c94509250840190508282801561058b5780601f106105605761010080835404028352916020019161058b565b820191906000526020600020905b81548152906001019060200180831161056e57829003601f168201915b505050505096509950995099509950995099509950995099505b509193959799909294969850565b6105bb61084d565b84815260208082018590526060820184905260808201839052336040830152600160e083018190528054808201808355600092909252835180519293859360089093027fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6019261062e928492019061089c565b506020828101518051610647926001850192019061089c565b50604082015160028201805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055606082015160038201556080820151600482015560a082015180516106aa91600584019160209091019061091a565b5060c082015180516106c691600684019160209091019061091a565b5060e082015181600701555050505050505050565b60018054829081106106e957fe5b60009182526020918290206008919091020180546040805160026001841615610100026000190190931692909204601f8101859004850283018501909152808252919350918391908301828280156107825780601f1061075757610100808354040283529160200191610782565b820191906000526020600020905b81548152906001019060200180831161076557829003601f168201915b505050505090806001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108205780601f106107f557610100808354040283529160200191610820565b820191906000526020600020905b81548152906001019060200180831161080357829003601f168201915b505050506002830154600384015460048501546007909501549394600160a060020a039092169390925086565b6101006040519081016040528060608152602001606081526020016000600160a060020a0316815260200160008152602001600081526020016060815260200160608152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106108dd57805160ff191683800117855561090a565b8280016001018555821561090a579182015b8281111561090a5782518255916020019190600101906108ef565b50610916929150610988565b5090565b82805482825590600052602060002090810192821561097c579160200282015b8281111561097c578251825473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0390911617825560209092019160019091019061093a565b506109169291506109a2565b6103e991905b80821115610916576000815560010161098e565b6103e991905b8082111561091657805473ffffffffffffffffffffffffffffffffffffffff191681556001016109a85600a165627a7a72305820a9875a47b1fe7e5c5feaae950bfc9fa2ef29f9a49d42d3ab83f9e9400c61d6840029";

    public static final String FUNC_KILL = "kill";

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

    public RemoteCall<TransactionReceipt> kill() {
        final Function function = new Function(
                FUNC_KILL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getTotaldePropostas() {
        final Function function = new Function(FUNC_GETTOTALDEPROPOSTAS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple9<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> getProposta(BigInteger index) {
        final Function function = new Function(FUNC_GETPROPOSTA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple9<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple9<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple9<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (BigInteger) results.get(8).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> criarProposta(String titulo, String descricao, BigInteger visivelAte, BigInteger totalVotos) {
        final Function function = new Function(
                FUNC_CRIARPROPOSTA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(titulo), 
                new org.web3j.abi.datatypes.Utf8String(descricao), 
                new org.web3j.abi.datatypes.generated.Uint256(visivelAte), 
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
