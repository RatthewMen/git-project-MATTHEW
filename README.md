# git-project-MATTHEW

to-do list:
1. finish 3.x assignments
2. add comments to code to prep for collab work (4.0 stuff)

How to read
(Assignment number. Part. stretch challenge) Date 
Method (with modifier and return type) and their discription
Date + change log to method

Changelog at the bottom

Methods:

2.1 (9/24/2025)
    #1
makeGitDirectoryAndFiles()
Modifier and type: static void
makes the git directory with another directory called objects and 2 files called HEAD and index inside of it. First of all, it checks if the files have been created and then tries to create the files. 
    9/25/2025: split the checking if the files exists into method checkGitFiles()

2.2 (9/25/2025)
    #1
checkGitFiles()
Modifier and type: static boolean
verifys repository initialization (checks if required files and directories exist) and returns true of it does

    #2
deleteGit()
Modifier and type: static void
a cleanup function to remove all the created directories and files.
    9/25/2025: It removes all the Blobs in objects folder first
    10/5/2025: moved the deleting index portion into a seperate method and then calls it

    #3
StressTest(int times)
Modifier and type: static void
runs multiple initialization/cleanup cycles to ensure robust functionality depending on the variable "times"

2.2 (9/25/2025)
    #1
hashString(byte[] Bytes)
Modifier and Type: static String
generates a SHA1 hash String using a file's contents from the variable "Bytes" (converted to a String) as input. This hash will serve as the unique identifier for the file. We read the string and then turn it into an array of bytes. Then we use Messagedigest to turn into a sha1 hash then convert it into hexadecimal. We wil then return the String

2.3 (9/25/2025)
    #1
BLOBmaker(String filePath)
Modifier and type: static void
uses the filePath ("filePath" variable) and calculates the SHA1 has of the filecontents with hashstring and then copies the contents of the file to a new Blob file inside of the objects folder
    (9/26/2025) now checks if the global variable wants it to be encrypted hash or not. Then it calculates the SHA1 hash of a file's content using method hashstring of the encrypted string if the variable is true

2.3.1 (9/25/2025)
    #1
BlobChecker(String blobName)
Modifier and type: static boolean
confirms if a BLOB file exist in the objects directory for the BLOB with the variable "blobName"

2.3.2 (9/26/2025)
    #1
fileCompressor(String filePath)
Modifier and type: static byte[]
compresses the original file's data (file from "filePath" variable) using the Deflator class that uses ZLIB compression library. It uses a bufferedoutput stream so that if it is a big file to be compressed we will not run out of space. 
  #2
hashStringfilePath(String filePath)
Modifier and Type: static String
accepts a filepath ("filePath" variable) instead of a String and then turns the contents of the file into a ByteArray and calls hashString

2.4 (9/26/2025)
    #1
addToIndex(String filePath, String SHAHash)
Modifier and type: static void
adds a BLOB and file entries to the index in the order "Blob fileName"
    10/2/2025: changed adding new entries and appending to putting entries into a Hashmap instead and then printing the Hashmap into the index. format was changed to (blob <SHA1> <Pathname>)
    10/5/2025: changed format to (<SHA1> <Pathname>)

2.4.1 (9/26/2025)
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

2.4.2 (9/26/2025)
    #1
masterRESET()
Modifier and type: static void 
deletes all generated files from previous tests and resets git and the rest of the directory to a clean state

3.1 (10/1/2025 - 10/2/2025)
    #1
Blobremover(String type, String filePath)
Modifier and type: static void
removes the blob of a certain filepath and removes the entry from the HashMap

    #2
staging(String type, String filePath)
Modifier and type: static void
imitates the staging function of git by editing the index file and rehashing the blob when a new edit is made and staged

    #3
removeFromIndex(String type, String filePath)
Modifier and type: static void
removes the file from the hashmap that stores the index

    #4
writeToIndex()
Modifier and type: static void
helper method that writes to index (added to clean up code)

    #5
