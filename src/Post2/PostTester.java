package Post2;

import java.util.*;


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
class Comment implements Comparable<Comment>
{
    String username;
    String commentID;
    String content;
    String replyToId;
    int likes;
    List<Comment>comments;
    int level;
    public Comment(String username, String commentID, String content, String replyToId,int level) {
        this.username = username;
        this.commentID = commentID;
        this.content = content;
        this.replyToId = replyToId;
        likes=0;
        comments=new ArrayList<>();
        this.level=level;
    }

    public void addLike()
    {
        likes++;
        Collections.sort(comments);
    }
    public void addComment(Comment c)
    {
        comments.add(c);
        Collections.sort(comments);
    }
    public int getLikesAndTotal()
    {   if(total()!=0)
        return likes+total();
        return likes;
    }
    public int total()
    {
        return comments.stream().mapToInt(Comment::getLikesAndTotal).sum()+comments.size();
    }
    public String printLevel()
    {
        return "    ".repeat(Math.max(0, level));
    }
    @Override
    public int compareTo(Comment o) {

        int val=Integer.compare(getLikesAndTotal(),o.getLikesAndTotal());
        if(val==0)
        {
            val=Integer.compare(likes,o.likes);
            if(val==0)
                return content.compareTo(content);
            return -val;
        }
        return -val ;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("%sComment: %s\n",printLevel(),content));
        sb.append(String.format("%sWritten by: %s\n",printLevel(),username));
        sb.append(String.format("%sLikes: %d\n",printLevel(),likes));
        for(Comment c:comments)
            sb.append(c);



        return sb.toString();
    }
}
class Post
{
    String user;
    String content;
    List<Comment> forThisLevel;
    Map<String,Comment>idComment;

    public Post(String user, String content) {
        this.user = user;
        this.content = content;
        forThisLevel =new ArrayList<>();
        idComment=new TreeMap<>();
    }
    void addComment (String username, String commentId, String content, String replyToId)
    {

        if(replyToId==null)
        {
            Comment c=new Comment(username,commentId,content,replyToId,2);
            forThisLevel.add(c);
            idComment.put(commentId,c);
        }
        else {
            Comment toAdd=idComment.get(replyToId);
            Comment c=new Comment(username,commentId,content,replyToId, toAdd.level+1);
            toAdd.addComment(c);
            idComment.put(commentId,c);
        }
        Collections.sort(forThisLevel);


    }
    public void likeComment(String id)
    {
        Comment toLike=idComment.get(id);
        toLike.addLike();
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        Collections.sort(forThisLevel);
        sb.append(String.format("Post: %s\n",content));
        sb.append(String.format("Written by: %s\n",user));
        sb.append("Comments:\n");
        for(Comment c:forThisLevel)
            sb.append(c);

        return sb.toString();
    }
}

