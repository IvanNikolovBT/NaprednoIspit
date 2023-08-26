package ArchiveStore;

import java.time.LocalDate;
import java.util.*;

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        LocalDate date = LocalDate.of(2013, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();

            LocalDate dateToOpen = date.atStartOfDay().plusSeconds(days * 24 * 60 * 60).toLocalDate();
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
            try {
                store.openItem(open, date);
            } catch (NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}

abstract class Archive {

    int id;
    LocalDate dateArchived;

    public Archive(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    abstract public String open(LocalDate date);

    public LocalDate getDateArchived() {
        return dateArchived;
    }

    public void setDateArchived(LocalDate dateArchived) {
        this.dateArchived = dateArchived;
    }

    abstract public String error();

    abstract boolean canOpen(LocalDate date);
}

class LockedArchive extends Archive {
    LocalDate dateToOpen;

    public LockedArchive(int id, LocalDate dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    @Override
    public String error() {
        return String.format("Item %d cannot be opened before %s\n", id, dateToOpen);
    }

    @Override
    boolean canOpen(LocalDate date) {
        return date.isAfter(dateToOpen);
    }

    @Override
    public String open(LocalDate date) {
        return String.format("Item %d opened at %s\n", id, date);
    }
}

class SpecialArchive extends Archive {
    int maxOpen;
    int opened;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        opened = 0;
    }

    @Override
    public String error() {
        return String.format("Item %d cannot be opened more than %d times\n", id, maxOpen);
    }

    @Override
    boolean canOpen(LocalDate date) {
        return opened < maxOpen;
    }

    @Override
    public String open(LocalDate date) {
        opened++;
        return String.format("Item %d opened at %s\n", id, date);
    }
}

class ArchiveStore {
    StringBuilder stringBuilder;
    TreeMap<Integer, Archive> items;

    public ArchiveStore() {
        items = new TreeMap<>();
        stringBuilder = new StringBuilder();
    }

    public void archiveItem(Archive item, LocalDate date) {
        item.setDateArchived(date);
        items.put(item.id, item);
        stringBuilder.append(String.format("Item %d archived at %s\n", item.id, item.dateArchived));
    }

    public void openItem(int id, LocalDate date) throws NonExistingItemException {
        if (!items.containsKey(id))
            throw new NonExistingItemException(String.format("Item with id %d doesn't exist", id));
        Archive archive = items.get(id);
        if (archive.canOpen(date)) stringBuilder.append(archive.open(date));
        else stringBuilder.append(archive.error());


    }


    public String getLog() {
        return stringBuilder.toString();
    }
}

class NonExistingItemException extends Exception {
    public NonExistingItemException(String msg) {
        super(msg);
    }
}
