This project deals with Automating Mutation Testing and steps to execute it. 

Requirements:
- Java should be installed
- Maven should be installed

Procedure:
- Get the control in the command prompt to ../SoftwareTesting-Project-MutationTesting/
- Execute "run.bat" on command prompt
- Enter the number of mutants
- Wait for the execution to be completed
- Output from the java files is saved in output.txt

- If this doesn't work as expected (due to some reason)
	- Import the project to eclipse
	- Add all the jar files present in the /lib folder to the build path
	- Execute STVVProject.java by entering the number of mutants as an argument variable
	- Once the mutants are generated go to each mutant using command line and execute "mvn test"
	- Once all the test cases are executed on the mutants execute MutationScore.java by passing number of mutants as an argument variable