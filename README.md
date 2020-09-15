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

## How to Git/Github Workflow
* _Add shared repo:_ git remote add <name ie "upstream"> <URL ie "https://github.com/MSU-CS3250-DreamTeam/Database">
* _Check remote repos:_ git remote -v
* _Update from remote:_ git pull upstream master
* _Check commits and changes:_ git status
* _Add a change: git_ add <path/to/file or path_head/*/file or *>
* _Remove a change:_ git rm <path/to/file or path_head/*/file or *>
* _Apply changes to current head:_ git commit -m "\<detailed but concise message\>"
* _Update fork: git_ <fork name ie origin\>
* _Update shared repo:_ git <repo name ie upstream\> // May want to create pull request via GitHub instead.
### Please remember to pull from upstream and check repo status after your commits and **just before pushing**!

## References					
* Git/Github Guide:
	https://arccwiki.uwyo.edu/index.php/Git_and_GitHub
