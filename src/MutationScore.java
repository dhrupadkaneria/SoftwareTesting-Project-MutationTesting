import java.io.File;
import java.io.FilenameFilter;
import org.apache.commons.io.FileUtils;

public class MutationScore {

	public static void main(String[] args) throws Exception 
	{
		int numOfMutants = 2;
		int killMutant = 0;
		int correctMutant = 0;
		
		int test_master = 92;
		int failed_master = 9;
		int error_master = 9;
		int skipped_master = 0;
		
		System.out.println("Master Project");
		System.out.println("Test Run : " + test_master);
		System.out.println("Failed : " + failed_master);
		System.out.println("Error : " + error_master);
		System.out.println("Skipped : " + skipped_master);
		System.out.println();
		
		for(int i = 0; i < numOfMutants; ++i)
		{
			File dir = new File("./Mutants/Mutant" + i + "/target/surefire-reports");
			File[] matchingFiles = dir.listFiles(new FilenameFilter() 
			{
			    public boolean accept(File dir, String name) 
			    {
			        return name.startsWith("org.parse4j.") && name.endsWith("txt");
			    }
			});
			
			int test = 0;
			int failed = 0;
			int error = 0;
			int skipped = 0;
			for(int j = 0; j < matchingFiles.length; ++j)
			{
				if(!matchingFiles[j].getCanonicalPath().contains("-output.txt"))
				{
					String source = FileUtils.readFileToString(matchingFiles[j]);
					String lineSplit[] = source.split("\n")[3].split(",");
					
					test += Integer.parseInt(lineSplit[0].split(":")[1].trim());
					failed += Integer.parseInt(lineSplit[1].split(":")[1].trim());
					error += Integer.parseInt(lineSplit[2].split(":")[1].trim());
					skipped += Integer.parseInt(lineSplit[3].split(":")[1].trim());
				}
			}
			System.out.println("Mutant " + i);
			System.out.println("Test Run : " + test);
			System.out.println("Failed : " + failed);
			System.out.println("Error : " + error);
			System.out.println("Skipped : " + skipped);
			
			if((test == test_master) && (failed == failed_master) && (error == error_master) && (skipped == skipped_master))
			{
				System.out.println("Mutant " + i + " is alive");
				correctMutant++;
			}
			else
			{
				System.out.println("Mutant " + i + " is killed");
				killMutant++;
			}
			System.out.println();
		}
		System.out.println("\nFinal Statistics:\nKilled Mutants: " + killMutant);
		System.out.println("Alive Mutants: " + correctMutant);
		System.out.println("Total Mutants: " + numOfMutants);
		System.out.println("Mutation Score: " + (killMutant/numOfMutants)*100 + "%");
	}

}
