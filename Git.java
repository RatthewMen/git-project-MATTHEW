import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

public class Git {

    public static HashMap<String, ArrayList<String>> indexMap = new HashMap<>();
    public static boolean hashMapStatus = false;
    // Changed working list to be a File in the objects folder
    public static File workingList = new File("git/objects/workingList");

    public static void main(String[] args) throws IOException {

        // GitDirectory.makeGitDirectoryAndFiles();
        // makeGitDirectoryAndFilesTester();

        // GitDirectory.deleteObjects();
        // deleteObjectsTester();

        // GitDirectory.StressTest(5);
        // StressTestTester(5);

        // Hashing.hashString("hello".getBytes());
        // hashingTester("hello");

        // Hashing.CompressOrNot("randomFiles/file1.txt");
        // compressTester("randomFiles/file1.txt");

        // BLOBmaker("randomFiles/file1.txt");
        // BLOBmakerTester("randomFiles/file1.txt");

        // Blobremover("blob", "randomFiles/file1.txt");
        // BlobremoverTester("blob", "randomFiles/file1.txt");

        // staging("blob", "randomFiles/file1.txt");
        // stagingTester("blob", "randomFiles/file1.txt");

        // BlobChecker("someblobname");
        // BlobCheckerTester("someblobname");

        // removeFromIndex("blob", "randomFiles/file1.txt");
        // removeFromIndexTester("blob", "randomFiles/file1.txt");

        // addToIndex("randomFiles/file1.txt", "somehash");
        // addToIndexTester("randomFiles/file1.txt", "somehash");

        // writeToIndex();
        // writeToIndexTester();

        // IndexMaptoString();
        // IndexMaptoStringTester();

        // remakeHashMap();
        // remakeHashMapTester();

        // File[] files = new File[] {new File("randomFiles/file1.txt"),
        // new File("randomFiles/file2.txt")};
        // multiBLOBMaker(files);
        // multiBLOBMakerTester(files);

        // DirectoryTreeGenerator("randomFiles");
        // DirectoryTreeGeneratorTester("randomFiles");

        // workingListMaker();
        // workingListMakerTester();

        // ArrayList<String> wList = new ArrayList<String>();
        // wList.add("blob somehash randomFiles/file1.txt");
        // wList.add("blob somehash2 randomFiles/folder1/file2.txt");
        // wList.add("tree sometreehash randomFiles/folder1");
        // wList.add("blob somehash3 randomFiles/folder2/file3.txt");
        // wList.add("tree sometreehash2 randomFiles/folder2");
        // wList.add("tree roottreehash randomFiles");
        // IndexTreeGenerator(wList);
        // IndexTreeGeneratorTester(wList);
        GitDirectory.makeGitDirectoryAndFiles();
        RandomFiles.randomFileMaker(10);
        BLOBmaker("randomFiles/file1.txt");
        BLOBmaker("randomFiles/file2.txt");
        BLOBmaker("randomFiles/file3.txt");
        BLOBmaker("randomFiles/file4.txt");
        BLOBmaker("randomFiles/file5.txt");
        BLOBmaker("randomFiles/file6.txt");
        BLOBmaker("randomFiles/file7.txt");
        BLOBmaker("randomFiles/file8.txt");
        BLOBmaker("randomFiles/file9.txt");
        BLOBmaker("randomFiles/file10.txt");
        fileMakerCheckerTester();


    }

    // fixed the tester to call the right method from right class
    public static void makeGitDirectoryAndFilesTester() {
        GitDirectory.makeGitDirectoryAndFiles();
        if (GitDirectory.directory.exists() && GitDirectory.objects.exists()
                && GitDirectory.index.exists() && GitDirectory.HEAD.exists()) {
            System.out.println("Git Directory and Files created successfully");
        }
    }

    // fixed the tester to call the right method from right class
    public static void deleteObjectsTester() {
        GitDirectory.deleteObjects();
        if (!GitDirectory.objects.exists()) {
            System.out.println("Git Objects deleted successfully");
        }
    }

    public static void StressTestTester(int times) {
        GitDirectory.StressTest(times);
        System.out.println("Did " + times + " cycles of making GIT and deleting it.");
    }

