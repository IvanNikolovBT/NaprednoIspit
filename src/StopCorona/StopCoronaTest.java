package StopCorona;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

interface ILocation {
    double getLongitude();

    double getLatitude();

    LocalDateTime getTimestamp();
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
class LocationUtils
{
    public static double distanceBetween(ILocation loc1,ILocation loc2)
    {
        return Math.sqrt(Math.pow(loc1.getLongitude()-loc2.getLongitude(),2)+Math.pow(loc2.getLatitude()-loc1.getLatitude(),2));
    }
    public static double getTimeBetween(ILocation loc1,ILocation loc2)
    {
        return Math.abs(loc1.getTimestamp().getSecond()-loc2.getTimestamp().getSecond());
    }
    public static boolean isDanger(ILocation loc1,ILocation loc2)
    {
        return distanceBetween(loc1,loc2)<=2&&getTimeBetween(loc1,loc2)<300;
    }
    public static int danger(User u1,User u2)
    {
        int count=0;
        for(ILocation i: u1.locations)
        {
            for(ILocation j: u2.locations)
            {
                if(isDanger(i,j))
                    count++;
            }
        }
        return count;
    }

}
class User {
    String id;
    String name;
    List<ILocation> locations;
    LocalDateTime sick;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addLocations(List<ILocation> locationLIst) {
        this.locations = locationLIst;
    }



    public void addTime(LocalDateTime sick) {
    this.sick=sick;
    }
}

class StopCoronaApp {
    static Map<String, User> users;

    public StopCoronaApp() {
        users = new HashMap<>();
    }

    public void addUser(String name, String id) throws UserAlreadyExistException {
        //da registrira nov korisnik, ako veke postoi da frli iskluchok
        //za ova najdobro da ima mapa <id,User>
        User user = new User(id, name);
        if(users.containsKey(user.id))
            throw  new UserAlreadyExistException();
        users.put(id,user);
    }

    public void addLocations(String id, List<ILocation> iLocations) {
        //na ID mu gi dodava lokaciite,znachi go zemame od mapata i samo mu dodavame info
        User user=users.get(id);
        user.addLocations(iLocations);
    }

    public void detectNewCase(String id, LocalDateTime timeStamp) {
        //zema od mapa i mu klava koga fatil korona
        User user=users.get(id);
        user.addTime(timeStamp);
    }

    public Map<User, Integer> getDirectConacts(User u) {
        //site bliski kontakti na korisnikot i so sekoj korisnik kolku pati bil vo kontakt
        // treba da se napraj metoda za sekoj korisnik da presmeta so koj bil vo kontakt
    }

    public Collection<User> getIndirectContacts(User u) {
        // site indirektni na u, da se zemat site direktni kontakti na direktnite kontakti
    }

    public void createReport() {
        //printot ima vrmee
    }

}
class UserAlreadyExistException extends Exception
{
    public UserAlreadyExistException()
    {
        super();
    }
}