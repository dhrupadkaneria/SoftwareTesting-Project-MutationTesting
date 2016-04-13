import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class STVVProject
{
	public static void main(String args[]) throws Exception {
		
		//String content = new Scanner(new File("./src/testProject.java")).useDelimiter("\\Z").next();
		String content;
		File dir = new File("./parse4j-036c44ca4eb167a260897ddd9e573dc63f72d796/src/main/java/org/parse4j");
		ArrayList<String> listOfFiles = new ArrayList<String>();
		listOfFiles = getAllFileNames(dir, listOfFiles);
		for(int i = 0; i < listOfFiles.size(); ++i)
		{
			//content = new Scanner(new File(listOfFiles.get(i))).useDelimiter("\\Z").next();
			//instrumentation(content);
			
			File file = new File(listOfFiles.get(i));
			processJavaFile(file);
		}
		/*File file = new File("./src/testProject.java");
		processJavaFile(file);*/
	}
	
	
	public static void processJavaFile(File file) throws Exception, BadLocationException 
	{
	    String source = FileUtils.readFileToString(file);
	    Document document = new Document(source);
	    ASTParser parser = ASTParser.newParser(AST.JLS3);
	    parser.setSource(document.get().toCharArray());
	    CompilationUnit unit = (CompilationUnit)parser.createAST(null);
	    unit.recordModifications();

	    // to get the imports from the file
/*	    List<ImportDeclaration> imports = unit.imports();
	    for (ImportDeclaration i : imports) 
	    {
	        System.out.println(i.getName().getFullyQualifiedName());
	    }
*/
	    // to create a new import
	    //AST ast = unit.getAST();
	    /*ImportDeclaration id = ast.newImportDeclaration();
	    //String classToImport = "path.to.some.class";
	    //id.setName(ast.newName(classToImport.split("\\.")));
	    //unit.imports().add(id); // add import declaration at end

	    // to save the changed file
	    TextEdit edits = unit.rewrite(document, null);
	    edits.apply(document);
	    FileUtils.writeStringToFile(file, document.get());

	    // to iterate through methods
	    List<AbstractTypeDeclaration> types = unit.types();
	    for (AbstractTypeDeclaration type : types) 
	    {
	        if (type.getNodeType() == ASTNode.TYPE_DECLARATION) 
	        {
	        	//System.out.println("class: " + type.getName().getFullyQualifiedName());
	            // Class def found
	            List<BodyDeclaration> bodies = type.bodyDeclarations();
	            for (BodyDeclaration body : bodies) 
	            {
	                if (body.getNodeType() == ASTNode.METHOD_DECLARATION) 
	                {
	                    MethodDeclaration method = (MethodDeclaration)body;
	                    //System.out.println("method: " + method.getName().getFullyQualifiedName());
	                    if(method.getBody() != null)
	                    {
	                    	Block methodBlock = method.getBody();
	                    	List statements = methodBlock.statements();
	                    	for(int i = 0; i < statements.size(); ++i)
	                    	{
	                    		//System.out.println("statementClass " + i + ": " + statements.get(i).getClass());
	                    		//System.out.println("statement " + i + ": " + statements.get(i));
	                    		if(statements.get(i) instanceof WhileStatement)
	                    		{
	                    			//System.out.println("While statement " + i + ": " + statements.get(i));
		                    		
	                    		}
	                    		if(statements.get(i) instanceof IfStatement)
	                    		{
	                    			//System.out.println("If statement " + i + ": " + statements.get(i));
		                    		
	                    		}
	                    	}
	                    }
	                }
	            }
	        }
	    }*/
	    ASTVisitor astVisitor = new ASTVisitor(){
	    	public boolean visit(final IfStatement ifstatement)
	    	{

	    		//System.out.println("before: " + ifstatement);
	    		Expression e = ifstatement.getExpression();
	    		//System.out.println("\nExpr: " + e.getClass());
	    		System.out.println("\nbefore: " + ifstatement.getExpression());
	    		
	    		if(e instanceof InfixExpression)
	    		{
	    			//System.out.println("left: " + ((InfixExpression) e).getLeftOperand());
	    			//System.out.println("right: " + ((InfixExpression) e).getRightOperand());
	    			//System.out.println("operator: " + ((InfixExpression) e).getOperator());
	    			
	    			if(((InfixExpression) e).getOperator() == InfixExpression.Operator.CONDITIONAL_OR)
	    			{
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.CONDITIONAL_AND);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.CONDITIONAL_AND)
	    			{
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.CONDITIONAL_OR);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.GREATER)
	    			{
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.GREATER_EQUALS);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.GREATER_EQUALS)
	    			{
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.GREATER);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.LESS)
	    			{
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.LESS_EQUALS);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.LESS_EQUALS)
	    			{
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.LESS);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.NOT_EQUALS)
	    			{
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.EQUALS);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.EQUALS)
	    			{
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.NOT_EQUALS);
	    			}
	    			//System.out.println("operator: " + ((InfixExpression) e).getOperator());
	    		}
	    		
	    		System.out.println("after: " + ifstatement.getExpression());
	    		//System.out.println("after: " + ifstatement);
	    		return true;
	    	}
	    	
	    	
	    };
	    unit.accept(astVisitor);
	}
	
	
	

	
	@SuppressWarnings("unchecked")
	private static void instrumentation(String content) throws Exception {

		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(content.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit astRoot = (CompilationUnit) parser.createAST(null);

		// create a ASTRewrite
		AST ast = astRoot.getAST();
		ASTRewrite rewriter = ASTRewrite.create(ast);

		// the original document
		Document document = new Document(content);
		
		// iterate over all types
		for (int t = 0; t < astRoot.types().size(); t++) {
			// TODO: finish the code to add log method name for each method
			
			
			TypeDeclaration type = (TypeDeclaration) astRoot.types().get(t);
			MethodDeclaration[] methodList = type.getMethods();	
			Block block;
			List<ASTNode> statements;
			System.out.println("class :" + type.getName());
			for(int i = 0; i < methodList.length; ++i)
			{
				System.out.println("method :" + methodList[i].getName());
				//ASTVisitor astVisitor = new ASTVisitor();
				/*block = methodList[i].getBody();

				statements = block.statements();
				for(int j = 0; j < statements.size(); ++j)
				{
					ASTNode st = statements.get(j);
					System.out.print("statement: " + st);*/
					/*
					if(st instanceof InfixExpression)
					{
						System.out.println("Expression");
						
						
						InstanceofExpression inst= (InstanceofExpression) st;
						InfixExpression expression= ast.newInfixExpression(); 
						expression.setLeftOperand((Expression) rewriter.createCopyTarget(inst.getLeftOperand())); 
						expression.setOperator(InfixExpression.Operator.MINUS);
						expression.setRightOperand((Expression) rewriter.createCopyTarget(inst.getRightOperand()));
						rewriter.replace(inst, expression, null);
					}*/
					/*ChildListPropertyDescriptor property = ast.;
					System.out.println("StructuralProperties: " + n.getStructuralProperty(property.getNodeClass()));
					List infix = n.structuralPropertiesForType();
					
					System.out.println(infix);*/
					/*if(n.getNodeType() == 21)
					{
						StructuralPropertyDescriptor s;
						System.out.println(n.getStructuralProperty(s.getNodeClass()));
					}*/
					
				}
				//System.out.println("statement:\n" + statements);
			//}
		}
		// apply all the edits to the compilation unit
		TextEdit edits = rewriter.rewriteAST(document, null);
		edits.apply(document);

		// this is the code for adding statements
		//System.out.println(document.get());
	}
	
	
	

	
	private static ArrayList<String> getAllFileNames(File dir, ArrayList<String> listOfFiles) throws Exception
	{
		File[] files = dir.listFiles();
		for (File file : files) 
		{
			if (file.isDirectory()) 
			{
				listOfFiles = getAllFileNames(file, listOfFiles);
			} 
			else 
			{
				listOfFiles.add(file.getCanonicalPath());
			}
		}
		return listOfFiles;
	}
	
}