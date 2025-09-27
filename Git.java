import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.zip.Deflater;


public class Git{
    public static File directory = new File("git");
    public static File objects = new File(directory.getPath(), "objects");
    public static File index = new File(directory.getPath(), "index");
    public static File HEAD = new File(directory.getPath(), "HEAD");
    public static boolean compress = false;
    

    public static void main(String[] args) {
        deleteGit();
        makeGitDirectoryAndFiles();
        //StressTest(10);
        //System.out.println(hashString("boar.txt"));
        BLOBmaker("boar.txt");
        //BlobChecker(hashString("boar.txt"));
        
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

    //helper method for hashString
    public static String hashStringfilePath(String filePath){
        try {
            byte[] Bytes = Files.readAllBytes(Paths.get(filePath));
            return hashString(Bytes);
        } catch (Exception e) {
            System.out.println("Cant find file: " + filePath);
        }
        return null;
    }

    //Hashes with the SHA-1 algorithim
    public static String hashString(byte[] Bytes){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = digest.digest(Bytes);

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            //System.out.println("The hash for " + Files.readString(Paths.get(filePath)) + " is: " + hexString.toString());
            return hexString.toString();

        } catch (Exception e){
            System.out.println("No SHA-1 encription");
        }
        return null;
    }

    public static void BLOBmaker(String filePath){
        String SHAHash = "";
        if (compress == true){
            SHAHash = hashString(fileCompressor(filePath));
            System.out.println("Compressed shahash is: " + SHAHash);
        } else{
            SHAHash = hashStringfilePath(filePath);
        }

        addToIndex(filePath, SHAHash);
          
        File Blob = new File(objects, SHAHash);

        try {
            if (compress == true){
                Files.write(Blob.toPath(), fileCompressor(filePath), StandardOpenOption.CREATE);
                System.out.println("Using Compression");

            } else{
                byte[] stuff = Files.readAllBytes(Paths.get(filePath));
                Files.write(Blob.toPath(), stuff, StandardOpenOption.CREATE);
            }
            
            System.out.println("Made new blob file for: " + filePath);

        } catch (Exception e) {
            System.out.println("git stuff prob not found (BLOB Maker) " + Blob.toPath());
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
    
    //https://docs.oracle.com/javase/8/docs/api/java/util/zip/Deflater.html 
    //https://www.baeldung.com/java-compress-uncompress-byte-array
    public static byte[] fileCompressor(String filePath){
        try {
            byte[] Bytes = Files.readAllBytes(Paths.get(filePath));
            
            Deflater deflater = new Deflater();
            deflater.setInput(Bytes);
            deflater.finish();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            while (!deflater.finished()) {
                int compressedSize = deflater.deflate(buffer);
                outputStream.write(buffer, 0, compressedSize);
            }

            return outputStream.toByteArray();
        } catch(java.io.UnsupportedEncodingException ex) {
            System.out.println("UnsupportedEncodingExceptipon");
        } catch (Exception e) {
            System.out.println("git stuff prob not found (fileCompressor)" + filePath);
        }

        return null;   
    }
    
    public static void addToIndex(String filePath, String SHAHash){
        try {
            String entry = SHAHash + " " + Paths.get(filePath).getFileName().toString();
            Files.write(index.toPath(), entry.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println("no index path prob");
        }
    }
}