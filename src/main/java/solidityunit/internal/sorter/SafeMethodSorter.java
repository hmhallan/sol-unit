package solidityunit.internal.sorter;

import java.util.Comparator;

import org.junit.runners.model.FrameworkMethod;

import solidityunit.annotations.Safe;

public class SafeMethodSorter implements Comparator<FrameworkMethod> {
	
    public int compare(FrameworkMethod m1, FrameworkMethod m2) {
    	
    	Safe i1Safe = m1.getAnnotation(Safe.class);
    	Safe i2Safe = m2.getAnnotation(Safe.class);
        if (i1Safe != null && i2Safe == null) {
            return -1;
        }
        else if (i1Safe == null && i2Safe != null) {
            return 1;
        }
        return 0;
    }
    
}
