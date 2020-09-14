# Database

A database to manage cvs stored data.

## Notice Board
* A quick rundown of how to run the project from the commandline, presuming maven is configured:
	1. mvn compile package
	2. java -cp target/my-database-1.0-SNAPSHOT.jar com/dreamteam/database/main
	3. mvn clean
* Avoid adding generated files to repo. If uncertain: "mvn clean" before commiting.
					.gitignore file should list these files in red text under "Untracked files:"
					Files/directories include .classpath, .project, .settings/, target/
* Not committing generated files of project is a general convention.

## References					
* Git/Github Guide:
	https://arccwiki.uwyo.edu/index.php/Git_and_GitHub