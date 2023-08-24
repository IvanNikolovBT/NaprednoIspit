package ApplicantEvaluationTest;

import java.util.Scanner;

/**
 * I partial exam 2016
 */
public class ApplicantEvaluationTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        int creditScore = scanner.nextInt();
        int employmentYears = scanner.nextInt();
        boolean hasCriminalRecord = scanner.nextBoolean();
        int choice = scanner.nextInt();
        Applicant applicant = new Applicant(name, creditScore, employmentYears, hasCriminalRecord);
        Evaluator.TYPE type = Evaluator.TYPE.values()[choice];
        Evaluator evaluator = null;
        try {
            evaluator = EvaluatorBuilder.build(type);
            System.out.println("Applicant");
            System.out.println(applicant);
            System.out.println("Evaluation type: " + type.name());
            if (evaluator.evaluate(applicant)) {
                System.out.println("Applicant is ACCEPTED");
            } else {
                System.out.println("Applicant is REJECTED");
            }
        } catch (InvalidEvaluation invalidEvaluation) {
            System.out.println("Invalid evaluation");
        }
    }
}

class Applicant {
    private String name;

    private int creditScore;
    private int employmentYears;
    private boolean hasCriminalRecord;

    public Applicant(String name, int creditScore, int employmentYears, boolean hasCriminalRecord) {
        this.name = name;
        this.creditScore = creditScore;
        this.employmentYears = employmentYears;
        this.hasCriminalRecord = hasCriminalRecord;
    }

    public String getName() {
        return name;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public int getEmploymentYears() {
        return employmentYears;
    }

    public boolean hasCriminalRecord() {
        return hasCriminalRecord;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nCredit score: %d\nExperience: %d\nCriminal record: %s\n",
                name, creditScore, employmentYears, hasCriminalRecord ? "Yes" : "No");
    }
}

interface Evaluator {
    enum TYPE {
        NO_CRIMINAL_RECORD,
        MORE_EXPERIENCE,
        MORE_CREDIT_SCORE,
        NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE,
        MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE,
        NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE,
        INVALID // should throw exception
    }

    boolean evaluate(Applicant applicant);
}

class EvaluatorBuilder {
    public static Evaluator build(Evaluator.TYPE type) throws InvalidEvaluation {

        switch (type)
        {
            case NO_CRIMINAL_RECORD :return new CriminalRecord(null);
            case MORE_EXPERIENCE: return new Experience(null);
            case MORE_CREDIT_SCORE: return new CreditScore(null);
            case NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE: return new CreditScore(new CriminalRecord(null));
            case NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE:return new Experience(new CriminalRecord(null));
            case MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE:return new Experience(new CreditScore(null));
            case INVALID: throw new InvalidEvaluation();
       }

        return null;
    }
}

abstract  class ChainEvaluator implements  Evaluator
{
    Evaluator next;

    public ChainEvaluator(Evaluator next) {
        this.next = next;
    }
    public boolean hasNext()
    {
        return next != null;
    }

    @Override
    public boolean evaluate(Applicant applicant) {

        return next.evaluate(applicant);
    }
}

class CriminalRecord extends ChainEvaluator
{
    public CriminalRecord(Evaluator next) {
        super(next);
    }

    @Override
    public boolean evaluate(Applicant applicant) {
        if(applicant.hasCriminalRecord())
            return false;
            else if(hasNext())
                next.evaluate(applicant);
        return true;
    }
}
class CreditScore extends ChainEvaluator
{
    public CreditScore(Evaluator next) {
        super(next);
    }

    @Override
    public boolean evaluate(Applicant applicant) {
    if(applicant.getCreditScore()<500)
        return false;
    else if(hasNext())
        return next.evaluate(applicant);
    else
        return true;
    }

}
class Experience extends ChainEvaluator
{
    public Experience(Evaluator next) {
        super(next);
    }

    @Override
    public boolean evaluate(Applicant applicant) {
        if(applicant.getEmploymentYears()<10)
            return false;
        else if(hasNext())
            return next.evaluate(applicant);
        return true;
    }

}
class InvalidEvaluation extends  Exception
{
    public InvalidEvaluation()
    {
        super();
    }
}