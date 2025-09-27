# git-project-MATTHEW

2.1
    #1
makeGitDirectoryAndFiles()
Modifier and type: static void
makes the git directory with another directory called objects and 2 files called HEAD and index inside of it. First of all, it checks if the files have been created and then tries to create the files. 

2.2
    #1
checkGitFiles()
Modifier and type: static boolean
verifys repository initialization (checks if required files and directories exist) and returns true of it does

    #2
deleteGit()
Modifier and type: static void
a cleanup function to remove all the created directories and files. It removes all the Blobs in objects folder first

    #3
StressTest(int times)
Modifier and type: static void
runs multiple initialization/cleanup cycles to ensure robust functionality depending on the variable "times"

2.2
    #1
hashString(byte[] Bytes)
Modifier and Type: static String
generates a SHA1 hash String using a file's contents from the variable "Bytes" (converted to a String) as input. This hash will serve as the unique identifier for the file. We read the string and then turn it into an array of bytes. Then we use Messagedigest to turn into a sha1 hash then convert it into hexadecimal. We wil then return the String

    #2
hashStringfilePath(String filePath)
Modifier and Type: static String
accepts a filepath ("filePath" variable) instead of a String and then turns the contents of the file into a ByteArray and calls hashString

2.3
    #1
BLOBmaker(String filePath)
Modifier and type: static void
uses the filePath ("filePath" variable) and calculates the SHA1 has of the filecontents with hashstring and checks if the global variable wants it to be encrypted hash or not. Then it calculates the SHA1 hash of a file's content using method hashstring, creates a new file in the objects folder with the hash as its filename using File reader that turns the file into an array of bytes, and then store an exact copy of the original file's content in this new blob file with the Files Writer

2.3.1
    #1
BlobChecker(String blobName)
Modifier and type: static boolean
confirms if a BLOB file exist in the objects directory for the BLOB with the variable "blobName"

2.3.2
    #1
fileCompressor(String filePath)
Modifier and type: static byte[]
compresses the original file's data (file from "filePath" variable) using the Deflator class that uses ZLIB compression library. It uses a bufferedoutput stream so that if it is a big file to be compressed we will not run out of space. 

2.4
    #1
addToIndex(String filePath, String SHAHash)
Modifier and type: static void
adds a BLOB and file entries to the index in the order "Blob, file"

2.4.1 
    #1
randomFileMaker(int numberofFiles)
Modifier and type: static void
makes a user set amount of files that is a string of random characters (50-100) long. The character used are lower case, uppercase and numbers. It creates the randomFiles folder and puts all the files in there

    #2
deleteRandomFileMaker()
Modifier and type: static void
deletes everything in RandomFile folder recursively and then it deletes the folder

    #3
multiBLOBMaker(File[] files)
Modifier and type: static void
accepts a list of Files and then makes Blobs from all the files using method Blobmaker 

    #4
FileMakerChecker()
Modifier and type: static void
checks if the Blob and its respective textfile in the objects and randomFiles directory actually exists.

    #5
randomFilesChecker(String fileName)
Modifier and type: static boolean
checks if a file exists in the randomFiles folder

2.4.2
    #1
masterRESET()
static void 
deletes all generated files from previous tests and resets git and the rest of the directory to a clean state