    public static void hashingTester(String input) {
        System.out.println(Hashing.hashString(input.getBytes()));

    }

    public static void compressTester(String filePath) {
        Hashing.CompressOrNot(filePath);
        if (Hashing.compress == true) {
            System.out.println("Using Compression");
        } else {
            System.out.println("Not Using Compression");
        }
    }

    public static void BLOBmakerTester(String filePath) {
        BLOBmaker(filePath);
        System.out.println("Made new blob file for: " + filePath);
    }

    public static void BlobremoverTester(String type, String filePath) {
        Blobremover(type, filePath);
        System.out.println("Removed blob file for: " + filePath);
    }

    public static void stagingTester(String type, String filePath) {
        staging(type, filePath);
        System.out.println("Staged " + type + " for: " + filePath);
    }

    public static void BlobCheckerTester(String blobName) {
        if (BlobChecker(blobName) == true) {
            System.out.println("Blob " + blobName + " exists");
        } else {
            System.out.println("Blob " + blobName + " does not exist");
        }
    }

    public static void removeFromIndexTester(String type, String filePath) {
        removeFromIndex(type, filePath);
        System.out.println("Removed " + type + " from index for: " + filePath);
    }

    public static void addToIndexTester(String filePath, String SHAHash) {
        addToIndex(filePath, SHAHash);
        System.out.println("Added blob to index for: " + filePath);
    }

    public static void writeToIndexTester() {
        writeToIndex();
        System.out.println("Wrote to index");
    }

    public static void IndexMaptoStringTester() {
        System.out.println(IndexMaptoString());
    }

    public static void remakeHashMapTester() {
        remakeHashMap();
        System.out.println("Remade HashMap from index");
    }

    public static void multiBLOBMakerTester(File[] files) {
        multiBLOBMaker(files);
        System.out.println("Made multiple blobs");
    }

    public static void DirectoryTreeGeneratorTester(String directoryPath) {
        DirectoryTreeGenerator(directoryPath);
        System.out.println("Generated directory tree for: " + directoryPath);
    }

    public static void workingListMakerTester() {
        BLOBmaker("randomFiles/file1.txt");
        BLOBmaker("randomFiles/file2.txt");
        BLOBmaker("randomFiles/file3.txt");
        BLOBmaker("randomFiles/file4.txt");
        BLOBmaker("randomFiles/file5.txt");
        BLOBmaker("randomFiles/file6.txt");
        BLOBmaker("randomFiles/file7.txt");
        BLOBmaker("randomFiles/file8.txt");
        BLOBmaker("randomFiles/file9.txt");
        BLOBmaker("randomFiles/file10.txt");
        workingListMaker();
        System.out.println("Made working list");
    }

    public static void IndexTreeGeneratorTester(ArrayList<String> wList) {
        BLOBmaker("randomFiles/file1.txt");
        BLOBmaker("randomFiles/file2.txt");
        BLOBmaker("randomFiles/file3.txt");
        BLOBmaker("randomFiles/file4.txt");
        BLOBmaker("randomFiles/file5.txt");
        BLOBmaker("randomFiles/file6.txt");
        BLOBmaker("randomFiles/file7.txt");
        BLOBmaker("randomFiles/file8.txt");
        BLOBmaker("randomFiles/file9.txt");
        BLOBmaker("randomFiles/file10.txt");
        IndexTreeGenerator(wList);
        System.out.println("Generated index tree");
    }

    public static void masterRESETTester() {
        GitDirectory.masterRESET();
        System.out.println("Reset master");
    }

    public static void randomFileMakerTester(int numFiles) {
        RandomFiles.randomFileMaker(numFiles);
        System.out.println("Made " + numFiles + " random files");
    }

    public static void deleteRandomFileMakerTester() {
        RandomFiles.deleteRandomFileMaker();
        System.out.println("Deleted random files");
    }

    public static void fileMakerCheckerTester() {
        if (RandomFiles.FileMakerChecker() == true) {
            System.out.println("All blobs in index exist");
        } else {
            System.out.println("Some blobs in index do not exist");
        }
    }

