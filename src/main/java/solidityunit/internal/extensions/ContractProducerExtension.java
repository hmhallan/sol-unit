package solidityunit.internal.extensions;

import java.util.Map.Entry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessInjectionTarget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import solidityunit.annotations.Contract;

public class ContractProducerExtension implements Extension {


	/** Logger instance */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * Este método é disparado pelo container na inicialização para cada classe de componente 
	 * que suporte injeção e seja instanciado em tempo de execução,
	 * inlcuindo managed beans declarados usando a anotação javax.annotation.ManagedBean,
	 * EJBs ou MDBs, interceptors e decorators.
	 *
	 * @param pit o evento que foi disparado
	 */
	<T> void processInjectionTarget(@Observes final ProcessInjectionTarget<T> pit) {
	    for (AnnotatedField<? super T> field : pit.getAnnotatedType().getFields()) {
	        if (field.isAnnotationPresent(Contract.class)) {
	        	
	        	//registra o contrato
	        	ContractProducerFactory.putIfAbsent(field.getJavaMember().getType());
	        }
	    }
	}
	
	/**
	 * Este método é disparado pelo weld quando o mesmo finalizar por completo o processo de
	 * descoberta de beans, validando que não existem erros de definição relativos aos beans descobertos.
	 * 
	 *
	 * @param abd evento que foi disparado
	 * @param bm Permite que uma extensão interaja de forma direta com o container. 
	 * 			O bean manager que vai fazer o controle do ponto de injeção da dependência.
	 */
	@SuppressWarnings("unchecked")
	void afterBeanDiscovery(@Observes final AfterBeanDiscovery abd, final BeanManager bm) {

	    // busca em todas as interfaces registradas
	    for (final Entry<String, Class<?>> remoteClassEntry : ContractProducerFactory.getProxyClassEntries()) {

	    	final Object remoteProxy;
	        final Class<?> remoteClass = remoteClassEntry.getValue();

	        try {
	            // Cria um proxy que faz a chamada do EJB Remoto via TjscLookup
	            remoteProxy = ContractProducerFactory.createContractProxy(remoteClass);
	        } catch (Exception e) {
	            throw new IllegalStateException("Criação do proxy para " + remoteClass.getCanonicalName() + " falhou.", e);
	        }
	        
	        final InjectionTarget<Object> it;
	        try {
	        	//cria o ponto de injeção com o proxy do EJB remoto
	            AnnotatedType<Object> at = (AnnotatedType<Object>) bm.createAnnotatedType(remoteProxy.getClass());
	            it = bm.createInjectionTarget(at);
	        } catch (Exception e) {
				throw new IllegalStateException("Ponto de Injeção de EJB Remoto para " + remoteClass.getCanonicalName() + " é inválido", e);
	        }

	        //cria o Bean CDI responsável pelo ciclo de vida da injeção
	        final Bean<?> beanRemoteProxy = ContractProducerFactory.createBeanForProxy(remoteProxy, it, remoteClass, ApplicationScoped.class);
	        abd.addBean(beanRemoteProxy);
	        
	        log.warn("Contrato encontrado: " + remoteClass);
	    }

	}

}
