import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor;
import org.eclipse.jdt.core.dom.ChildPropertyDescriptor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class STVVProject
{
	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception {
		
		//String content = new Scanner(new File("./src/testProject.java")).useDelimiter("\\Z").next();
		String content;
		File dir = new File("./parse4j-036c44ca4eb167a260897ddd9e573dc63f72d796/src/main/java/org/parse4j");
		ArrayList<String> listOfFiles = new ArrayList<String>();
		listOfFiles = getAllFileNames(dir, listOfFiles);
		for(int i = 0; i < listOfFiles.size(); ++i)
		{
			content = new Scanner(new File(listOfFiles.get(i))).useDelimiter("\\Z").next();
			instrumentation(content);	
		}
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