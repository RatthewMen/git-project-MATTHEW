# git-project-MATTHEW

2.1
method makeGitDirectoryAndFiles makes the git directory with another directory called objects and 2 files called HEAD and index. This is also the method that initalizes the repository. 

2.2
method checkGitFiles verifys repository initialization (checks if required files and directories exist)
method DeleteGit Implements a cleanup function to remove all the created directories and files. Removes all the Blobs in objects folder first
method StressTest runs multiple initialization/cleanup cycles to ensure robust functionality

2.2
method hashString generates a SHA1 hash String using a file's contents from a bytearray (converted to a String) as input. This hash will serve as the unique identifier for the file. We read the string and then turn it into an array of bytes. Then we use Messagedigest to turn into a sha1 hash then convert it into hexadecimal.
helpermethod hashStringfilePath accepts a filepath instead

2.3
method Blobmaker calculates the SHA1 hash of a file's content using method hashstring, creates a new file in the objects folder with the hash as its filename using File reader that turns the file into an array of bytes, and then store an exact copy of the original file's content in this new blob file with the Files Writer

2.3.1
method Blobchecker confirms if a BLOB file exist in the objects directory. 

2.3.2
method fileCompressor compresses the original file's data using the Deflator class that uses ZLIB compression library. We also have a global compress boolean variable so we can make blobs with compressed and not compressed versions

2.4
method addToIndex adds a BLOB and file entries to the index in the order "Blob file"

2.4.1 
method randomFileMaker makes a user set amount of files that is a string of random characters (50-100) long. The character used are lower case, uppercase and numbers. It creates the randomFiles folder and puts all the files in there
method deleteRandomFileMaker deletes everything in the file recursively
method multiBLOBMaker accepts a list of Files and then makes Blobs from all the files using method Blobmaker 
method  
method FileMakerChecker checks if the Blob and its respective textfile in the objects and randomFiles directory actually exists.
method randomFilesChecker checks if a file exists in the randomFiles folder

2.4.2
method masterRESET deletes all generated files from previous tests and resets git and the rest of the directory to a clean state