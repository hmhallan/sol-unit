package solidityunit.parser.code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.runners.model.FrameworkMethod;

import solidityunit.constants.Config;
import solidityunit.internal.utilities.PropertiesReader;
import solidityunit.parser.SafeParser;
import solidityunit.parser.code.internal.JavaCodeReader;
import solidityunit.parser.code.internal.SolidityCodeReader;
import solidityunit.parser.code.internal.entity.Classe;
import solidityunit.parser.code.internal.entity.Metodo;

public class SafeCodeParser implements SafeParser {

	private Properties testProperties;

	private List<File> javaFiles;
	private List<File> solidityFiles;
	
	private List<Classe> classes;

	public SafeCodeParser() {
		try {
			this.testProperties = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE);
			this.javaFiles = new ArrayList<>();
			this.solidityFiles = new ArrayList<>();

			this.init();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public boolean isSafe(FrameworkMethod actualMethod) {
		return false;
	}

	private void init() throws IOException {
		//faz a leitura de todos os metodos de teste
		File testDir = new File( testProperties.getProperty("test.src.dir") );
		this.readJavaFiles( testDir );

		//faz a leitura de todos os contratos
		File solidityDir = new File( testProperties.getProperty("solidity.src.dir") );
		if ( solidityDir.listFiles() == null ) {
			throw new FileNotFoundException("Diretorio " + testProperties.getProperty("solidity.src.dir") + " não possui arquivos");
		}
		this.readSolidityFiles( solidityDir );

		//faz a leitura para descobrir quais metodos sao 'safe' nos contratos
		this.findSafeMethodsOnContracts();
		
		this.findSafeMethodsOnTests();
	}


	private void findSafeMethodsOnContracts() throws IOException {
		for ( File f: this.solidityFiles ) {
			String code = this.readFile(f.getAbsolutePath());
			SolidityCodeReader reader = new SolidityCodeReader(code);
			
			//TODO: criar o parser
			//listar os metodos "safe"
			List<Metodo> metodos = reader.getMetodosSafe();
			for ( Metodo m: metodos ) {
				System.out.println( f.getName() + " -> " + m.getNome() );
			}
		}
	}
	
	private void findSafeMethodsOnTests() throws IOException {
		for ( File f: this.javaFiles ) {
			String s = this.readFile(f.getAbsolutePath());
			JavaCodeReader reader = new JavaCodeReader(s);
			
			if ( reader.isSolidityUnitRunner() ) {
				List<Metodo> metodos = reader.getMetodos();
				for ( Metodo m: metodos ) {
//					System.out.println( f.getName() + " -> " + m.getNome() );
				}
				//TODO: listar os metodos (e seu conteudo) para poder ver se chama algo "nao safe"
				//		basta ver se o que chama está na lista dos "safe" ou nao
			}
		}
	}

	private String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded);
	}

	private void readJavaFiles( File dir ) {
		for ( File f: dir.listFiles() ) {
			if ( f.isFile() && f.getName().endsWith(".java") ) {
				this.javaFiles.add( f );
			}
			if ( f.isDirectory() ) {
				this.readJavaFiles(f);
			}
		}
	}

	private void readSolidityFiles( File dir ) {
		for ( File f: dir.listFiles() ) {
			if ( f.isFile() && f.getName().endsWith(".sol") ) {
				this.solidityFiles.add( f );
			}
			if ( f.isDirectory() ) {
				this.readSolidityFiles(f);
			}
		}
	}
}
