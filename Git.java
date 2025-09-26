import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;

public class Git{
    public static File directory = new File("git");
    public static File objects = new File(directory.getPath(), "objects");
    public static File index = new File(directory.getPath(), "index");
    public static File HEAD = new File(directory.getPath(), "HEAD");
    

    public static void main(String[] args) {
        //makeGitDirectoryAndFiles();
        //deleteGit();
        //StressTest(10);
        //System.out.println(hashString("boar.txt"));
        //BLOBmaker("boar.txt");
        BlobChecker(hashString("boar.txt"));
        
    }

    public static void makeGitDirectoryAndFiles(){

        if (checkGitFiles() == true){
            return;
        }

        //makes git directory
        if (!directory.exists()){
            directory.mkdir();
        }
        //makes object directory inside of git
        if (!objects.exists()){
            objects.mkdir();
        }
        // makes the index file inside of git
        try {
            if (!index.exists()){
                index.createNewFile();
            }
        } catch (Exception e) {
            System.out.println("Error creating: " + index);
        }
        //makes the HEAD file inside of git
        try {
            if (!HEAD.exists()){
                HEAD.createNewFile();
            }
        } catch (Exception e) {
            System.out.println("Error creating: " + HEAD);
        }
        System.out.println("Git Repository Created");

    }

    public static boolean checkGitFiles(){

        if (directory.exists() && objects.exists() && index.exists() && HEAD.exists()){
            System.out.println("Git Repository Already Exists");
            return true;
            
        }
        return false;
    }

    public static void deleteGit(){
        
        
        File[] files = objects.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.delete()) {
                    System.out.println("Failed to delete: " + file.getPath());
                }
            }
        }
        
        HEAD.delete();
        index.delete();
        objects.delete();
        directory.delete();
    }

    public static void StressTest(int times){
        for (int i = 0; i < times; i++){
            makeGitDirectoryAndFiles();
            deleteGit();
        }

        System.out.println("Did " + times +  " cycles of making GIT and deleting it.");
    }

    //Hashes with the SHA-1 algorithim
    public static String hashString(String filePath){
        try{

            byte[] Bytes = Files.readAllBytes(Paths.get(filePath));

            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = digest.digest(Bytes);

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            //System.out.println("The hash for " + Files.readString(Paths.get(filePath)) + " is: " + hexString.toString());

            return hexString.toString();

        } catch (java.nio.file.NoSuchFileException e) {
            System.err.println("File not found: " + filePath);
            return null;
    
        } catch (IOException e) {
            System.err.println("I/O error while reading file: " + filePath);
            return null;
        
        } catch (Exception e){
            System.out.println("DUMB ERROR FOR File : " + filePath);
            return null;
        }
    }

    public static void BLOBmaker(String filePath){
        String SHAHash = hashString(filePath);
        File Blob = new File(objects, SHAHash);

        try {
            byte[] stuff = Files.readAllBytes(Paths.get(filePath));
            Files.write(Blob.toPath(), stuff, StandardOpenOption.CREATE);
            System.out.println("Made new blob file for: " + filePath);

        } catch (Exception e) {
            System.out.println("git stuff prob not found " + Blob.toPath());
        }
    }

    public static void BlobChecker(String blobName){
        File[] files = objects.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().equals(blobName)){
                    System.out.println("There is a file with the blob name of: " + blobName);
                } else {
                    System.out.println("There is no file with the blob name of: " + blobName);
                }
            }
        }
        System.out.println("There is no file with the blob name of: " + blobName);
    }
 
}