IndexMaptoString()
Modifier and type: static String 
prints out all key value pairs of the indexHashMap

    #6
remakeHashMap()
Modifier and type: static void
remakes the Hashmap after the code is ran again. It reads the index and remakes the hashmap so that we can use it for staging or anything else

3.2 (10/5/2025)
    #1 
deleteIndex()
Modifier and type: static void
deletes the index by deleting all the files first and then the index folder itself
    #2
DirectoryTreeGenerator(String directoryPath)
Modifier and type: static String
makes a tree from the provided directory. If a file is in the directory then it hashes the file and adds it to a String builder in the format (blob <SHA1> <pathName>). After all files has been hashed, it makes a tree in the objects folder with the hash of all of the contents of the tree and returns it. If there are directories in the provided directory the method recursively calls itself and makes a tree object for the inner and outer directories. The inner directory tree is also added to the outer directory with the format (tree <SHA1> <pathName>).


Change Log:
10/5/2025:
- GP 3.2:  a method that, given a String directory path:
1. Generates a tree file containing references to all files and subdirectories
2. Returns the SHA-1 hash of the generated tree based on its contents
Blob format (blob <SHA1> <pathName>)
Tree format (tree <SHA1> <pathName>)
- updated readme a little bit

10/2/2025:
- GP 3.1: Split the code into different sections like Hashing, GitDirectory, and RandomFiles to make the codebase more readable
GitDiectory.java is all the methods that is used to make the gitfolders
Hashing.java is all the methods used for Hashing the blob
RandomFiles.java is all the methods for creating and deleting the random files for testing
Git.java now just holds all the methods for creating the fake github
- reorganized and updated ReadMe

9/26/2025:
- GP 2.3.2: method fileCompressor compresses the original file's data using the Deflator class that uses ZLIB compression library. We also have a global compress boolean variable so we can make blobs with compressed and not compressed versions
- GP 2.4: method addToIndex adds a BLOB and file entries to the index in the order "Blob file"
- GP 2.4.1: method randomFileMaker makes a user set amount of files that is a string of random characters (50-100) long. The character used are lower case, uppercase and numbers. It creates the randomFiles folder and puts all the files in there
method deleteRandomFileMaker deletes everything in the file recursively
method multiBLOBMaker accepts a list of Files and then makes Blobs from all the files using method Blobmaker
method
method FileMakerChecker checks if the Blob and its respective textfile in the objects and randomFiles directory actually exists.
method randomFilesChecker checks if a file exists in the randomFiles folder
- GP 2.4.2: method masterRESET deletes all generated files from previous tests and resets git and the rest of the directory to a clean state
- GP 2.4.3: not a real assignment but it cleans up the ReadMe to be more clear and readable for a person


9/25/2025
- GP 2.1.1: method checkGitFiles verifys repository initialization (checks if required files and directories exist)
method DeleteGit Implements a cleanup function to remove all the created directories and files
method StressTest runs multiple initialization/cleanup cycles to ensure robust functionality
- updated gitignore to ignore txt files (tester files)
- GP 2.2: method hashString generates a SHA1 hash String using a file's contents (converted to a String) as input. This hash will serve as the unique identifier for the file.
- GP 2.3: method BLOBmaker calculates the SHA1 hash of a file's content using method hashstring, creates a new file in the objects folder with the hash as its filename using File reader that turns the file into an array of bytes, and then store an exact copy of the original file's content in this new blob file with the Files Writer
- GP 2.3.1: method Blobchecker confirms if a BLOB file exist in the objects directory.
made the directory, objects, index, HEAD file objects a global variable instead of private per method to removed repeated instances
- made the files objects for the git folder (directory, objects, index, and head) global variables 

9/24/2025:
- GP 2.1: makeGitDirectoryAndFiles makes the git directory with another directory called objects and 2 files called HEAD and index. This is also the method that initalizes the repository.

9/19/2024:
- GP 1.1: made a Git.java file and changed .gitignore
initailized projects
updated gitignore
created Git.java file