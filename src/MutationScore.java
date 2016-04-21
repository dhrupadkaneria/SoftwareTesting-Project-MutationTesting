import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

public class MutationScore {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File dir = new File("./Mutants/Mutant1/target/surefire-reports");
		
		File[] matchingFiles = dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.startsWith("org.parse4j.") && name.endsWith("txt");
		    }
		});
		
		int test = 0;
		int failed = 0;
		int error = 0;
		int skipped = 0;
		for(int i = 0; i < matchingFiles.length; ++i)
		{
			if(!matchingFiles[i].getCanonicalPath().contains("-output.txt"))
			{
				String source = FileUtils.readFileToString(matchingFiles[i]);
				String lineSplit[] = source.split("\n")[3].split(",");
				
				test += Integer.parseInt(lineSplit[0].split(":")[1].trim());
				failed += Integer.parseInt(lineSplit[1].split(":")[1].trim());
				error += Integer.parseInt(lineSplit[1].split(":")[1].trim());
				skipped += Integer.parseInt(lineSplit[1].split(":")[1].trim());
			}
		}
		System.out.println("Test run : " + test);
		System.out.println("Failed : " + failed);
		System.out.println("Errored : " + error);
		System.out.println("Skipped : " + skipped);
		
		System.out.println();
	}

}
