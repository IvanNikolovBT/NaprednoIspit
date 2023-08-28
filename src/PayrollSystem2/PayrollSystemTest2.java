package PayrollSystem2;//package PayrollSystem2;

import java.util.*;
import java.util.stream.Collectors;

public class PayrollSystemTest2 {

    public static void main(String[] args) {

        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 11 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5.5 + i * 2.5);
        }

        Scanner sc = new Scanner(System.in);

        int employeesCount = Integer.parseInt(sc.nextLine());

        PayrollSystem ps = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);
        Employee emp = null;
        for (int i = 0; i < employeesCount; i++) {
            try {
                emp = ps.createEmployee(sc.nextLine());
            } catch (BonusNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        }

        int testCase = Integer.parseInt(sc.nextLine());

        switch (testCase) {
            case 1: //Testing createEmployee
                if (emp != null) System.out.println(emp);
                break;
            case 2: //Testing getOvertimeSalaryForLevels()
                ps.getOvertimeSalaryForLevels().forEach((level, overtimeSalary) -> {
                    System.out.printf("Level: %s Overtime salary: %.2f\n", level, overtimeSalary);
                });
                break;
            case 3: //Testing printStatisticsForOvertimeSalary()
                ps.printStatisticsForOvertimeSalary();
                break;
            case 4: //Testing ticketsDoneByLevel
                ps.ticketsDoneByLevel().forEach((level, overtimeSalary) -> {
                    System.out.printf("Level: %s Tickets by level: %d\n", level, overtimeSalary);
                });
                break;
            case 5: //Testing getFirstNEmployeesByBonus (int n)
                ps.getFirstNEmployeesByBonus(Integer.parseInt(sc.nextLine())).forEach(System.out::println);
                break;
        }

    }
}

interface Employee extends Comparable<Employee> {
    String getID();

    double salary();

    String getLevel();

    @Override
    default int compareTo(Employee o) {
        return -Double.compare(this.salary(), o.salary());
    }

    double calculateBonus();

    double getOverTime();

    int getTicketsCount();
}

class HourlyEmployee implements Employee {

    String type;


    double val;
    String id;
    String level;
    double hours;
    double bonus;
    boolean proc;

    public HourlyEmployee(String id, String level, double hours, double bonus, boolean proc) throws BonusNotAllowedException {
        this.type = "H";
        this.id = id;
        this.level = level;
        this.hours = hours;
        calculateValue(level, bonus, proc);
        this.proc = proc;
    }

    private void calculateValue(String level, double bonus, boolean proc) throws BonusNotAllowedException {
        val = PayrollSystem.hourlyRateByLevel.get(level);
        if (proc) {
            if (bonus > 20) throw new BonusNotAllowedException();
            this.bonus = bonus / 100;
        } else {
            if (bonus > 1000) throw new BonusNotAllowedException();
            this.bonus = bonus;
        }
    }


    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f Regular hours: %.2f Overtime hours: %.2f Bonus: %.2f", id, getLevel(), salary(), regular(), overTime(), calculateBonus());
    }

    @Override
    public double getOverTime() {
        return overTime() * 1.5 * val;
    }

    @Override
    public int getTicketsCount() {
        return -1;
    }

    double regular() {

        if (hours > 40) return 40;
        else return hours;
    }

    double overTime() {
        if (hours > 40) return hours - 40;
        return 0;
    }


    public double salary() {

        if (hours > 40) {
            return 40 * val + (hours - 40) * val * 1.5;
        } else return hours * val;
    }

    public double calculateBonus() {
        if (proc) {
            return salary() * bonus;
        } else return bonus;
    }
}

class FreelanceEmployee implements Employee {
    String type;

    String id;
    String level;
    List<Integer> list;
    double val;
    double bonus;
    boolean proc;

    public FreelanceEmployee(String id, String level, List<Integer> list, double bonus, boolean proc) throws BonusNotAllowedException {
        this.type = "F";
        this.id = id;
        this.level = level;
        this.list = list;
        calculateValue(level, bonus, proc);
        this.proc = proc;
    }

