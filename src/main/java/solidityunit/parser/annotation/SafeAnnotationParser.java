package solidityunit.parser.annotation;

import org.junit.runners.model.FrameworkMethod;

import solidityunit.annotations.Safe;
import solidityunit.parser.SafeParser;

public class SafeAnnotationParser implements SafeParser {
	
	@Override
    public boolean isSafe(FrameworkMethod actualMethod) {
    	Safe safe = actualMethod.getAnnotation(Safe.class);
    	return (safe != null);
    }
    
}
