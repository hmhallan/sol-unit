package solidityunit.parser;

import org.junit.runners.model.FrameworkMethod;

import solidityunit.annotations.Safe;

public class SafeAnnotationParser implements SafeParser {
	
	@Override
    public boolean isSafe(FrameworkMethod actualMethod) {
    	Safe safe = actualMethod.getAnnotation(Safe.class);
    	return (safe != null);
    }
    
}
