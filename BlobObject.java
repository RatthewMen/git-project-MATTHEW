public class BlobObject {

    private String type;
    private String pathName;
    private String hash;
    private int depth;
    private String folderName;
    private String fileName;

    public BlobObject(String type,String pathName, String hash, int depth, String folderName,String fileName) {
        this.type = type;
        this.pathName = pathName;
        this.hash = hash;
        this.depth = depth;
        this.folderName = folderName;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return type + " " + hash + " " + pathName + "\n";
    }

    // getters 
    /**
     * @return the depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @return the folderName
     */
    public String getFolderName() {
        return folderName;
    }

    /**
     * @return the hash
     */
    public String getHash() {
        return hash;
    }
    
    /**
     * @return the pathName
     */
    public String getPathName() {
        return pathName;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    // setters

    /**
     * @param depth the depth to set
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }
    
    /**
     * @param folderName the folderName to set
     */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @param pathName the pathName to set
     */
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}    

