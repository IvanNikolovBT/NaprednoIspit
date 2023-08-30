package Post;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class PostTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String postAuthor = sc.nextLine();
        String postContent = sc.nextLine();

        Post p = new Post(postAuthor, postContent);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String testCase = parts[0];

            if (testCase.equals("addComment")) {
                String author = parts[1];
                String id = parts[2];
                String content = parts[3];
                String replyToId = null;
                if (parts.length == 5) {
                    replyToId = parts[4];
                }
                p.addComment(author, id, content, replyToId);
            } else if (testCase.equals("likes")) { //likes;1;2;3;4;1;1;1;1;1 example
                for (int i = 1; i < parts.length; i++) {
                    p.likeComment(parts[i]);
                }
            } else {
                System.out.println(p);
            }

        }
    }
}
interface Commnetable
{
    void addComment (String username, String commentId, String content, String replyToId);
    void likeComment (String commentId);
}
class Comment implements Commnetable
{
    String user;
    int likes;
    String content;
    String commentID;
    Map<String,Set<Comment>>comments;
    String replyToID;

    public Comment(String user, String content, String commentID,String replyToID) {
        this.user = user;
        this.likes = 0;
        this.content = content;
        this.commentID = commentID;
        this.replyToID=replyToID;
        comments=new HashMap<>();
    }

    @Override
    public void addComment(String username, String commentId, String content, String replyToId) {
    Comment c=new Comment(user,content,commentID,replyToId);
    Set<Comment> toAdd=comments.get(replyToId);
    toAdd.add(c);
    }

    @Override
    public void likeComment(String commentId) {

    }
    void addLike()
    {
        likes++;
    }

    public String printInfo(int level) {
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("%sComment: %s\n",IndentPrinter.print(level),content));
        sb.append(String.format("%sWritten by: %s",IndentPrinter.print(level),user));
        sb.append(String.format("%sLikes: %d",IndentPrinter.print(level),likes));
        for(Comment c:comments)
            printInfo(level+1);
        return sb.toString();
    }
}
class Post implements Commnetable
{
    String username;
    String postContent;
    String rootID;
    Map<String,Set<Comment>>comments;
    Set<Comment>commentSet;
    public Post(String username, String postContent) {
        this.username = username;
        this.postContent = postContent;
        rootID="root";
        comments=new HashMap<>();
        commentSet=new HashSet<>();
    }
    public void addComment(String userName,String commentID,String content,String replyToID)
    {
        if(replyToID==null)
            comments.put(rootID,new Comment());
    }
    public void likeComment(String commentID)
    {

    }
}
class IndentPrinter
{
    static String print(int level)
    {
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<level;i++)
            sb.append("\t");
        return sb.toString();
    }
}