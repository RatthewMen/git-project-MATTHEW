import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomFiles {

    public static File randomFiles = new File("randomFiles");
        
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

    //https://stackoverflow.com/questions/7899525/how-to-split-a-string-by-space
    public static boolean FileMakerChecker(){
        try {
            List<String> lines = Files.readAllLines(GitDirectory.index.toPath());
            ArrayList<String> hashes = new ArrayList<String>();
            ArrayList<String> fileNames = new ArrayList<String>();

            for (String line : lines){
                String[] parts = line.split(" ", 2);
                hashes.add(parts[0]);
                fileNames.add(parts[1]);
            }

            //checking blobs
            for (String hash : hashes){
                if (Git.BlobChecker(hash) == false){
                    //System.out.println(hash + " is not found");
                    return false;
                }
                
            }
            
            //checking Files
            for (String fileName : fileNames){
                if (randomFilesChecker(fileName) == false){
                    //System.out.println(fileName + " is not found");
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Error reading file");
        }

        return false;
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
