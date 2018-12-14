package solidityunit.parser.code.internal.ast;

import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ContractClassFinder extends VoidVisitorAdapter<Object> {
	
	List<ClassOrInterfaceDeclaration> list;
	
	public ContractClassFinder( List<ClassOrInterfaceDeclaration> list ) {
		this.list = list;
	}
	
	@Override
    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
        super.visit(n, arg);
        
        for (ClassOrInterfaceType ext:  n.getExtendedTypes() ) {
        	if( ext.getNameAsString().equals("Contract") ) {
        		list.add(n);
        	}
        }
    }

}
