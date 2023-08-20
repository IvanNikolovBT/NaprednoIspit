//package FootballTableTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

class Team {
    String name;
    int wins;
    int draws;
    int losses;
    int scored;
    int taken;

    public Team(String name) {
        this.name = name;
        wins = 0;
        draws = 0;
        losses = 0;
    }
    public  void addTaken(int num)
    {
        taken+=num;
    }
    public void addScored(int num)
    {
        scored+=num;
    }
    public void addWin() {
        wins++;
    }

    public void addLoss() {
        losses++;
    }

    public void addDraw() {
        draws++;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }
    public int getPoints()
    {
        return wins*3+draws;
    }
    public int getPlayed()
    {
        return wins+draws+losses;
    }
    public int getDif()
    {
        return scored-taken;
    }

    @Override
    public String toString() {
        return String.format("%-15s%5d%5d%5d%5d%5d",name,getPlayed(),getWins(),getDraws(),getLosses(),getPoints());
    }
}

class FootballTable {
    Map<String, Team> teams;

    public FootballTable() {
        teams = new TreeMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        Team home;
        if (teams.containsKey(homeTeam))
             home=teams.get(homeTeam);
        else
             home=new Team(homeTeam);
        Team away;
        if (teams.containsKey(awayTeam))
            away=teams.get(awayTeam);
        else
            away=new Team(awayTeam);
        addInfo(homeGoals, awayGoals, home, away);
        teams.put(homeTeam,home);
        teams.put(awayTeam,away);
    }

    private void addInfo(int homeGoals, int awayGoals, Team home,Team away) {
        if(homeGoals > awayGoals)
        {
            home.addWin();
            away.addLoss();

        } else if (homeGoals == awayGoals) {
            home.addDraw();
            away.addDraw();

        }
        else {
            home.addLoss();
            away.addWin();
        }
        home.addScored(homeGoals);
        home.addTaken(awayGoals);
        away.addScored(awayGoals);
        away.addTaken(homeGoals);
    }

    public void printTable() {
        AtomicInteger z=new AtomicInteger(1);
        teams.values().stream().sorted(Comparator.comparing(Team::getPoints,Comparator.reverseOrder()).thenComparing(Team::getDif,Comparator.reverseOrder()).thenComparing(Team::getName)).forEach(i-> System.out.printf("%2d. %s\n",z.getAndIncrement(),i));
    }
}

