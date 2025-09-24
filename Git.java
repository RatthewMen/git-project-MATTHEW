import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Git{

    public static void main(String[] args) {
        makeGitDirectoryAndFiles();
        
    }

    public static void makeGitDirectoryAndFiles(){

        File directory = new File("git");
        File objects = new File(directory.getPath(), "objects");
        File index = new File(directory.getPath(), "index");
        File HEAD = new File(directory.getPath(), "HEAD");

        if (directory.exists() && objects.exists() && index.exists() && HEAD.exists()){
            System.out.println("Git Repository Already Exists");
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
}