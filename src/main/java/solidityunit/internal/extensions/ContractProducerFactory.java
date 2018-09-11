package solidityunit.internal.extensions;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.util.AnnotationLiteral;

public class ContractProducerFactory {
	
	private static final Map<String, Class<?>> CONTRACTS_CLASS_MAP = new ConcurrentHashMap<String, Class<?>>();

	/**
	 * Registra os Contratos injetados
	 * @param contractClass a classe do contrato a ser registrado
	 */
	public static void putIfAbsent(final Class<?> contractClass) {
	    if (!CONTRACTS_CLASS_MAP.containsKey(contractClass.getCanonicalName())) {
	    	CONTRACTS_CLASS_MAP.put(contractClass.getCanonicalName(), contractClass);
	    }
	}
	
	public static Set<Entry<String, Class<?>>> getProxyClassEntries() {
	    return CONTRACTS_CLASS_MAP.entrySet();
	}
	
	/**
     * Create a bean for given proxy / injection target / type / scope
     * @param proxy the proxy object
     * @param it the injection target
     * @param clazz the proxy type
     * @param targetScope the returned managed bean' scope
     * @return the managed bean handling given proxy
     */
    public static <T extends Object> Bean<T> createBeanForProxy(final T proxy, final InjectionTarget<T> it, final Class<?> clazz, final Class<? extends Annotation> targetScope) {
        return new Bean<T>() {

            @Override
            public T create(final CreationalContext<T> ctx) {
            	return proxy;
            }

            @Override
            public void destroy(final T instance, final CreationalContext<T> ctx) {
                it.preDestroy(instance);
                it.dispose(instance);
                ctx.release();
            }

            @Override
            public Class<?> getBeanClass() {
                return clazz;
            }

            @Override
            public Set<InjectionPoint> getInjectionPoints() {
                return it.getInjectionPoints();
            }

            @Override
            public String getName() {
                return clazz.toString();
            }

            @Override
            public Set<Annotation> getQualifiers() {
                Set<Annotation> qualifiers = new HashSet<Annotation>();
                qualifiers.add(new AnnotationLiteral<Default>() {
                    private static final long serialVersionUID = 1L;
                });
                qualifiers.add(new AnnotationLiteral<Any>() {
                    private static final long serialVersionUID = 1L;
                });
                return qualifiers;
            }

            @Override
            public Class<? extends Annotation> getScope() {
                return targetScope;
            }

            @Override
            public Set<Class<? extends Annotation>> getStereotypes() {
                return Collections.emptySet();
            }

            @Override
            public Set<Type> getTypes() {
                Set<Type> types = new HashSet<Type>();
                types.add(clazz);
                return types;
            }

            @Override
            public boolean isAlternative() {
                return false;
            }

            @Override
            public boolean isNullable() {
                return false;
            }

        };
    }
    
    
    /**
	 * Este proxy é o responsável por instanciar o contrato quando o ponto de injeção for utilizado <br>
	 * Como no ponto de injeção é obrigatório já saber em tempo de deploy qual é a classe de implementação <br>
	 * a ser injetada, utilizamos um proxy, onde este faz o lookup apenas no momento da injeção
	 * 
	 * @param contractClazz classe do contrato
	 * @return proxy que faz o deploy do contrato ser invocado
	 */
	public static Object createContractProxy( final Class<?> contractClazz ) {
		return Proxy.newProxyInstance(contractClazz.getClassLoader(), new Class[] {
				contractClazz
	        }, new InvocationHandler() {

	            @Override
	            public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
	                Object ejbInstance = null;
	                try {
	                    // chamada do EJB remoto
	                    //ejbInstance = TjscLookup.locateService( Class.forName(remoteEJBClazz.getCanonicalName()) );
	                } catch (Exception e) {
	                	throw new IllegalStateException("Criação de Contrato para " + contractClazz.getCanonicalName() + " é inválida", e);
	                }
	                // Delega a invocação
	                return method.invoke(ejbInstance, args);
	            }
	        });
    }


}
