
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.zip.Deflater;
import java.io.ByteArrayOutputStream;

public class Hashing {

    public static boolean compress = false;

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

    public static String CompressOrNot(String filePath){
        String SHAHash = "";
        if (compress == true){
            SHAHash = Hashing.hashString(fileCompressor(filePath));
            //System.out.println("Compressed shahash is: " + SHAHash);
        } else{
            SHAHash = Hashing.hashStringfilePath(filePath);
        }

        return SHAHash;
    }

}