    void calculateValue(String level, double bonus, boolean proc) throws BonusNotAllowedException {
        val = PayrollSystem.ticketRateByLevel.get(level);
        if (proc) {
            if (bonus > 20) throw new BonusNotAllowedException();
            this.bonus = bonus / 100;

        } else {
            if (bonus > 1000) throw new BonusNotAllowedException();
            this.bonus = bonus;
        }
    }

    @Override
    public double getOverTime() {
        return -1;
    }

    @Override
    public int getTicketsCount() {
        return list.size();
    }

    @Override
    public String getLevel() {
        return level;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f Tickets count: %d Tickets points: %d Bonus: %.2f", id, getLevel(), salary(), list.size(), getPoints(), calculateBonus());
    }

    public double calculateBonus() {
        if (proc) {
            return salary() * bonus;
        } else return bonus;
    }

    private int getPoints() {
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    public double salary() {
        return getPoints() * val;
    }
}

class EmployeeFactory {
    public static Employee Create(String s) throws BonusNotAllowedException {
        String[] splitter = s.split(";");
        String type = splitter[0];
        String id = splitter[1];
        String level = splitter[2];

        String[] split2 = s.split("\\s+");
        boolean proc = false;
        double bonus;
        if (s.contains("%")) {
            proc = true;
            bonus = Double.parseDouble(split2[1].split("%")[0]);
        } else {

           if(s.trim().contains(" "))
                bonus=Double.parseDouble(s.split("\\s+")[1]);
           else
               bonus = Double.parseDouble(splitter[splitter.length - 1]);
        }
        split2 = splitter[3].split("\\s+");
        if (type.equals("H")) return new HourlyEmployee(id, level, Double.parseDouble(split2[0]), bonus, proc);
        else {
            List<Integer> list = new ArrayList<>();
            for (int i = 3; i < splitter.length - 1; i++)
                list.add(Integer.parseInt(splitter[i]));
            return new FreelanceEmployee(id, level, list, bonus, proc);
        }
    }

    public static String extractLevel(String s) {
        return s.split(";")[2];
    }

}

class PayrollSystem {
    static Map<String, Double> hourlyRateByLevel;
    static Map<String, Double> ticketRateByLevel;
    List<Employee> employees;

    public PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        PayrollSystem.hourlyRateByLevel = hourlyRateByLevel;
        PayrollSystem.ticketRateByLevel = ticketRateByLevel;
        employees = new ArrayList<>();
    }

    public Employee createEmployee(String line) throws BonusNotAllowedException {
        Employee employee = EmployeeFactory.Create(line);
        employees.add(employee);
        return employee;
    }

    public Map<String, Double> getOvertimeSalaryForLevels() {

        Map<String, Double> levelOvertime = new TreeMap<>();
        for (Employee e : employees) {
            if (e.getOverTime() != -1) {
                levelOvertime.putIfAbsent(e.getLevel(), e.calculateBonus());
                levelOvertime.computeIfPresent(e.getLevel(), (k, v) -> {
                    v = v + e.calculateBonus();
                    return v;
                });
            }
        }
        return levelOvertime;
    }

    public void printStatisticsForOvertimeSalary() {
        DoubleSummaryStatistics ds = employees.stream().filter(i -> i.getOverTime() != -1).mapToDouble(Employee::getOverTime).summaryStatistics();
        System.out.printf("Statistics for overtime salary: Min: %.2f Average: %.2f Max: %.2f Sum: %.2f", ds.getMin(), ds.getAverage(), ds.getMax(), ds.getSum());
    }

    public Map<String, Integer> ticketsDoneByLevel() {
        Map<String, Integer> ticketsLevel = new TreeMap<>();
        for (Employee e : employees) {
            if (e.getTicketsCount() != -1) {
                ticketsLevel.computeIfPresent(e.getLevel(), (k, v) -> {
                    v += e.getTicketsCount();
                    return v;
                });
                ticketsLevel.putIfAbsent(e.getLevel(), e.getTicketsCount());
            }
        }
        return ticketsLevel;
    }

    public Collection<Employee> getFirstNEmployeesByBonus(int n) {
        return employees.stream().sorted(Comparator.comparing(Employee::calculateBonus, Comparator.reverseOrder())).limit(n).collect(Collectors.toList());
    }
}

class BonusNotAllowedException extends Exception {
    public BonusNotAllowedException() {
        super();
    }
}