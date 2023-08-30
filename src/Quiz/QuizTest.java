package Quiz;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class QuizTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i=0;i<questions;i++) {
            quiz.addQuestion(sc.nextLine());
        }

        List<String> answers = new ArrayList<>();

        int answersCount =  Integer.parseInt(sc.nextLine());

        for (int i=0;i<answersCount;i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase==1) {
            quiz.printQuiz(System.out);
        } else if (testCase==2) {
            try {
                quiz.answerQuiz(answers, System.out);
            } catch (InvalidOperationException e) {
                System.out.println("Answers and questions must be of same length!");
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}
enum TYPE
{
    TF,MC
}
interface Question
{
    TYPE getType();
    String getText();
    double getPoints();
    String getAnswer();
    boolean isCorrect(String s);
    List<String> getValid();
    boolean checkValid(String s);

    double getPointsForAnswer(String s);
}
class TF implements Question
{
    String text;
    double points;
    String trueAnswer;
    List<String> valid;
    public TF(String text, double points, String trueAnswer) throws InvalidOperationException {
        this.text = text;
        this.points = points;
        this.trueAnswer = trueAnswer;
        fillValid();
        if(!checkValid(trueAnswer))
            throw new InvalidOperationException();

    }

    private void fillValid() {
        valid=new ArrayList<>();
        valid.add("true");
        valid.add("false");
    }

    @Override
    public double getPointsForAnswer(String s) {
        if(s.equals(trueAnswer))
            return points;
        else return 0;
    }

    @Override
    public List<String> getValid() {
        return valid;
    }

    @Override
    public boolean checkValid(String s) {
        return valid.contains(s);
    }

    @Override
    public TYPE getType() {
        return TYPE.TF;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public double getPoints() {
        return points;
    }

    @Override
    public String getAnswer() {
       return trueAnswer;
    }

    @Override
    public boolean isCorrect(String s) {
        return s.equals(trueAnswer);
    }

    @Override
    public String toString() {
        return String.format("True/False Question: %s Points: %d Answer: %s",getText(),(int)getPoints(),trueAnswer);
    }
}
class MC implements  Question
{
    String text;
    double points;
    String trueAnswer;
    List<String> valid;

    public MC(String text, double points, String trueAnswer) throws InvalidOperationException {
        this.text = text;
        this.points = points;
        this.trueAnswer = trueAnswer;
        fillValid();
        if(!checkValid(trueAnswer))
            throw new InvalidOperationException();
    }

    private void fillValid() {
        valid=new ArrayList<>();
        valid.add("A");
        valid.add("B");
        valid.add("C");
        valid.add("D");
        valid.add("E");
    }

    @Override
    public List<String> getValid() {
        return valid;
    }

    @Override
    public boolean checkValid(String s) {
        return valid.contains(s);
    }

    @Override
    public double getPointsForAnswer(String s) {
        if(s.equals(trueAnswer))
            return points;
        else return -points*0.2;
    }

    @Override
    public TYPE getType() {
        return TYPE.TF;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public double getPoints() {
        return points;
    }

    @Override
    public String getAnswer() {
        return trueAnswer;
    }

    @Override
    public boolean isCorrect(String s) {
        return s.equals(getAnswer());
    }

    @Override
    public String toString() {
        return String.format("Multiple Choice Question: %s Points %d Answer: %s",getText(),(int)getPoints(),trueAnswer);
    }
}

class Quiz
{

    List<Question> questions;
    int count;
    public Quiz()
    {
        questions=new ArrayList<Question>();

    }
    public void addQuestion(String questionData)
    {
        //TF;text;points;answer  answer mozi true/false
        //MC;text;points;answer  answer mozi A/B/C/D/E
        try {
            questions.add(QuestionFactory.create(questionData));
        } catch (InvalidOperationException e) {

            System.out.printf("%s is not allowed option for this question\n",QuestionFactory.extractAnswer(questionData));
        }

    }

    public void printQuiz(OutputStream os)
    {
        PrintWriter pw=new PrintWriter(os);
        questions.stream().sorted(Comparator.comparing(Question::getPoints,Comparator.reverseOrder())).forEach(pw::println);
        pw.flush();
    }
    void answerQuiz (List<String> answers, OutputStream os) throws InvalidOperationException //throws InvalidOperationException
    {
        PrintWriter pw=new PrintWriter(os);
        if(answers.size()!=questions.size())
            throw new InvalidOperationException();
        int i=0;
        double total=0;
       for(Question q:questions)
       {
           if(i==answers.size())
               break;
           try {
               if(!q.checkValid(answers.get(i)))
                    throw  new InvalidOperationException();
               pw.printf("%d. %.2f\n",i+1,q.getPointsForAnswer(answers.get(i)));
               total+=q.getPointsForAnswer(answers.get(i));
           }
           catch (Exception e)
           {
               System.out.printf("%s is not allowed option for this question\n",answers.get(i));
           }
           i++;
       }
       pw.printf("Total points: %.2f",total);
       pw.flush();
    }


}
class QuestionFactory
{
    static Question create(String line) throws InvalidOperationException {
        String[]splitter=line.split(";");
        String type=splitter[0];
        if(type.equals("MC"))
            return new MC(splitter[1],Double.parseDouble(splitter[2]),splitter[3]);
        else return new TF(splitter[1],Double.parseDouble(splitter[2]),splitter[3]);
    }

    public static String extractAnswer(String line) {
    String[]splitter=line.split(";");
    return splitter[3];
    }
}
class InvalidOperationException extends  Exception
{
    public InvalidOperationException()
    {
        super();
    }
}