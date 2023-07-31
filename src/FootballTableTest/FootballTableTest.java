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

class Team implements Comparable<Team> {

    String name;
    int wins;
    int draws;
    int losses;

    public Team(String name) {
        this.name = name;
        wins = 0;
        draws = 0;
        losses = 0;
    }

    public void addWin() {
        wins++;
    }

    public void addDraw() {
        draws++;
    }

    public void addLoss() {
        losses++;
    }

    @Override
    public String toString() {
        return String.format("%-15s %5d %5d %5d %5d %5d", name, all(), wins, draws, losses, points());
    }

    private int points() {
        return wins * 3 + draws;
    }

    private int all() {
        return draws + wins + losses;
    }

    @Override
    public int compareTo(Team o) {
        int v=Integer.compare(points(),o.points());
        if(v==0)
        {
            v=Integer.compare(Math.abs(wins-losses),Math.abs(o.wins-o.losses));
            if(v==0)
            {
                return  -name.compareTo(o.name);
            }
            return -v;
        }
        return -v;
    }
}

class FootballTable {
    TreeMap<String, Team> teams;

    public FootballTable() {
        teams = new TreeMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        Team home;
        Team away;

        if (homeGoals > awayGoals) {
            //home win
            if (teams.containsKey(homeTeam)) {
                home = teams.get(homeTeam);
                home.addWin();
            } else {
                home = new Team(homeTeam);
                home.addWin();
                teams.put(homeTeam, home);
            }
            if (teams.containsKey(awayTeam)) {
                away = teams.get(awayTeam);
                away.addLoss();
            } else {
                away = new Team(awayTeam);
                away.addLoss();
                teams.put(awayTeam, away);
            }

        } else if (homeGoals < awayGoals) {
            //away win
            if (teams.containsKey(homeTeam)) {
                home = teams.get(homeTeam);
                home.addLoss();
            } else {
                home = new Team(homeTeam);
                home.addLoss();
                teams.put(homeTeam, home);
            }
            if (teams.containsKey(awayTeam)) {
                away = teams.get(awayTeam);
                away.addWin();
            } else {
                away = new Team(awayTeam);
                away.addWin();
                teams.put(awayTeam, away);
            }
        } else {
            //draw
            if (teams.containsKey(homeTeam)) {
                home = teams.get(homeTeam);
                home.addDraw();
            } else {
                home = new Team(homeTeam);
                home.addDraw();
                teams.put(homeTeam, home);
            }
            if (teams.containsKey(awayTeam)) {
                away = teams.get(awayTeam);
                away.addDraw();
            } else {
                away = new Team(awayTeam);
                away.addDraw();
                teams.put(awayTeam, away);
            }

        }
    }

    public void printTable() {
        AtomicInteger z=new AtomicInteger(1);
        teams.entrySet().stream().sorted(Map.Entry.<String,Team>comparingByValue()).forEach(i-> System.out.printf("%2d. %s%n",z.getAndIncrement(),i.getValue()));

    }
}

