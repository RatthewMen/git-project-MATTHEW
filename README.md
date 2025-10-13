# git-project-MATTHEW

to-do list:

1. finish 3.x assignments
2. add comments to code to prep for collab work (4.0 stuff)

How to read
(Assignment number. Part. stretch challenge) Date
Method (with modifier and return type) and their discription
Date + change log to method

Changelog at the bottom

4.3 (10/12/2025):
Bugs and fixes:
The method didn't properly sort the list of lines of blobs and trees before using it. Now, the lines with the most slashes are at the top and it alphabetically sorts downward. Also, the working list isn't updated throughout every use of the method void IndexTreeGenerator(ArrayList<String> wList). The working list wasn't in the objects folder of git. Some of the code wasn't very clear and redundant. The README wasn't updated. The tester called on the wrong classes for some methods. The tester was lacking some methods that it should've tested. I tested all the methods in the Git class too. The remakeHashmap() method didn't work, since he didn't clear the HashMap before making it. I fixed that too.

Git Class:
readCommit()
The readCommit() method retrieves and parses the contents of a commit object stored in the repository. Given a commit hash, it locates the corresponding commit file within the .git/objects directory and reads all of its lines into memory. Each line of the commit file follows a key-value format separated by a colon (e.g., tree: <hash> or author: <name>). The method splits these lines into their respective fields and stores them in a HashMap<String, String> for easy access to commit metadata such as the tree hash, parent commit, author, date, and summary. If the commit file cannot be found, the method throws a FileNotFoundException. It returns a HashMap of the commit data.

restoreTree()
The restoreTree() method reconstructs the directory structure and file contents of a specific commit by restoring all files and subdirectories associated with a given tree hash. It begins by locating the tree object within the .git/objects directory using the provided hash. Each line of the tree file represents either a blob (a single file) or another tree (a subdirectory). For each blob, the method retrieves the corresponding file content from the objects directory and writes it back to the working directory at its original location. For nested trees, it recursively creates subdirectories and restores their contents. If the specified tree file cannot be found, the method throws a FileNotFoundException. This recursive process allows restoreTree() to accurately rebuild the complete file of a commit.

deleteRecursively()
The deleteRecursively() method removes a file or directory and all of its contents from the filesystem. If the provided file is a directory, the method first iterates through all files and subdirectories within it, calling itself recursively to ensure that every nested item is deleted. Once all children are removed, it deletes the directory itself. If the file is not a directory, it is simply deleted.

GitWrapper Class:
init()
The init() method initializes a new Git repository by setting up the necessary directory structure and base files required for version control. It first removes any existing repository data by calling GitDirectory.deleteGit(), ensuring that the workspace is clean and free of old metadata. Then, it calls GitDirectory.makeGitDirectoryAndFiles() to create a new git directory containing essential components such as the objects, index, and HEAD files.

add()
The add() method stages a file to be included in the next commit by adding it to the repository’s index. It first validates the file path, ensuring that the specified file exists and is not a directory. The method then reads the file’s contents, computes its SHA-1 hash using the Hashing.hashString() function, and checks whether the file has already been staged by comparing the hash against existing entries in the index file. If the file is not already present, add() calls the Git.BLOBmaker() method to create a blob object representing the file’s current version and store it in the objects directory.

commit()
The commit() method captures the current state of the repository and saves it as a new commit object. It takes in the author’s name and a commit message as parameters, then delegates the core functionality to the static Git.commit() method. This process involves recording the repository’s file structure, generating a tree object that represents the current index, and creating a commit entry that includes metadata such as the author, timestamp, and commit message. The method returns the SHA-1 hash of the newly created commit, which serves as a unique identifier for that snapshot of the repository.

checkout()
The checkout() method restores the repository to match the state of a specific commit, effectively reverting all tracked files to the versions recorded in that commit. Given a commit hash, the method first retrieves metadata about that commit using Git.readCommit() and extracts its corresponding root tree hash. It then clears the working directory by recursively deleting all files and folders except essential project files—such as source code files, the .git directory, and configuration items like README.md and LICENSE. After the workspace is cleaned, the method calls Git.restoreTree() to reconstruct the project’s directory structure and restore file contents based on the tree object stored in the commit. Finally, it updates the HEAD file, ensuring that the repository’s reference state matches the restored version. I am not sure if this works. I will graciously accept partial credit.

