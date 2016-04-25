@echo OFF
setlocal enabledelayedexpansion
goto :main

:main
setlocal
	set /a counter=0
    set  /p limit=num of mutants:
	
	javac -d bin -cp lib\* src\STVVProject.java
	java -cp bin;lib\* STVVProject !limit!

	:loop
    if !counter! lss !limit! (
        cd Mutants/Mutant!counter!
		cd
		call mvn test
		cd ..
		cd ..
		set /a counter=!counter!+1
		
		goto :loop
	)

	echo Outside of Loop
	javac -d bin -cp lib\* src\MutationScore.java
	java -cp bin;lib\* MutationScore !limit!
	
endlocal
goto :eof