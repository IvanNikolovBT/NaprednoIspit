//package PayrollSystem;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class PayrollSystemTest {

    public static void main(String[] args) {

        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 10 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5 + i * 2.5);
        }

        PayrollSystem payrollSystem = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);

        System.out.println("READING OF THE EMPLOYEES DATA");
        payrollSystem.readEmployees(System.in);

        System.out.println("PRINTING EMPLOYEES BY LEVEL");
        Set<String> levels = new LinkedHashSet<>();
        for (int i = 5; i <= 10; i++) {
            levels.add("level" + i);
        }
        Map<String, Collection<Employee>> result = payrollSystem.printEmployeesByLevels(System.out, levels);
        result.forEach((level, employees) -> {
            System.out.println("LEVEL: " + level);
            System.out.println("Employees: ");
            employees.forEach(System.out::println);
            System.out.println("------------");
        });


    }
}

interface Employee  extends Comparable<Employee> {
    String getID();

    double salary();

    String getLevel();

    @Override
    default int compareTo(Employee o) {
        return - Double.compare(this.salary(),o.salary());


    }
}

class HourlyEmployee implements Employee {

    String type;


    double val;
    String id;
    String level;
    double hours;

    public HourlyEmployee(String id, String level, double hours) {
        this.type = "H";
        this.id = id;
        this.level = level;
        this.hours=hours;
        val = PayrollSystem.hourlyRateByLevel.get(level);
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
        return String.format("Employee ID: %s Level: %s Salary: %.2f Regular hours: %.2f Overtime hours: %.2f", id, getLevel(), salary(), regular(), overTime());
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
}

class FreelanceEmployee implements Employee {
    String type;

    String id;
    String level;
    List<Integer> list;
    double val;

    public FreelanceEmployee(String id, String level, List<Integer> list) {
        this.type = "F";
        this.id = id;
        this.level = level;
        this.list = list;
        val = PayrollSystem.ticketRateByLevel.get(level);

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
        return String.format("Employee ID: %s Level: %s Salary: %.2f Tickets count: %d Tickets points: %d", id, getLevel(), salary(), list.size(), getPoints());
    }

    private int getPoints() {
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    public double salary() {
        return getPoints() * val;
    }
}

class EmployeeFactory {
    public static Employee Create(String s) {
        String[] splitter = s.split(";");
        String type = splitter[0];
        String id = splitter[1];
        String level = splitter[2];

        if (type.equals("H"))
            return new HourlyEmployee(id, level, Double.parseDouble(splitter[3]));
        else {
            List<Integer> list = new ArrayList<>();
            for (int i = 3; i < splitter.length; i++)
                list.add(Integer.parseInt(splitter[i]));
            return new FreelanceEmployee(id, level, list);}
    }

    public static String extractLevel(String s) {
        return s.split(";")[2];
    }

}

class PayrollSystem {

    static Map<String, Double> hourlyRateByLevel;
    static Map<String, Double> ticketRateByLevel;
    Map<String, Employee> employeeMap;

    PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        PayrollSystem.hourlyRateByLevel = hourlyRateByLevel;
        PayrollSystem.ticketRateByLevel = ticketRateByLevel;
        employeeMap = new HashMap<>();
    }

    public void readEmployees(InputStream is) {
        Scanner scanner = new Scanner(is);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("kraj"))
                break;
            Employee employee = EmployeeFactory.Create(line);
            employeeMap.put(employee.getID(), employee);
        }
        scanner.close();
    }

    Map<String, Collection<Employee>> printEmployeesByLevels(OutputStream os, Set<String> levels) {

    return employeeMap.values().stream().filter(i->levels.contains(i.getLevel())).sorted(Comparator.comparing(Employee::salary, Comparator.reverseOrder()).thenComparing(Employee::getLevel)).
            collect(Collectors.groupingBy(i->i.getLevel(),TreeMap::new,Collectors.toCollection(TreeSet::new)));
        //        PrintWriter pw = new PrintWriter(os);
//    for (String l : levels)
//            employeeMap.values().stream().sorted(Comparator.comparing(Employee::salary, Comparator.reverseOrder()).thenComparing(Employee::getLevel)).forEach(pw::println);
//
//        ;
//        pw.flush();

    }
}