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
    private static final String BINARY = "608060405260008054600160a060020a0319163317905534801561002257600080fd5b50610aed806100326000396000f3006080604052600436106100775763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166341c0e1b5811461007c578063432332e1146100935780634fa5a16f146100ba57806358a7b386146100d55780635f7d17131461020e5780636f4777be146102ae575b600080fd5b34801561008857600080fd5b506100916103d2565b005b34801561009f57600080fd5b506100a86103f5565b60408051918252519081900360200190f35b3480156100c657600080fd5b50610091600435602435610412565b3480156100e157600080fd5b506100ed6004356104da565b604051808a8152602001806020018060200189600160a060020a0316600160a060020a0316815260200188815260200187815260200186815260200185815260200184815260200183810383528b818151815260200191508051906020019080838360005b8381101561016a578181015183820152602001610152565b50505050905090810190601f1680156101975780820380516001836020036101000a031916815260200191505b5083810382528a5181528a516020918201918c019080838360005b838110156101ca5781810151838201526020016101b2565b50505050905090810190601f1680156101f75780820380516001836020036101000a031916815260200191505b509b50505050505050505050505060405180910390f35b34801561021a57600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261009194369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497505084359550505060209092013591506106a19050565b3480156102ba57600080fd5b506102c66004356107c9565b60405180806020018060200187600160a060020a0316600160a060020a03168152602001868152602001858152602001848152602001838103835289818151815260200191508051906020019080838360005b83811015610331578181015183820152602001610319565b50505050905090810190601f16801561035e5780820380516001836020036101000a031916815260200191505b5083810382528851815288516020918201918a019080838360005b83811015610391578181015183820152602001610379565b50505050905090810190601f1680156103be5780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b600054600160a060020a03163314156103f357600054600160a060020a0316ff5b565b60015460009081101561040b575060015461040f565b5060005b90565b600081600114806104235750816002145b151561042e57600080fd5b600180548490811061043c57fe5b9060005260206000209060080201905080600301544210151561045e57600080fd5b81600114156104a0576005810180546001810182556000918252602090912001805473ffffffffffffffffffffffffffffffffffffffff1916331790556104d5565b6006810180546001810182556000918252602090912001805473ffffffffffffffffffffffffffffffffffffffff1916331790555b505050565b600060608060008060008060008060008a60018054905010151561069357600180548c90811061050657fe5b906000526020600020906008020190508a81600001826001018360020160009054906101000a9004600160a060020a031684600301548560040154866005018054905087600601805490508860070154878054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105eb5780601f106105c0576101008083540402835291602001916105eb565b820191906000526020600020905b8154815290600101906020018083116105ce57829003601f168201915b50508a5460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959d508c9450925084019050828280156106795780601f1061064e57610100808354040283529160200191610679565b820191906000526020600020905b81548152906001019060200180831161065c57829003601f168201915b505050505096509950995099509950995099509950995099505b509193959799909294969850565b6106a961093b565b84815260208082018590526060820184905260808201839052336040830152600160e083018190528054808201808355600092909252835180519293859360089093027fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6019261071c928492019061098a565b506020828101518051610735926001850192019061098a565b50604082015160028201805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055606082015160038201556080820151600482015560a08201518051610798916005840191602090910190610a08565b5060c082015180516107b4916006840191602090910190610a08565b5060e082015181600701555050505050505050565b60018054829081106107d757fe5b60009182526020918290206008919091020180546040805160026001841615610100026000190190931692909204601f8101859004850283018501909152808252919350918391908301828280156108705780601f1061084557610100808354040283529160200191610870565b820191906000526020600020905b81548152906001019060200180831161085357829003601f168201915b505050505090806001018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561090e5780601f106108e35761010080835404028352916020019161090e565b820191906000526020600020905b8154815290600101906020018083116108f157829003601f168201915b505050506002830154600384015460048501546007909501549394600160a060020a039092169390925086565b6101006040519081016040528060608152602001606081526020016000600160a060020a0316815260200160008152602001600081526020016060815260200160608152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106109cb57805160ff19168380011785556109f8565b828001600101855582156109f8579182015b828111156109f85782518255916020019190600101906109dd565b50610a04929150610a76565b5090565b828054828255906000526020600020908101928215610a6a579160200282015b82811115610a6a578251825473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909116178255602090920191600190910190610a28565b50610a04929150610a90565b61040f91905b80821115610a045760008155600101610a7c565b61040f91905b80821115610a0457805473ffffffffffffffffffffffffffffffffffffffff19168155600101610a965600a165627a7a72305820a90749f8d5d293e23f0073303eddf944917911717c16a209921e2c4d9b9eeec90029";

    public static final String FUNC_KILL = "kill";

    public static final String FUNC_GETTOTALDEPROPOSTAS = "getTotaldePropostas";

    public static final String FUNC_VOTAR = "votar";

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

    public RemoteCall<TransactionReceipt> votar(BigInteger index, BigInteger voto) {
        final Function function = new Function(
                FUNC_VOTAR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index), 
                new org.web3j.abi.datatypes.generated.Uint256(voto)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
