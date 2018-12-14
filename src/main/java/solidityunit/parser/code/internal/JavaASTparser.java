package solidityunit.parser.code.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import solidityunit.parser.code.internal.ast.ContractClassFinder;
import solidityunit.parser.code.internal.ast.ContractSafeMethodFinder;
import solidityunit.parser.code.internal.ast.TestRunnerClassFinder;
import solidityunit.parser.code.internal.ast.TestRunnerSafeMethodFinder;

public class JavaASTparser {

	String sourceRootDir;
	SourceRoot sourceRoot;
	
	List<ClassOrInterfaceDeclaration> contractClasses;
	List<ClassOrInterfaceDeclaration> testClasses;
	
	List<MethodDeclaration> contractSafeMethods;
	List<MethodDeclaration> testSafeMethods;

	public static void main(String [] args) {
		JavaASTparser parser = new JavaASTparser();
	}

	public JavaASTparser() {
		this.sourceRootDir = "src/test/java";
		this.testClasses = new ArrayList<>();
		this.contractClasses = new ArrayList<>();
		this.contractSafeMethods = new ArrayList<>();
		this.testSafeMethods = new ArrayList<>();
		
		this.init();
	}

	public void init() {

		System.out.println( String.format("[%s] initializing", this.getClass().getName()) );
		
		//find all contracts
		this.findContractClasses();
		
		//finds all safe methods on contracts
		this.findContractSafeMethods();
		
		this.printContractResults();
		
		//find test classes
		this.findTestClasses();
		
		//find all safe methods on tests
		this.findTestClassesSafeMethods();
		
		this.printTestClassResults();
	}
	
	private void findContractClasses() {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            try {
            	new ContractClassFinder(this.contractClasses).visit(JavaParser.parse(file), null);
            } catch (IOException e) {
                new RuntimeException(e);
            }
		}).explore( new File(this.sourceRootDir) );
	}
	
	private void findContractSafeMethods() {
		this.contractClasses.stream().forEach( n -> {
			new ContractSafeMethodFinder( this.contractSafeMethods ).visit(n, null);
		});
	}
	
	private void findTestClasses() {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            try {
                new TestRunnerClassFinder(this.testClasses).visit(JavaParser.parse(file), null);
            } catch (IOException e) {
                new RuntimeException(e);
            }
		}).explore( new File(this.sourceRootDir) );
	}
	
	private void findTestClassesSafeMethods() {
		TestRunnerSafeMethodFinder finder = new TestRunnerSafeMethodFinder( this.testSafeMethods );
		finder.setContractClasses(this.contractClasses);
		finder.setContractSafeMethods(this.contractSafeMethods);
		
		this.testClasses.stream().forEach( n -> {
			finder.visit(n, null);
		});
	}
	
	private void printContractResults() {
		this.contractSafeMethods.stream().forEach( m -> System.out.println( String.format("Safe method found: [%s].[%s]", 
																			this.getClassFromContractMethodAsString(m),
																			m.getName()) ) );
	}
	
	private void printTestClassResults() {
		this.testSafeMethods.stream().forEach( m -> System.out.println( String.format("Safe method found: [%s].[%s]", 
																			this.getClassFromTestMethodAsString(m),
																			m.getName()) ) );
	}
	
	private String getClassFromContractMethodAsString( MethodDeclaration m ) {
		for ( ClassOrInterfaceDeclaration n: this.contractClasses ) {
			if ( n.getMethods().contains(m) ) {
				return n.getNameAsString();
			}
		}
		return null;
	}
	
	private String getClassFromTestMethodAsString( MethodDeclaration m ) {
		for ( ClassOrInterfaceDeclaration n: this.testClasses ) {
			if ( n.getMethods().contains(m) ) {
				return n.getNameAsString();
			}
		}
		return null;
	}
}

