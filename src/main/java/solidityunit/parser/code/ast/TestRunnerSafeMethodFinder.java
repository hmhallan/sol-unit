package solidityunit.parser.code.ast;

import java.util.List;

import org.junit.Test;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import solidityunit.annotations.Contract;

public class TestRunnerSafeMethodFinder extends VoidVisitorAdapter<Void> {
	
	List<MethodDeclaration> list;
	
	List<ClassOrInterfaceDeclaration> contractClasses;
	List<MethodDeclaration> contractSafeMethods;
	
	public TestRunnerSafeMethodFinder(List<MethodDeclaration> list) {
		this.list = list;
	}
	public void setContractClasses(List<ClassOrInterfaceDeclaration> contractClasses) {
		this.contractClasses = contractClasses;
	}
	public void setContractSafeMethods(List<MethodDeclaration> contractSafeMethods) {
		this.contractSafeMethods = contractSafeMethods;
	}



	@Override
	public void visit(MethodDeclaration md, Void arg) {
		super.visit(md, arg);
		
		if ( md.getAnnotationByClass(Test.class).isPresent() ) {
			if ( this.isSafe(md) ){
				this.list.add(md);
			}
		}
			
	}
	
	private boolean isSafe(MethodDeclaration md) {
		FieldAccessFinder a = new FieldAccessFinder(md);
		a.setContractClasses(contractClasses);
		a.setContractSafeMethods(contractSafeMethods);
		a.visit(md, null);
		return a.isSafe();
	}

}



class FieldAccessFinder extends VoidVisitorAdapter<Void> {
	
	List<ClassOrInterfaceDeclaration> contractClasses;
	List<MethodDeclaration> contractSafeMethods;
	
	MethodDeclaration md;
	boolean contractAccess;
	Type contractType;
	
	boolean safe;
	
	//TODO: more than one contract in one method test?
	
	public FieldAccessFinder(MethodDeclaration md) {
		this.md = md;
		this.safe = true;
	}
	public void setContractClasses(List<ClassOrInterfaceDeclaration> contractClasses) {
		this.contractClasses = contractClasses;
	}
	public void setContractSafeMethods(List<MethodDeclaration> contractSafeMethods) {
		this.contractSafeMethods = contractSafeMethods;
	}
	
	@Override
    public void visit(FieldAccessExpr field, Void arg) {
        super.visit(field, arg);
        
        String fieldAccessName = field.getNameAsString();
        
        //search for a field call that is a contract
        field.findCompilationUnit().get().findAll(FieldDeclaration.class).stream().forEach( a -> {
    		if ( a.isAnnotationPresent(Contract.class)  ) {
    			String fieldName = a.getVariable(0).getNameAsString();
    			contractAccess = fieldName.equals(fieldAccessName);
    			contractType = a.getCommonType();
    		}
    	} );
        
        //search method call in the contract
        //send methods must be ignored
        if ( contractAccess ) {
        	md.findAll(MethodCallExpr.class).stream().forEach( a -> {
        		if( a.getScope().isPresent() && a.getScope().get().containsWithin(field) ) {
        			if ( !a.getName().asString().equals("send") ) {
        				
        				//search if there is a method safe with the same name
        				boolean found = false;
        				for ( MethodDeclaration s: this.contractSafeMethods ) {
        					if( s.getNameAsString().equals( a.getName().asString() ) ) {
        						found = true;
        					};
        				}
        				if ( !found ) {
        					safe = false;
        				}
        				
        			}
        		}
        	} );
        }
        
    }
	
	public boolean isSafe() {
		return safe;
	}
}