    public static void randomFilesCheckerTester(String fileName) {
        if (RandomFiles.randomFilesChecker(fileName) == true) {
            System.out.println("File " + fileName + " exists in randomFiles");
        } else {
            System.out.println("File " + fileName + " does not exist in randomFiles");
        }
    }



    public static void BLOBmaker(String filePath) {
        String SHAHash = Hashing.CompressOrNot(filePath);
        // System.out.println("HASH: " + SHAHash);
        File Blob = new File(GitDirectory.objects, SHAHash);
        try {
            Blob.createNewFile();
        } catch (Exception e) {
            System.out.println("Can't create file: " + Blob.toPath());
        }
        addToIndex(filePath, SHAHash);


        try {
            if (Hashing.compress == true) {
                Files.write(Blob.toPath(), Hashing.fileCompressor(filePath),
                        StandardOpenOption.CREATE);
                // System.out.println("Using Compression");

            } else {
                byte[] stuff = Files.readAllBytes(Paths.get(filePath));
                Files.write(Blob.toPath(), stuff, StandardOpenOption.TRUNCATE_EXISTING);
            }

            // System.out.println("Made new blob file for: " + filePath);

        } catch (Exception e) {
            System.out.println(
                    "Error moment (BLOB Maker) for: " + Blob.toPath() + "\nException is: " + e);
        }
    }

    public static void Blobremover(String type, String filePath) {

        String abspath = Paths.get(filePath).toAbsolutePath().toString();
        String path = abspath.substring(abspath.indexOf("git"));
        String oldHash = null;
        // System.out.println("the path is: " + path);

        for (HashMap.Entry<String, ArrayList<String>> entry : indexMap.entrySet()) {
            ArrayList<String> paths = entry.getValue();
            if (paths.contains(path)) {
                oldHash = entry.getKey().substring(entry.getKey().indexOf(" ") + 1);
                break;
            }
        }
        // System.out.println("the index map is: " + IndexMaptoString());

        if (oldHash == null) {
            System.out.println("No blob found for file: " + path);
            return;
        }

        File oldBlob = new File(GitDirectory.objects, oldHash);
        oldBlob.delete();

        removeFromIndex(type, filePath);
    }

    // assumes that the file has been changed
    public static void staging(String type, String filePath) {
        if (hashMapStatus == false) {
            System.out.println("remaking hashmap rn!");
            remakeHashMap();
            System.out.println("the index map is: " + IndexMaptoString());
            hashMapStatus = true;
        } else {
            System.out.println("wth code bruh moment");
        }
        // removes the old blob
        Blobremover(type, filePath);

        // adds blob
        if (type.equals("blob")) {
            BLOBmaker(filePath);
        }

    }

