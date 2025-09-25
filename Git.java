import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Git{

    public static void main(String[] args) {
        //makeGitDirectoryAndFiles();
        //deleteGit();
        StressTest(10);
        
    }

    public static void makeGitDirectoryAndFiles(){

        File directory = new File("git");
        File objects = new File(directory.getPath(), "objects");
        File index = new File(directory.getPath(), "index");
        File HEAD = new File(directory.getPath(), "HEAD");

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

        File directory = new File("git");
        File objects = new File(directory.getPath(), "objects");
        File index = new File(directory.getPath(), "index");
        File HEAD = new File(directory.getPath(), "HEAD");

        if (directory.exists() && objects.exists() && index.exists() && HEAD.exists()){
            System.out.println("Git Repository Already Exists");
            return true;
            
        }
        return false;
    }

    public static void deleteGit(){
        File directory = new File("git");
        File objects = new File(directory.getPath(), "objects");
        File index = new File(directory.getPath(), "index");
        File HEAD = new File(directory.getPath(), "HEAD");

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
 
}