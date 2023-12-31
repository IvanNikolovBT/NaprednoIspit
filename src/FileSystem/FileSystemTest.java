package FileSystem;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Partial exam II 2016/2017
 */
public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}
class File implements Comparable<File>
{
    String name;
    int size;
    LocalDateTime created;


    public File(String name, int size, LocalDateTime created) {
        this.name = name;
        this.size = size;
        this.created = created;
    }

    @Override
    public int compareTo(File o) {
        int val=created.compareTo(o.created);
        if(val==0)
        {
            val=name.compareTo(o.name);
            if(val==0)
            {
                val=Integer.compare(size,o.size);
                return val;
            }
            return val;
        }
        return val;
    }
    public boolean ishidden()
    {
        return name.startsWith(".");
    }
    @Override
    public String toString() {
        return String.format("%-10s %5dB %s",name,size,created);
    }
}
class FileSystem
{

    Map<Character,Set<File>>foldersToFile;

    public FileSystem()
    {
        foldersToFile=new TreeMap<>();
    }
    public void addFile(char folder,String name,int size,LocalDateTime createdAt)
    {
        File file=new File(name,size,createdAt);
        foldersToFile.putIfAbsent(folder,new TreeSet<>());
        foldersToFile.computeIfPresent(folder,(k,v)->{v.add(file);return v;});
    }
    public List<File> findAllHiddenFilesWithSizeLessThen(int size)
    {
        return foldersToFile.values().stream().flatMap(Collection::stream).filter(i->i.size<size && i.ishidden()).collect(Collectors.toList());
    }
    public int totalSizeOfFilesFromFolders(List<Character>folders)
    {
        int total=0;
        for(Character c:folders)
            total+=foldersToFile.get(c).stream().mapToInt(i->i.size).sum();

        return total;
    }
    public Map<Integer,Set<File>>byYear()
    {
        return foldersToFile.values().stream().flatMap(Collection::stream).collect(Collectors.groupingBy(i->i.created.getYear(),TreeMap::new,Collectors.toCollection(HashSet::new)));
    }
    public Map<String, Long> sizeByMonthAndDay()
    {
        Map<String,Long> val=new TreeMap<>();
        for(Set<File> f :foldersToFile.values())
        {
            for(File a:f)
            {
                val.computeIfPresent(String.format("%s-%s",a.created.getMonth(),a.created.getDayOfMonth()), (k,v)->{v=v+a.size;return v;});
                val.putIfAbsent(String.format("%s-%s",a.created.getMonth(),a.created.getDayOfMonth()), (long) a.size);

            }
        }
        return val;
       }
}
