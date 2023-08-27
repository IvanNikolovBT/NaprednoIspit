//package StadiumTest;

import java.util.*;

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


class Sector {
    String name;
    int size;
    HashMap<Integer, Integer> taken;
    HashSet<Integer> types;

    public Sector(String name, int seats) {
        this.name = name;
        this.size = seats;
        taken = new HashMap<Integer, Integer>();
        types = new HashSet<Integer>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats() {
        return size;
    }

    public void setSeats(int seats) {
        this.size = seats;
    }

    public HashMap<Integer, Integer> getTaken() {
        return taken;
    }

    public void setTaken(HashMap<Integer, Integer> taken) {
        this.taken = taken;
    }

    public HashSet<Integer> getTypes() {
        return types;
    }

    public void setTypes(HashSet<Integer> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%", name, free(), size, freePer());
    }

    public int free() {
        return size - taken.size();
    }

    public double freePer() {
        return (size - free() *100.0/ size);
    }

    public void takeSeat(int seat, int type) throws SeatNotAllowedException {

        if (type == 1) {
            if (types.contains(2))
                throw new SeatNotAllowedException();
        } else if (type == 2) {
            if (types.contains(1))
                throw new SeatNotAllowedException();
        }
        taken.put(seat, type);
        types.add(type);
    }
    public boolean isSeatTaken(int seat)
    {
        return taken.containsKey(seat);
    }

}

class Stadium {
    String name;
    List<Sector> sectorList;

    public Stadium(String name) {
        this.name = name;
        sectorList = new ArrayList<>();
    }

    public void createSectors(String[] sectorNames, int[] sizes) {
        for (int i = 0; i < sectorNames.length; i++) {
            sectorList.add(new Sector(sectorNames[i], sizes[i]));
        }
    }

    public void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        int i = 0;
        Sector sector = null;
        for (i = 0; i < sectorList.size(); i++) {
            if (sectorList.get(i).getName().equals(sectorName)) {
               sector = sectorList.get(i);
                break;
            }
        }
        if (sector.isSeatTaken(seat))
            throw new SeatTakenException();
        sector.takeSeat(seat,type);
    }

    public void showSectors() {
    sectorList.stream().sorted(Comparator.comparing(Sector::free).reversed()).forEach(r-> System.out.println(r));
    }

}


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
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}
