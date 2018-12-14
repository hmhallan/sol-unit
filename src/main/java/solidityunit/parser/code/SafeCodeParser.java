package solidityunit.parser.code;

import java.io.IOException;

import org.junit.runners.model.FrameworkMethod;

import solidityunit.constants.Config;
import solidityunit.internal.utilities.PropertiesReader;
import solidityunit.parser.SafeParser;
import solidityunit.parser.code.ast.JavaASTparser;

public class SafeCodeParser implements SafeParser {

	private JavaASTparser javaParser;
	
	public SafeCodeParser() {
		try {
			String testSourceDir = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE).getProperty("test.src.dir");
			this.javaParser = new JavaASTparser( testSourceDir );
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public boolean isSafe(FrameworkMethod actualMethod) {
		return this.javaParser.isSafe(actualMethod);
	}

}
