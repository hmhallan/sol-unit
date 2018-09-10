package solidityunit.runner;

import org.jglue.cdiunit.CdiRunner;
import org.junit.runners.model.InitializationError;

public class SolidityUnitRunner extends CdiRunner {

	public SolidityUnitRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

}
