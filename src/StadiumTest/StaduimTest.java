package StadiumTest;//package StadiumTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}

class Sector {
    String code;
    int size;
    Map<Integer, Integer> seats;
    int type;

    public Sector(String code, int size) {
        this.code = code;
        this.size = size;
        seats=new HashMap<>();
        setType(0);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void buyTicket(int where, int type) {
        seats.put(where, type);
    }

    public int getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public int free() {
        return size-seats.size();
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%", code,free(),size,(size-free())*100.0/size);
    }
}

class Stadium {
    String name;
    Map<String, Sector> sectors;

    public Stadium(String name) {
        this.name = name;
        sectors = new HashMap<>();
    }

    void createSectors(String[] sectorNames, int[] sizes) {
        sectors = IntStream.range(0, sectorNames.length).mapToObj(i -> new Sector(sectorNames[i], sizes[i])).collect(Collectors.toMap(Sector::getCode, i -> i));
    }

    public void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        Sector sector = sectors.get(sectorName);
        check(seat, type, sector);
        if(sector.getType()==0)
        sector.setType(type);
        sector.buyTicket(seat, type);
    }

    private static void check(int seat, int type, Sector sector) throws SeatTakenException, SeatNotAllowedException {
        if (sector.seats.containsKey(seat)) throw new SeatTakenException();
        if (type == 2 && sector.getType() == 1) throw new SeatNotAllowedException();
        if (type == 1 && sector.getType() == 2) throw new SeatNotAllowedException();


    }

    public void showSectors() {
        sectors.values().stream().sorted(Comparator.comparing(Sector::free,Comparator.reverseOrder()).thenComparing(Sector::getCode)).forEach(System.out::println);
    }
}

class SeatTakenException extends Exception {
    public SeatTakenException() {
        super();
    }
}

class SeatNotAllowedException extends Exception {
    public SeatNotAllowedException() {
        super();
    }
}