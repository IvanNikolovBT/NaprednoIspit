package Post;//package Post;

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

class Comment //implements Comparable<Comment>
{

    int order;
    Set<Comment> comments;
    String commentID;
    String content;
    int likes;
    String user;
    int level;
    public Comment(String user,String commentID,String content, int level)
    {
        this.user=user;
        this.commentID=commentID;
        this.content=content;
        comments=new TreeSet<>(Comparator.comparing(Comment::getLikes,Comparator.reverseOrder()));
        likes=0;
        this.level=level;
    }

    public int getLikes() {
        return likes;
    }
    void addLike()
    {
        likes++;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("%sComment: %s\n",printIndent(),content));
        sb.append(String.format("%sWritten by: %s\n",printIndent(),user));
        sb.append(String.format("%sLikes: %d\n",printIndent(),getLikes()));
        for(Comment c:comments)
            sb.append(c);
        return sb.toString();
    }
    public String printIndent()
    {
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<level;i++)
            stringBuilder.append("    ");

        return stringBuilder.toString();
    }

    public String  getContent() {
        return content;
    }
    public int getTotal()
    {
        return comments.stream().mapToInt(i->i.comments.size()).sum();
    }
    public int likesPlusTotal()
    {
        return likes+getTotal();
    }

    public String getUser() {
        return user;
    }

    public void addOrder(int order) {
        this.order=order;
    }

    public int getOrder() {
        return order;
    }
    //    @Override
//    public int compareTo(Comment o) {
//        int val=Integer.compare(getLikes(),o.likes);
//        if(val==0)
//        {
//            return Integer.compare(comments.size()+getLikes(),o.comments.size()+o.getLikes());
//        }
//            return val;
//    }
}
class Post
{
    List<String>inWhatOrder;
    String username;
    String content;
    Set<Comment>forThisLevel;//site mozni komentari gi mame tuka
    Map<String,Comment>idComments;//p

    public Post(String username, String content) {
        this.username = username;
        this.content = content;
//        forThisLevel=new TreeSet<>();
        forThisLevel=new TreeSet<>(Comparator.comparing(Comment::likesPlusTotal,Comparator.reverseOrder()).thenComparing(Comment::getContent));
        idComments=new TreeMap<>();
        inWhatOrder=new ArrayList<>();
    }

    void addComment (String username, String commentId, String content, String replyToId)
    {

        if(replyToId==null)
        {

            Comment comment=new Comment(username,commentId,content,2);
            forThisLevel.add(comment);
            idComments.put(commentId,comment);
            inWhatOrder.add(comment.content);
            comment.addOrder(inWhatOrder.size());
        }else
        {
            Comment toAdd=idComments.get(replyToId);
            Comment comment=new Comment(username,commentId,content, toAdd.level+1);
            idComments.put(commentId,comment);
            toAdd.addComment(comment);
            inWhatOrder.add(comment.content);
            comment.addOrder(inWhatOrder.size());
        }


    }
    public int getLikes()
    {
        return forThisLevel.stream().mapToInt(Comment::getLikes).sum();
    }

    public void likeComment(String commentID)
    {
        Comment comment=idComments.get(commentID);
        comment.addLike();
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("Post: %s\n",content));
        sb.append(String.format("Written by: %s\n",username));
        sb.append("Comments: \n");
        List<Comment> temp=forThisLevel.stream().collect(Collectors.toList());
        Collections.sort(temp,Comparator.comparing(Comment::getLikes,Comparator.reverseOrder()).thenComparing(Comment::getOrder));
        for(Comment c:temp)
           sb.append(c);
//        for(Comment c:forThisLevel)
//            sb.append(c);
        return sb.toString();
    }

}
