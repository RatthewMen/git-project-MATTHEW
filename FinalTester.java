import java.io.IOException;
import java.nio.file.*;
import java.io.File;
import java.util.List;

public class FinalTester {

    public static GitWrapper gw = new GitWrapper();

    public static void main(String[] args) throws IOException{
        GitDirectory.deleteGit();
        gw.init();
    }

    

    //commit tester

    public static void commitTesterFlatFiles() throws IOException{
        File flatFile = new File("flat.txt");
        Files.writeString(flatFile.toPath(), "hi im a flat file", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        gw.add(flatFile.getPath());
        gw.commit("Rat", "message");

    }
    public static void commitTesterEmptyIndex() throws IOException{
        GitDirectory.deleteGit();
        gw.init();
        gw.commit("Rat", "hi");
    }

    //add tester

    //checks if blob is made
    public static void addTesterBlobMade() throws IOException{
        gw.add("ZidFolder\\dpeng.txt");
        File[] files = GitDirectory.objects.listFiles();
        for (File file : files) {
            if (file.getName().equals("5ea38cf69bccefb8cb061592e068d30d4869b60c")){
                System.out.println("blob file exists");
            }
        }
        
    }

    //checks if it what add does if its a dupe file
    public static void addFileDupeFile() throws IOException{
        gw.add("ZidFolder\\dpeng.txt");
        gw.add("ZidFolder\\dpeng.txt");

    }

    //tests adding a new file
    public static void addFileContentsChecker() throws IOException{
        //gw.init();
        gw.add("ZidFolder\\dpeng.txt");
        List<String> lines = Files.readAllLines(GitDirectory.index.toPath());
        if (!lines.get(0).equals("5ea38cf69bccefb8cb061592e068d30d4869b60c git-project-MATTHEW\\ZidFolder\\dpeng.txt")){
            System.out.println("not right index string for dpeng.txt");
        }

    }

    //tests a directory
    public static void addTesterNotRealDirectory() throws IOException{
        gw.add("ZidFolder\\ZidFolder2");
    }

    //tests a not real path
    public static void addTesterNotRealPath() throws IOException{
        gw.add("ZidFolder\\amongus");
    }


    //deletes folders for testing
    public static void deleteZidFolder() throws IOException {
        File folder = new File("ZidFolder");
        if (!folder.exists()) {
            System.out.println("ZidFolder does not exist.");
            return;
        }

        deleteRecursively(folder);
        System.out.println("ZidFolder and all its contents have been deleted.");
    }

    private static void deleteRecursively(File file) throws IOException {
        if (file.isDirectory()) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    deleteRecursively(f);
                }
            }
        }
        Files.deleteIfExists(file.toPath());
    }

    
    //makes folders for testing
    public static void createZidFolder() throws IOException {
        // Create the folder
        File folder = new File("ZidFolder");
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Create zid.txt
        File zidFile = new File(folder, "zid.txt");
        Files.writeString(zidFile.toPath(), "zid.txt", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        // Create dpeng.txt
        File dpengFile = new File(folder, "dpeng.txt");
        Files.writeString(dpengFile.toPath(), "dpeng.txt", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        //create zidfolder2
        File folder2 = new File(folder.toString(),"ZidFolder2");
        if (!folder2.exists()) {
            folder2.mkdir();
        }

        //create zid2.txt
        File zidFile2 = new File(folder2, "zid2.txt");
        Files.writeString(zidFile2.toPath(), "zid2.txt", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("ZidFolder with zid.txt and dpeng.txt created successfully.");
    }
    
    //init tester

    //checks if paths are correct
    public static void InitTesterPaths(){
        if (!GitDirectory.directory.getPath().equals("git")){
            System.out.println("Git has wrong path");
        }

         if (!GitDirectory.objects.getPath().equals("git\\objects")){
            System.out.println("Objects has wrong path: " + GitDirectory.objects.getPath() );
        }

         if (!GitDirectory.HEAD.getPath().equals("git\\HEAD")){
            System.out.println("HEAD has wrong path");
        }
        
         if (!GitDirectory.index.getPath().equals("git\\index")){
            System.out.println("index has wrong path");
        }

    }

    //run init tester alot
    public static void InitTesterRepetition(int times){
        for (int i =0; i < times; i++){
            gw.init();
        }
    }

    //HEAD created if missing
    public static void InitTesterDeleteHead() throws IOException{
        gw.init();
        Files.delete(GitDirectory.HEAD.toPath());
        gw.init();
    }

    //Index created if missing
    public static void InitTesterDeleteIndex() throws IOException{
        gw.init();
        Files.delete(GitDirectory.index.toPath());
        gw.init();
    }

    //objects recreated if missing
    public static void InitTesterDeleteObjects() throws IOException{
        gw.init();
        GitDirectory.deleteObjects();
        Files.delete(GitDirectory.objects.toPath());
        gw.init();
    }

    //git recreated if missing
    public static void InitTesterDeleteGit() throws IOException{
        gw.init();
        GitDirectory.deleteGit();
        gw.init();
    }
}
