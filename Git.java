import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.Deflater; 

public class Git{
    public static File directory = new File("git");
    public static File objects = new File(directory.getPath(), "objects");
    public static File index = new File(directory.getPath(), "index");
    public static File HEAD = new File(directory.getPath(), "HEAD");
    public static File randomFiles = new File("randomFiles");
    public static boolean compress = true;
    

    public static void main(String[] args) {
        deleteGit();
        makeGitDirectoryAndFiles();
        //StressTest(10);
        //System.out.println(hashString("boar.txt"));
        //BLOBmaker("boar.txt");
        //BlobChecker(hashString("boar.txt"));
        deleteRandomFileMaker();
        randomFileMaker(10);
        multiBLOBMaker(randomFiles.listFiles());
        FileMakerChecker();
        
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
            //System.out.println("Compressed shahash is: " + SHAHash);
        } else{
            SHAHash = hashStringfilePath(filePath);
        }

        addToIndex(filePath, SHAHash);
          
        File Blob = new File(objects, SHAHash);

        try {
            if (compress == true){
                Files.write(Blob.toPath(), fileCompressor(filePath), StandardOpenOption.CREATE);
                //System.out.println("Using Compression");

            } else{
                byte[] stuff = Files.readAllBytes(Paths.get(filePath));
                Files.write(Blob.toPath(), stuff, StandardOpenOption.CREATE);
            }
            
            //System.out.println("Made new blob file for: " + filePath);

        } catch (Exception e) {
            System.out.println("git stuff prob not found (BLOB Maker) " + Blob.toPath());
        }
    }

    public static boolean BlobChecker(String blobName){
        File[] files = objects.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().equals(blobName)){
                    return true;
                }
            }
        }
        return false;
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
            String entry = SHAHash + " " + Paths.get(filePath).getFileName().toString() + "\n";
            Files.write(index.toPath(), entry.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println("no index path prob");
        }
    }

    public static void randomFileMaker(int numberofFiles){
        if (!randomFiles.exists()){
            randomFiles.mkdir();
        }

        Random rand = new Random();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 1; i <= numberofFiles; i++) {
            try {
                int length = rand.nextInt(41) + 100; //length of text

                StringBuilder sb = new StringBuilder(length);
                for (int j = 0; j < length; j++) {
                    sb.append(chars.charAt(rand.nextInt(chars.length())));
                }
                String str = sb.toString();

                String fileName = "file" + i + ".txt";
                File newFile = new File(randomFiles.getPath(), fileName);
                Files.write(newFile.toPath(), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
                //System.out.println("Created " + fileName + " with content: " + str);

            } catch (Exception e) {
                System.err.println("Error creating random text file #" + i);
            }
        }
    }
    
    public static void deleteRandomFileMaker(){
        File[] files = randomFiles.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.delete()) {
                    System.out.println("Failed to delete: " + file.getPath());
                }
            }
        }

        randomFiles.delete();
    }

    public static void multiBLOBMaker(File[] files){
        if (files != null) {
            for (File file : files) {
                BLOBmaker(file.getPath());
            }
        }
    }

    //https://stackoverflow.com/questions/7899525/how-to-split-a-string-by-space
    public static void FileMakerChecker(){
        try {
            List<String> lines = Files.readAllLines(index.toPath());
            ArrayList<String> hashes = new ArrayList<String>();
            ArrayList<String> fileNames = new ArrayList<String>();

            for (String line : lines){
                String[] parts = line.split(" ", 2);
                hashes.add(parts[0]);
                fileNames.add(parts[1]);
            }

            //checking blobs
            for (String hash : hashes){
                if (BlobChecker(hash) == false){
                    System.out.println(hash + " is not found");
                }
                
            }
            
            //checking Files
            for (String fileName : fileNames){
                if (randomFilesChecker(fileName) == false){
                    System.out.println(fileName + " is not found");
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading file");
        }
    }

    public static boolean randomFilesChecker(String fileName){
        File[] files = randomFiles.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().equals(fileName)){
                    return true;
                }
            }
        }
        return false;
    }
}