4.2 (10/12/29025)

commit()
The commit() method creates a new commit object that captures the current state of the repository. It begins by generating a new tree object using IndexTreeGenerator() and workingListMaker(), which represent the current file and contents. The method then reads the existing HEAD file to identify the hash of the most recent commit (if any), allowing the new commit to reference it as its parent. It creates a commit record containing key data — including the tree hash, parent commit, author, date, and summary message — and writes this information to a new file within the .git/objects directory. The SHA-1 hash of the commit content serves as its unique identifier, which is also written to the HEAD file to mark it as the latest commit. Additionally, a new CommitNode is created and appended to the commitHistory linked list to maintain a record of commits.

commitTester():
The commitTester() method simulates the process of creating a commit by first generating ten random files and turning each into a blob object. It then calls the commit() method with the provided author and message to create a new commit representing these files. This function is primarily used for testing the commit workflow.

printCommitHistory()
The printCommitHistory() method displays all commits stored in the commitHistory linked list. For each commit, it prints its hash, parent hash, tree hash, author, and message, providing a readable overview of the repository’s commit history.

4.1 (10/9/2025)

int slashCount(String line):
This method returns the number of slashes present in the relative path of a tree or blob. It is called in createIndexTree() and createIndexTreeHelper() and is useful for sorting based on most slashes first in the working list. it returns the number of slashes in a relative path.

String pathLine(String line):
This method returns the relative path of a blob or tree by parsing the line into three different parts. The last part is the relative path, which it returns. This is useful for the sortSlashCountDescend(List lines) method, as it helps organize the order of the lines. It returns the relative path of a blob or tree.

void sortSlashCountDescend(List lines):
This method organizes a list of lines based on the number of slashes and alphabetically for the relative path of a tree or blob. It puts the blobs or trees with the most slashes at the front, alphabatizing the order in case some have the same number of slashes. This is useful in the createIndexTree() and createIndexTreeHelper() methods to keep the working list organized and updated.

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
deleteObjects()
Modifier and type: static void
deletes the objects by deleting all the files inside the folder
10/7/2025: changed from deleteIndex to deleteObjects (wrongly named)

    #2

DirectoryTreeGenerator(String directoryPath)
Modifier and type: static String
makes a tree from the provided directory. If a file is in the directory then it hashes the file and adds it to a String builder in the format (blob <SHA1> <pathName>). After all files has been hashed, it makes a tree in the objects folder with the hash of all of the contents of the tree and returns it. If there are directories in the provided directory the method recursively calls itself and makes a tree object for the inner and outer directories. The inner directory tree is also added to the outer directory with the format (tree <SHA1> <pathName>).

3.3 (10/7/2025)
#1
workingListMaker()
The workingListMaker() method generates a comprehensive list of all blobs currently tracked in the index and organizes them into a structured format for later tree construction. It begins by ensuring that the workingList file exists and that the in-memory indexMap is properly initialized by calling remakeHashMap() if needed. For every blob entry in the index, the method constructs a corresponding BlobObject containing metadata such as the blob’s type, file path, parent directory, file name, and directory depth. These blob objects are then sorted by their directory depth (deepest files first) and alphabetically by file and folder names to ensure correct hierarchical ordering. The resulting list is written to the git/objects/workingList file and returned as an ArrayList<String>.

#2
IndexTreeGenerator()
The IndexTreeGenerator() method constructs the tree objects that represent the structure of the repository at a given state. It takes the working list (wList) generated by workingListMaker() and recursively organizes it into nested tree objects stored in the git/objects directory. Each iteration identifies files belonging to the same parent folder, groups them into a subtree, and computes a unique SHA-1 hash for the subtree’s contents using the Hashing.hashString() function. This hash becomes the identifier for that tree object, which is then written as a new file inside the objects directory. The method replaces the grouped entries in the working list with a single tree entry linking the subtree hash to its directory path, re-sorts the list for proper order, and continues the process until only a single root tree remains. The final output is the hash of the root tree.

Change Log:

10/7/2025:
-GP 3.3: gl to reading it cus there is no documentation rn

10/5/2025:

- GP 3.2: a method that, given a String directory path:

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
