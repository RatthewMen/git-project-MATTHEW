import java.io.File;


public class GitDirectory {

    public static File directory = new File("git");
    public static File objects = new File(directory.getPath(), "objects");
    public static File index = new File(directory.getPath(), "index");
    public static File HEAD = new File(directory.getPath(), "HEAD");
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
        deleteIndex();
        HEAD.delete();
        index.delete();
        objects.delete();
        directory.delete();
    }

    public static void deleteIndex(){
        File[] files = objects.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.delete()) {
                    System.out.println("Failed to delete: " + file.getPath());
                }
            }
        }
    }

    public static void StressTest(int times){
        for (int i = 0; i < times; i++){
            makeGitDirectoryAndFiles();
            deleteGit();
        }

        System.out.println("Did " + times +  " cycles of making GIT and deleting it.");
    }


    public static void masterRESET(){
            deleteGit();
            makeGitDirectoryAndFiles();
            RandomFiles.deleteRandomFileMaker();
            System.out.println("Reset to blank GIT");
    }
}
