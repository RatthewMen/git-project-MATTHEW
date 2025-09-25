# git-project-MATTHEW

method makeGitDirectoryAndFiles makes the git directory with another directory called objects and 2 files called HEAD and index. This is also the method that initalizes the repository. 


method checkGitFiles verifys repository initialization (checks if required files and directories exist)
method DeleteGit Implements a cleanup function to remove all the created directories and files
method StressTest runs multiple initialization/cleanup cycles to ensure robust functionality

method hashString generates a SHA1 hash String using a file's contents (converted to a String) as input. This hash will serve as the unique identifier for the file. We read the string and then turn it into an array of bytes. Then we use Messagedigest to turn into a sha1 hash then convert it into hexadecimal.