    public static boolean BlobChecker(String blobName) {
        File[] files = GitDirectory.objects.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().equals(blobName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void removeFromIndex(String type, String filePath) {
        String abspath = Paths.get(filePath).toAbsolutePath().toString();
        String path = abspath.substring(abspath.indexOf("git"));

        // String name = Paths.get(filePath).getFileName().toString();
        // removes from hashmap
        for (HashMap.Entry<String, ArrayList<String>> entry : indexMap.entrySet()) {
            ArrayList<String> paths = entry.getValue();
            if (paths.contains(path)) {
                paths.remove(path);
                if (paths.isEmpty() == true) {
                    indexMap.remove(entry);
                }
            }
        }

        writeToIndex();
    }


    public static void addToIndex(String filePath, String SHAHash) {
        String abspath = Paths.get(filePath).toAbsolutePath().toString();
        String path = abspath.substring(abspath.indexOf("git"));

        ArrayList<String> pathsOfHash = new ArrayList<String>();
        if (indexMap.get(SHAHash) != null) {
            pathsOfHash = indexMap.get(SHAHash);

        }

        pathsOfHash.add(path);
        indexMap.put(SHAHash, pathsOfHash);

        writeToIndex();
    }

    public static void writeToIndex() {
        try {
            Files.write(GitDirectory.index.toPath(),
                    IndexMaptoString().getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            System.out.println("no index path prob");
        }
    }

    public static String IndexMaptoString() {
        StringBuilder sb = new StringBuilder();

        for (HashMap.Entry<String, ArrayList<String>> entry : indexMap.entrySet()) {
            String key = entry.getKey();
            ArrayList<String> paths = entry.getValue();

            for (String path : paths) {
                sb.append(key).append(" ").append(path).append("\n");
            }
        }
        // System.out.println(sb.toString());
        return sb.toString();
    }

    public static void remakeHashMap() {
        try {
            for (String line : Files.readAllLines(GitDirectory.index.toPath(),
                    StandardCharsets.UTF_8)) {
                String[] parts = line.split(" ", 2);
                String key = parts[0];
                String path = parts[1];

                ArrayList<String> paths = indexMap.getOrDefault(key, new ArrayList<>());
                paths.add(path);
                indexMap.put(key, paths);
            }
        } catch (Exception e) {
            System.out.println("Failed to load index");
        }
    }


    public static void multiBLOBMaker(File[] files) {
        if (files != null) {
            for (File file : files) {
                BLOBmaker(file.getPath());
            }
        }
    }

    public static String DirectoryTreeGenerator(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.isDirectory()) {
            System.out.println("this is not a directory bruv");
        }

        StringBuilder sb = new StringBuilder();
        try {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileHash = Hashing.hashStringfilePath(file.getPath());
                        // Made code cleaner
                        sb.append(
                                "blob " + fileHash + " " + fileHash + " " + file.getPath() + "\n");
                        BLOBmaker(file.getPath());
                    } else if (file.isDirectory()) {
                        String subTreeHash = DirectoryTreeGenerator(file.getPath());
                        // Made code cleaner
                        sb.append("tree " + subTreeHash + " " + subTreeHash + " " + file.getPath()
                                + "\n");
                    } else {
                        System.out.println("What file even is this (not a file or folder)");
                    }
                }
            }


            String treeHash = Hashing.hashString(sb.toString().getBytes());
            File tree = new File(GitDirectory.objects.getPath(), treeHash);
            if (!tree.exists()) {
                tree.createNewFile();
            }
            Files.write(tree.toPath(), sb.toString().getBytes(),
                    StandardOpenOption.TRUNCATE_EXISTING);

            return treeHash;

        } catch (IOException e) {
            System.out.println("File not found or something like that");
            return null;
        }

    }

    // https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html
    // https://www.geeksforgeeks.org/java/java-comparator-interface/ (lambda expressions part)
    public static ArrayList<String> workingListMaker() {
        try {
            if (!workingList.exists()) {
                workingList.createNewFile();
                // System.out.println("creating file");
            }
            if (hashMapStatus == false) {
                remakeHashMap();
                hashMapStatus = true;
            }

            ArrayList<BlobObject> fileObjects = new ArrayList<>();
            for (Map.Entry<String, ArrayList<String>> entry : indexMap.entrySet()) {
                String hash = entry.getKey();
                ArrayList<String> paths = entry.getValue();

                for (String path : paths) {
                    String[] parts = path.split("/"); // no clue why 4 ngl
                    String pathName = path;
                    int depth = parts.length;
                    String folderName = parts[parts.length - 2];
                    String fileName = parts[parts.length - 1];

                    fileObjects.add(
                            new BlobObject("blob", pathName, hash, depth, folderName, fileName));
                }
            }

            fileObjects.sort(Comparator.comparingInt(BlobObject::getDepth).reversed()
                    .thenComparing(BlobObject::getFileName)
                    .thenComparing(BlobObject::getFolderName));

            StringBuilder sb = new StringBuilder();
            for (BlobObject blob : fileObjects) {
                sb.append(blob.toString());
            }


            Files.write(workingList.toPath(), sb.toString().getBytes(),
                    StandardOpenOption.TRUNCATE_EXISTING);

            ArrayList<String> wList = new ArrayList<>();
            wList.addAll(Files.readAllLines(workingList.toPath()));

            return wList;
        } catch (Exception e) {
            System.out.println("bruh not working");
            return null;
        }
    }

    // helper method to get slash count
    public static int slashCount(String line) {
        String[] parts = line.split(" ", 3);
        String path = parts[2];
        int count = 0;
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '/') {
                count++;
            }
        }
        return count;
    }

    // bubble sort helper method
    public static void sortSlashCountDescend(List<String> lines) {
        for (int i = 0; i < lines.size() - 1; i++) {
            for (int j = 0; j < lines.size() - i - 1; j++) {
                String first = lines.get(j);
                String second = lines.get(j + 1);
                int firstSlash = slashCount(first);
                int secondSlash = slashCount(second);
                if (secondSlash > firstSlash || (secondSlash == firstSlash
                        && pathLine(second).compareTo(pathLine(first)) < 0)) {
                    String temp = lines.get(j);
                    lines.set(j, lines.get(j + 1));
                    lines.set(j + 1, temp);
                }
            }
        }
    }

    // helper method to get the path part of the line
    private static String pathLine(String line) {
        if (line == null)
            return "";
        int space = 0;
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ' ') {
                space++;
            }
            if (space >= 2) {
                s.append(c);
            }
        }
        return s.toString();
    }


    public static void IndexTreeGenerator(ArrayList<String> wList) {
        try {

            if (!workingList.exists()) {
                System.out.println("Working list missing");
                workingListMaker();
            }

            if (wList.size() == 1) {
                String rootLine = wList.get(0);
                String content = rootLine; // or whatever content you want inside root tree
                String rootHash = Hashing.hashString(content.getBytes());
                File rootFile = new File(GitDirectory.objects.getPath(), rootHash);
                Files.write(rootFile.toPath(), content.getBytes(), StandardOpenOption.CREATE);
                return;
            }
            // properly helped create a sorter helper method with two other helper methods
            // to sort the working list by slash count descending and then by path alphabetically
            // Makes sure the working list is properly sorted before processing
            sortSlashCountDescend(wList);
            ArrayList<String> subTree = new ArrayList<>();
            if (wList.size() == 0) {
                System.out.println("Working list empty");
                return;
            }
            String file = wList.get(0);
            String path = file.substring(file.lastIndexOf(" ") + 1);

            String parts[] = path.split("/");
            if (parts.length < 2) {
                if (parts.length < 2) {
                    System.out.println("No more subtrees can be made");
                    return;
                }
            }
            String folderName = parts[parts.length - 2];

            for (int i = wList.size() - 1; i >= 0; i--) {
                String otherFile = wList.get(i);
                String otherPath = otherFile.substring(otherFile.lastIndexOf(" ") + 1);
                String[] otherParts = otherPath.split("/");
                String otherFolderName = otherParts[otherParts.length - 2];

                if (folderName.equals(otherFolderName) && parts.length == otherParts.length) {
                    subTree.add(otherFile);
                    wList.remove(i);
                }
            }

            StringBuilder subTreeBlobs = new StringBuilder();
            for (String blobs : subTree) {
                subTreeBlobs.append(blobs).append("\n");
            }
            String treeHash = Hashing.hashString(subTreeBlobs.toString().getBytes());
            int lastSlash = path.lastIndexOf('/');
            String subTreePath = path.substring(0, lastSlash);
            String treeLine = "tree " + treeHash + " " + subTreePath;
            wList.add(0, treeLine);
            sortSlashCountDescend(wList);
            File subtree = new File(GitDirectory.objects.getPath(), treeHash);
            // subtree.createNewFile();
            Files.write(subtree.toPath(), subTreeBlobs.toString().getBytes(),
                    StandardOpenOption.CREATE);
            // Writes to the working list file to keep it updated. It did not do this before.
            if (wList.get(wList.size() - 1).substring(wList.get(wList.size() - 1).length() - 1)
                    .equals("\n"))
                wList.set(wList.size() - 1, wList.get(wList.size() - 1).substring(0,
                        wList.get(wList.size() - 1).length() - 1));
            Files.write(workingList.toPath(), wList, StandardCharsets.UTF_8);
            IndexTreeGenerator(wList);
        } catch (Exception e) {
            System.out.println("WorkingList file not found");
            e.printStackTrace();
            // return null;
        }

    }



}
