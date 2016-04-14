import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class STVVProject
{
	static boolean status;
	public static void main(String args[]) throws Exception {
		
		int numofmutants = 10;
		deleteDir(new File("./Mutants"));
		for(int i=0; i<numofmutants; i++)
		{
			File source = new File("./parse4j-036c44ca4eb167a260897ddd9e573dc63f72d796");
			File dest = new File("./Mutants/Mutant" + i);
			FileUtils.copyDirectory(source, dest);
		}
		
		int i = 0;
		while(i < numofmutants)
		{
			File dir = new File("./Mutants/Mutant" + i + "/src/main/java/org/parse4j");
			ArrayList<String> listOfFiles = new ArrayList<String>();
			listOfFiles = getAllFileNames(dir, listOfFiles);
			Random random = new Random();
			int fileNumber = random.nextInt(listOfFiles.size());
			//System.out.println(listOfFiles.get(fileNumber));
			System.out.println("For Mutant " + i);
			status = false;
			File forMutant = new File(listOfFiles.get(fileNumber));
			processJavaFile(forMutant, listOfFiles.get(fileNumber));
			if(status)
			{
				i++;
				System.out.println();
			}
		}
	}
	
	public static boolean deleteDir(File dir) {
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }
	    return dir.delete();
	}
	
	public static void processJavaFile(File file, String path) throws Exception, BadLocationException 
	{
	    String source = FileUtils.readFileToString(file);
	    Document document = new Document(source);
	    ASTParser parser = ASTParser.newParser(AST.JLS3);
	    parser.setSource(document.get().toCharArray());
	    CompilationUnit unit = (CompilationUnit)parser.createAST(null);
	    AST ast = unit.getAST();
	    ASTRewrite rewriter = ASTRewrite.create(ast);
	    //unit.recordModifications();

	    ASTVisitor astVisitor = new ASTVisitor(){
	    	public boolean visit(final IfStatement ifstatement)
	    	{

	    		System.out.println("before: " + ifstatement);
	    		Expression e = ifstatement.getExpression();
	    		//System.out.println("\nExpr: " + e.getClass());
	    		//System.out.println("\nbefore: " + ifstatement.getExpression());
	    		
	    		if(e instanceof InfixExpression && status == false)
	    		{
	    			Operator oldOp = null, newOp = null;
	    			//System.out.println("left: " + ((InfixExpression) e).getLeftOperand());
	    			//System.out.println("right: " + ((InfixExpression) e).getRightOperand());
	    			//System.out.println("operator: " + ((InfixExpression) e).getOperator());
	    			
	    			if(((InfixExpression) e).getOperator() == InfixExpression.Operator.CONDITIONAL_OR)
	    			{
	    				status = true;
	    				oldOp = InfixExpression.Operator.CONDITIONAL_OR;
	    				newOp = InfixExpression.Operator.CONDITIONAL_AND;
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.CONDITIONAL_AND);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.CONDITIONAL_AND)
	    			{
	    				status = true;
	    				oldOp = InfixExpression.Operator.CONDITIONAL_AND;
	    				newOp = InfixExpression.Operator.CONDITIONAL_OR;
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.CONDITIONAL_OR);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.GREATER)
	    			{
	    				status = true;
	    				oldOp = InfixExpression.Operator.GREATER;
	    				newOp = InfixExpression.Operator.GREATER_EQUALS;
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.GREATER_EQUALS);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.GREATER_EQUALS)
	    			{
	    				status = true;
	    				oldOp = InfixExpression.Operator.GREATER_EQUALS;
	    				newOp = InfixExpression.Operator.GREATER;
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.GREATER);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.LESS)
	    			{
	    				status = true;
	    				oldOp = InfixExpression.Operator.LESS;
	    				newOp = InfixExpression.Operator.LESS_EQUALS;
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.LESS_EQUALS);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.LESS_EQUALS)
	    			{
	    				status = true;
	    				oldOp = InfixExpression.Operator.LESS_EQUALS;
	    				newOp = InfixExpression.Operator.LESS;
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.LESS);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.NOT_EQUALS)
	    			{
	    				status = true;
	    				oldOp = InfixExpression.Operator.NOT_EQUALS;
	    				newOp = InfixExpression.Operator.EQUALS;
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.EQUALS);
	    			}
	    			else if(((InfixExpression) e).getOperator() == InfixExpression.Operator.EQUALS)
	    			{
	    				status = true;
	    				oldOp = InfixExpression.Operator.EQUALS;
	    				newOp = InfixExpression.Operator.NOT_EQUALS;
	    				((InfixExpression) e).setOperator(InfixExpression.Operator.NOT_EQUALS);
	    			}
	    			//System.out.println("operator: " + ((InfixExpression) e).getOperator());
	    			System.out.println("Changing from " + oldOp + " to " + newOp);
	    		}
	    		
	    		//System.out.println("after: " + ifstatement.getExpression());
	    		System.out.println("after: " + ifstatement);
	    		return true;
	    	}
	    	
	    	
	    };
	    unit.accept(astVisitor);
	    
	    
	    
	    if(status)
	    {
	    	
	    	TextEdit edits = rewriter.rewriteAST(document, null);
	    	edits.apply(document);
	    	System.out.println("path: " + path);
	    	//System.out.println("doc: " + document.get());
	    	FileUtils.writeStringToFile(new File(path), document.get());
	    }
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