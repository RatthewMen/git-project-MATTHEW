import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitWrapper {

    /**
     * Initializes a new Git repository. This method creates the necessary directory structure and
     * initial files (index, HEAD) required for a Git repository.
     */
    public void init() {
        GitDirectory.deleteGit();
        GitDirectory.makeGitDirectoryAndFiles();
    }

    /**
     * Stages a file for the next commit. This method adds a file to the index file. If the file
     * does not exist, it throws an IOException. If the file is a directory, it throws an
     * IOException. If the file is already in the index, it does nothing. If the file is
     * successfully staged, it creates a blob for the file.
     * 
     * @param filePath The path to the file to be staged.
     * @throws IOException
     */
    public void add(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Error: File does not exist.");
        }
        if (file.isDirectory()) {
            throw new IOException("Error: File is a directory.");
        }
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        String name = Hashing.hashString(bytes);
        for (String line : Files.readAllLines(GitDirectory.index.toPath(),
                StandardCharsets.UTF_8)) {
            String[] parts = line.split(" ");
            String hash = parts[0];
            if (name.equals(hash)) {
                return;
            }
        }
        Git.BLOBmaker(filePath);

        // if (Git.indexMap.containsKey(name)) {
        // return;

        // }

    }

    /**
     * Creates a commit with the given author and message. It should capture the current state of
     * the repository by building trees based on the index file, writing the tree to the objects
     * directory, writing the commit to the objects directory, updating the HEAD file, and returning
     * the commit hash.
     * 
     * The commit should be formatted as follows: tree: <tree_sha> parent: <parent_sha> author:
     * <author> date: <date> summary: <summary>
     *
     * @param author The name of the author making the commit.
     * @param message The commit message describing the changes.
     * @return The SHA1 hash of the new commit.
     * @throws IOException
     */
    public String commit(String author, String message) throws IOException {
        return Git.commit(author, message);
    }

    /**
     * EXTRA CREDIT: Checks out a specific commit given its hash. This method should read the HEAD
     * file to determine the "checked out" commit. Then it should update the working directory to
     * match the state of the repository at that commit by tracing through the root tree and all its
     * children.
     *
     * @param commitHash The SHA1 hash of the commit to check out.
     */
    public void checkout(String commitHash) {
        try {
            Map<String, String> commitData = Git.readCommit(commitHash);
            String treeHash = commitData.get("tree");
            if (treeHash == null || treeHash.isEmpty()) {
            throw new IOException("Commit has no tree: " + commitHash);
        }
            File workingDirectory = new File(".");
            for (File file : workingDirectory.listFiles()) {
                if (!file.getName().contains(".git") || !file.getName().equals(null)
                        || !file.getName().contains("git") || !file.getName().contains(".DS_Store")
                        || !file.getName().equals("README.md")
                        || !file.getName().equals("GitWrapper.java")
                        || !file.getName().equals("Git.java")
                        || !file.getName().equals("Hashing.java")
                        || !file.getName().equals("CommitNode.java")
                        || !file.getName().equals("GitDirectory.java")
                        || !file.getName().equals("RandomFiles.java")
                        || !file.getName().equals("GitWrapperTester.java")
                        || !file.getName().equals("LICENSE")
                        || !file.getName().equals("BlobObject.java")) {
                    Git.deleteRecursively(file);
                }
            }
            Git.restoreTree(treeHash, new File("."));
            Files.write(GitDirectory.HEAD.toPath(), commitHash.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Checked out commit: " + commitHash);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}

