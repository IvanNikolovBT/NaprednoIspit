//package StopCorona;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

interface ILocation {
    double getLongitude();

    double getLatitude();

    LocalDateTime getTimestamp();
}

class User {
    String id;
    String name;
    List<ILocation> iLocations;
    LocalDateTime localDate;
    int timesInContact;
    boolean positive;
    List<User> directContacts;
    public User(String id, String name) {
        this.id = id;
        iLocations = new ArrayList<>();
        this.name = name;
        timesInContact=0;
        positive=false;
        directContacts=new ArrayList<>();
    }

    public void setTimesInContact(int timesInContact) {
        this.timesInContact = timesInContact;
    }

    public void addLocations(List<ILocation> iLocations) {
        this.iLocations = iLocations;
    }

    public void addTime(LocalDateTime timestamp) {
        positive=true;
        localDate = timestamp;
    }



    public static int calcuateIfDirect(User a, User b) {
        List<ILocation> aL = a.iLocations;
        List<ILocation> bL = b.iLocations;
        int times=0;
        for (ILocation iLocation : aL) {
            for (ILocation location : bL) {
                if (checkIfSamePlaceSameTime(iLocation, location))
                    times++;
            }
        }
        a.setTimesInContact(times);
        b.setTimesInContact(times);
        return times;
    }

    public int getTimesInContact() {
        return timesInContact;
    }

    private static boolean checkIfSamePlaceSameTime(ILocation iLocation, ILocation location) {
        return calcualteDistance(iLocation, location) <= 2 && Math.abs(iLocation.getTimestamp().getMinute() - location.getTimestamp().getMinute()) < 5;
    }

    public static double calcualteDistance(ILocation a, ILocation b) {
        return Math.sqrt(Math.pow(a.getLatitude() - b.getLatitude(), 2) + Math.pow(a.getLongitude() - b.getLongitude(), 2));

    }

    public User returnUser() {
    return this;
    }

    public String hiddenId() {
    return id.substring(0,4)+"***";
    }
    public int directContacts()
    {
        return directContacts.size();
    }
}

class StopCoronaApp {
    Map<String, User> users;

    public StopCoronaApp() {
        users = new HashMap<>();
    }

    public void addUser(String name, String id) throws UserAlreadyExistException {
        checkForUser(id);
        users.put(id, new User(id, name));
    }

    private void checkForUser(String id) throws UserAlreadyExistException {
        if (users.containsKey(id))
            throw new UserAlreadyExistException();
    }

    void addLocations(String id, List<ILocation> iLocations) {
        User user = users.get(id);
        user.addLocations(iLocations);
    }

    public void detectNewCase(String id, LocalDateTime timestamp) {
        User user = users.get(id);
        user.addTime(timestamp);
    }

    public Map<User, Integer> getDirectContacts (User u) {
        return users.values().stream().filter(i->User.calcuateIfDirect(i,u)>0).collect(Collectors.toMap(user->user,User::getTimesInContact));
    }
    public Collection<User> getIndirectContacts (User u)
    {
        Map<User,Integer> direct=getDirectContacts(u);
        return direct.keySet().stream().filter(integer -> User.calcuateIfDirect(integer, u) > 0).collect(Collectors.toList());
    }
    public  void createReport()
    {

        for(User u:users.values())
        {

            if(u.positive)
            {
                System.out.printf("%s %s %s\n",u.name,u.id,u.localDate);
                System.out.printf("Direct contacts:\n");
                Map<User,Integer> directForU=getDirectContacts(u);
                for(Map.Entry<User,Integer> d:directForU.entrySet())
                {
                    System.out.printf("%s %s %d\n",d.getKey().name,d.getKey().hiddenId(),d.getKey().timesInContact);
                }
                System.out.printf("Count of direct contacts: %d",directForU.size());
            }
        }
    }
}

class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException() {
        super();
    }
}

public class StopCoronaTest {

    public static double timeBetweenInSeconds(ILocation location1, ILocation location2) {
        return Math.abs(Duration.between(location1.getTimestamp(), location2.getTimestamp()).getSeconds());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StopCoronaApp stopCoronaApp = new StopCoronaApp();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            if(line.equals("KRAJ"))
                break;
            String[] parts = line.split("\\s+");

            switch (parts[0]) {
                case "REG": //register
                    String name = parts[1];
                    String id = parts[2];
                    try {
                        stopCoronaApp.addUser(name, id);
                    } catch (UserAlreadyExistException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "LOC": //add locations
                    id = parts[1];
                    List<ILocation> locations = new ArrayList<>();
                    for (int i = 2; i < parts.length; i += 3) {
                        locations.add(createLocationObject(parts[i], parts[i + 1], parts[i + 2]));
                    }
                    stopCoronaApp.addLocations(id, locations);

                    break;
                case "DET": //detect new cases
                    id = parts[1];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[2]);
                    stopCoronaApp.detectNewCase(id, timestamp);

                    break;
                case "REP": //print report
                    stopCoronaApp.createReport();
                    break;
                default:
                    break;
            }
        }
    }

    private static ILocation createLocationObject(String lon, String lat, String timestamp) {
        return new ILocation() {
            @Override
            public double getLongitude() {
                return Double.parseDouble(lon);
            }

            @Override
            public double getLatitude() {
                return Double.parseDouble(lat);
            }

            @Override
            public LocalDateTime getTimestamp() {
                return LocalDateTime.parse(timestamp);
            }
        };
    }
}
