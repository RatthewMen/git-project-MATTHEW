import java.util.Date;

public class CommitNode {
    String commitHash;
    String parentHash;
    String treeHash;
    String author;
    String message;
    Date date;
    CommitNode parent;

    public CommitNode(String commitHash, String parentHash, String treeHash,
                      String author, String message, Date date, CommitNode parent) {
        this.commitHash = commitHash;
        this.parentHash = parentHash;
        this.treeHash = treeHash;
        this.author = author;
        this.message = message;
        this.date = date;
        this.parent = parent;
    }
    
}
