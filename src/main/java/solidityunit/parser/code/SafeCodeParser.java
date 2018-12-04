package solidityunit.parser.code;

import org.junit.runners.model.FrameworkMethod;

import solidityunit.parser.SafeParser;

public class SafeCodeParser implements SafeParser {

	@Override
	public boolean isSafe(FrameworkMethod actualMethod) {
		return false;
	}

}
