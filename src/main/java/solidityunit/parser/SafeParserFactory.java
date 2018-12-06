package solidityunit.parser;

import java.io.IOException;
import java.util.Properties;

import solidityunit.constants.Config;
import solidityunit.internal.utilities.PropertiesReader;
import solidityunit.parser.annotation.SafeAnnotationParser;

public class SafeParserFactory {
	
	private static SafeParser instance;
	
	private SafeParserFactory() {}
	
	public static SafeParser createParser() {
		
		if (instance != null) {
			return instance;
		}
		
		try {
			//le o properties
			Properties testProperties = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE);
			
			//instancia do parser criada via reflexao
			Class<?> clazz = Class.forName(testProperties.getProperty("safe.parser.class"));
			instance  = (SafeParser) clazz.newInstance();
			return instance;
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		//fallback para annotation
		return new SafeAnnotationParser();
		
	}

}
