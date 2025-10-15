import java.io.File;
import java.io.IOException;

public class GitWrapperTester {

    public static void main(String args[]) throws IOException {

        /* Your tester code goes here */
        GitWrapper gw = new GitWrapper();
        // gw.init();
        File sample = new File("sampleDirectory");
        sample.mkdir();
        File hello = new File("sampleDirectory/hello.txt");
        File world = new File("sampleDirectory/world.txt");
        hello.createNewFile();
        world.createNewFile();
        gw.add("sampleDirectory/hello.txt");
        gw.add("sampleDirectory/world.txt");
        gw.commit("John Doe", "Initial commit");
        // gw.checkout("1234567890");

    }

}
