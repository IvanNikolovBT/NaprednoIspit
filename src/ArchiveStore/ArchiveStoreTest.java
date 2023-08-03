package ArchiveStore;

import java.time.LocalDateTime;
import java.util.*;

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60 * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            int open = scanner.nextInt();
            if(open==-1)
                break;
            try {
                store.openItem(open, date);
            } catch (NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}

interface Archive {
    public int getId();

    public String setArchived(Date date);

    public String  open(int id, Date date);
}

class LockedArchive implements Archive {
    int id;

    @Override
    public String open(int id, Date date) {
        if (date.after(dateToOpen))
            return String.format("Item %d opened at %s\n", id, date);
        else
            return String.format("Item %d cannot be opened before %s\n", id, dateToOpen);
    }

    Date dateToOpen;
    Date archived;

    public LockedArchive(int id, Date dateToOpen) {
        this.id = id;
        this.dateToOpen = dateToOpen;
        archived = null;
    }

    @Override
    public int getId() {
        return id;
    }

    public String  setArchived(Date archived) {
        this.archived = archived;
        return String.format("Item %d archived at %s\n", id, archived);
    }



}

class SpecialArchive implements Archive {
    int id;
    final int maxOpen;
    Date archived;
    int timesLeft;
    @Override
    public String open(int id, Date date) {
        if (timesLeft > 0) {
            timesLeft--;
            return  String.format("Item %d opened at %s\n", id, date);
        } else
            return String.format("Item %d cannot be opened more than %d times\n", id,maxOpen);
    }

    public SpecialArchive(int id, int maxOpen) {
        this.id = id;
        this.maxOpen = maxOpen;
        timesLeft=maxOpen;
        archived = null;
    }

    @Override
    public int getId() {
        return id;
    }


    public String setArchived(Date archived) {
        this.archived = archived;
        return String.format("Item %d archived at %s\n", id, archived);
    }

}

class ArchiveStore {
    StringBuilder stringBuilder;
    Map<Integer, Archive> archiveMap;

    public ArchiveStore() {
        archiveMap = new TreeMap<>();
        stringBuilder=new StringBuilder();
    }

    public void archiveItem(Archive item, Date date) {
        stringBuilder.append(item.setArchived(date));
        archiveMap.put(item.getId(), item);
    }

    public void openItem(int id, Date date) throws NonExistingItemException {

        if (!archiveMap.containsKey(id)) throw new NonExistingItemException(id);
        Archive item = archiveMap.get(id);
        stringBuilder.append(item.open(id,date));
      }

    public String getLog() {
        return stringBuilder.toString();
    }
}

class NonExistingItemException extends Exception {
    public NonExistingItemException(int id) {
        super("Item with id " + id + " doesn't exist");
    }
}


