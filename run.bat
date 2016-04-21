@echo OFF
setlocal enabledelayedexpansion
goto :main

:main
setlocal
    set /a counter=0
    set /a limit=3

	:loop
    if !counter! lss !limit! (
		echo !counter!
        cd Mutants/Mutant!counter!
		cd
		call mvn test
		cd ..
		cd ..
		set /a counter=!counter!+1
		
		goto :loop
	)

	echo Outside of Loop

endlocal
goto :eof