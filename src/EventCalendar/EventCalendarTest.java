//package EventCalendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

class Event {
    String name;
    String location;
    Date time;

    public Event(String name, String location, Date time) {
        this.name = name;
        this.location = location;
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String[]splitter=time.toString().split("\\s+");
        String year=splitter[splitter.length-1];
        String hour=splitter[splitter.length-3];
        String month=splitter[1];
        String date=splitter[2];
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(date).append(" ");
        stringBuilder.append(month).append(", ");
        stringBuilder.append(year).append(" ");
        splitter=hour.split(":");
        stringBuilder.append(splitter[0]).append(":").append(splitter[1]).append(" ");

        return String.format("%sat %s, %s", stringBuilder.toString(), location, name);
    }



}

class EventCalendar {
    int year;
    HashMap<Integer, Set<Event>> onDayEvents;
    HashMap<Integer, Integer> byMonth;

    public EventCalendar(int year) {
        this.year = year;
        onDayEvents = new HashMap<>();
        byMonth=new HashMap<>();
        fillMonths();

    }

    public  void fillMonths()
    {
        for(int i=1;i<13;i++)
            byMonth.put(i,0);

    }

    public void addEvent(String name, String location, Date date) throws WrongDateException {
        Event e = new Event(name, location, date);
        if (year != getYear(date)) throw new WrongDateException(String.format("Wrong date: %s", date));
        onDayEvents.putIfAbsent(getDayOfYear(date), new HashSet<>());
        onDayEvents.computeIfPresent(getDayOfYear(date), (k, v) -> {
            v.add(e);
            return v;
        });
        byMonth.computeIfPresent(getMonth(date)+1, (k,v)->{v+=1;return v;});
    }

    public void listEvents(Date date) {
        if(onDayEvents.containsKey(getDayOfYear(date)))
        onDayEvents.get(getDayOfYear(date)).stream().sorted(Comparator.comparing(Event::getTime).thenComparing(Event::getName)).forEach(System.out::println);
        else
            System.out.printf("No events on this day!\n");
    }

    public void listByMonth() {

        for(Map.Entry<Integer,Integer> c:byMonth.entrySet())
            System.out.printf("%d : %d\n",c.getKey(),c.getValue());

    }

    int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

     int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    int getDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

}

class WrongDateException extends Exception {
    public WrongDateException(String msg) {
        super(msg);
    }

}