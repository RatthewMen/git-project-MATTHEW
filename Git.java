import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Git{
    
    public static HashMap<String, ArrayList<String>> indexMap = new HashMap<>();
    public static boolean hashMapStatus = false;

    public static void main(String[] args) {
        //makeGitDirectoryAndFiles();
        //deleteGit();
        //StressTest(10);
        //System.out.println(hashString("boar.txt"));
        //BLOBmaker("boar.txt");
        //BlobChecker(hashString("boar.txt"));
        //deleteRandomFileMaker();
        //randomFileMaker(10);
        //multiBLOBMaker(randomFiles.listFiles());
        //FileMakerChecker();
        //BLOBmaker("/dupefiles/boar.txt");
        //BLOBmaker("boar.txt");

        GitDirectory.masterRESET();
        //BLOBmaker("boar.txt");
        
        //multiBLOBMaker(RandomFiles.randomFiles.listFiles());
        //System.out.println("the index map is: " + IndexMaptoString());
        //staging("blob" , "boar.txt");
        RandomFiles.randomFileMaker(4);
        DirectoryTreeGenerator(RandomFiles.randomFiles.getPath());

        
    }

    
    public static void BLOBmaker(String filePath){
        String SHAHash = Hashing.CompressOrNot(filePath);

        //System.out.println("HASH: " + SHAHash);
        
        File Blob = new File(GitDirectory.objects, SHAHash);
        try {
            Blob.createNewFile();
        } catch (Exception e) {
            System.out.println("Can't create file: " + Blob.toPath());
        }
        
        addToIndex(filePath, SHAHash);


        try {
            if (Hashing.compress == true){
                Files.write(Blob.toPath(), Hashing.fileCompressor(filePath), StandardOpenOption.CREATE);
                //System.out.println("Using Compression");

            } else{
                byte[] stuff = Files.readAllBytes(Paths.get(filePath));
                Files.write(Blob.toPath(), stuff, StandardOpenOption.TRUNCATE_EXISTING);
            }
            
            //System.out.println("Made new blob file for: " + filePath);

        } catch (Exception e) {
            System.out.println("Error moment (BLOB Maker) for: " + Blob.toPath() + "\nException is: " + e);
        }
    }

    public static void Blobremover(String type, String filePath){

        String abspath = Paths.get(filePath).toAbsolutePath().toString();
        String path = abspath.substring(abspath.indexOf("git"));
        String oldHash = null;
        //System.out.println("the path is: " + path);
        
        for (HashMap.Entry<String, ArrayList<String>> entry : indexMap.entrySet()) {
            ArrayList<String> paths = entry.getValue();
            if (paths.contains(path)) {
                oldHash = entry.getKey().substring(entry.getKey().indexOf(" ") + 1);
                break;
            }
        }
        //System.out.println("the index map is: " + IndexMaptoString());

        if (oldHash == null) {
            System.out.println("No blob found for file: " + path);
            return;
        }

        File oldBlob = new File(GitDirectory.objects, oldHash);
        oldBlob.delete();

        removeFromIndex(type, filePath);
    }

    //assumes that the file has been changed
    public static void staging(String type, String filePath){
        if (hashMapStatus == false){
            System.out.println("remaking hashmap rn!");
            remakeHashMap();
            System.out.println("the index map is: " + IndexMaptoString());
            hashMapStatus = true;
        }
        else {
            System.out.println("wth code bruh moment");
        }
        //removes the old blob
        Blobremover(type, filePath);

        //adds blob
        if (type.equals("blob")){
            BLOBmaker(filePath);
        }
        
    }

    public static boolean BlobChecker(String blobName){
        File[] files = GitDirectory.objects.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().equals(blobName)){
                    return true;
                }
            }
        }
        return false;
    }

    public static void removeFromIndex(String type, String filePath){
        String abspath = Paths.get(filePath).toAbsolutePath().toString();
        String path = abspath.substring(abspath.indexOf("git"));

        //String name = Paths.get(filePath).getFileName().toString();
        //removes from hashmap 
        for (HashMap.Entry<String, ArrayList<String>> entry : indexMap.entrySet()) {
            ArrayList<String> paths = entry.getValue();
            if (paths.contains(path)){
                paths.remove(path);
                if (paths.isEmpty() == true){
                    indexMap.remove(entry);
                }
            }
        }

        writeToIndex();
    }
    
    
    public static void addToIndex(String filePath, String SHAHash){
        String abspath = Paths.get(filePath).toAbsolutePath().toString();
        String path = abspath.substring(abspath.indexOf("git"));
        
        ArrayList<String> pathsOfHash = new ArrayList<String>();
        if (indexMap.get(SHAHash)!= null){
            pathsOfHash = indexMap.get(SHAHash);
            
        }
    
        pathsOfHash.add(path);
        indexMap.put(SHAHash, pathsOfHash);
        
        writeToIndex();
    }

    public static void writeToIndex(){
        try {
            Files.write(GitDirectory.index.toPath(), IndexMaptoString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
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
        //System.out.println(sb.toString());
        return sb.toString();
    }
    
    public static void remakeHashMap(){
        try {
            for (String line : Files.readAllLines(GitDirectory.index.toPath(), StandardCharsets.UTF_8)) {
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


    public static void multiBLOBMaker(File[] files){
        if (files != null) {
            for (File file : files) {
                BLOBmaker(file.getPath());
            }
        }
    }

    public static String DirectoryTreeGenerator(String directoryPath){
        File dir = new File(directoryPath);
        if (!dir.isDirectory()){
            System.out.println("this is not a directory bruv");
        }

        StringBuilder sb = new StringBuilder();
        try {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()){
                        String fileHash = Hashing.hashStringfilePath(file.getPath());
                        sb.append("blob ").append(fileHash).append(" ").append(file.getPath()).append("\n");
                        BLOBmaker(file.getPath());
                    } else if (file.isDirectory()){
                        String subTreeHash = DirectoryTreeGenerator(file.getPath());
                        sb.append("tree ").append(subTreeHash).append(" ").append(file.getPath()).append("\n");
                    } else{
                        System.out.println("What file even is this (not a file or folder)");
                    }
                }
            }


            String treeHash = Hashing.hashString(sb.toString().getBytes());
            File tree = new File(GitDirectory.objects.getPath(),treeHash);
            if (!tree.exists()){
                tree.createNewFile();
            }
            Files.write(tree.toPath(), sb.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);

            return treeHash;

        } catch (IOException e) {
            System.out.println("File not found or something like that");
            return null;
        }

    }



}