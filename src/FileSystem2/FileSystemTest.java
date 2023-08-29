package FileSystem2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileSystemTest {

    public static Folder readFolder(Scanner sc) {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < totalFiles; i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String[] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return folder;
    }

    public static void main(String[] args) {

        //file reading from input

        Scanner sc = new Scanner(System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());


    }
}
class IndentPrinter
{
    public static String printIndent(int indent)
    {
        return IntStream.range(0,indent).mapToObj(i->"\t").collect(Collectors.joining());    }
}

interface IFile {
    String getFileName();

    long getFileSize();

    String getFileInfo(int indent);

    void sortBySize();

    long findLargestFile();
}

class File implements IFile, Comparable<IFile> {
    String name;
    long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return size;
    }

    @Override
    public String getFileInfo(int indent) {
        return String.format("%sFile name: %10s File size: %10d\n",
                IndentPrinter.printIndent(indent),
                getFileName(),
                getFileSize());
    }

    @Override
    public void sortBySize() {
        return;
    }

    @Override
    public long findLargestFile() {
        return size;
    }

    @Override
    public int compareTo(IFile o) {
        return Long.compare(size, o.getFileSize());
    }
}

class Folder implements IFile, Comparable<IFile> {
    Map<String, IFile> files;

    String name;
    long size;

    public Folder(String name) {
        this.name = name;
        files = new TreeMap<>();
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return files.values().stream().mapToLong(IFile::getFileSize).sum();
    }

    @Override
    public String getFileInfo(int indent) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%sFolder name: %10s Folder size: %10d\n",
                IndentPrinter.printIndent(indent),
                name,
                this.getFileSize()));

        files.values().forEach(file -> sb.append(file.getFileInfo(indent + 1)));

        return sb.toString();
    }
    public static void createString(StringBuilder sb,int level)
    {
        for(int i=0;i<level;i++)
            sb.append("     ");


    }
    @Override
    public void sortBySize() {

    }

    @Override
    public long findLargestFile() {
        Optional<? extends IFile> largestFile = files.values().stream().max(Comparator.comparing(IFile::getFileSize));
        return largestFile.map(IFile::getFileSize).orElse(0L);
    }

    @Override
    public int compareTo(IFile o) {
        return Long.compare(getFileSize(), o.getFileSize());
    }

    public void addFile(IFile file) throws FileNameExistsException {
        if (files.containsKey(file.getFileName())) throw new FileNameExistsException();
        files.put(file.getFileName(), file);
    }


}
class FileSystem
{
    Folder root;

    public FileSystem() {
        this.root = new Folder("root");
    }
    public  void addFile(IFile file) throws FileNameExistsException {
        root.addFile(file);
    }
    public long findLargestFile()
    {
        return root.findLargestFile();
    }
    public void sortBySize()
    {
        root.sortBySize();
    }

    @Override
    public String toString() {
        return root.getFileInfo(0);
    }
}
class FileNameExistsException extends Exception {
    public FileNameExistsException() {
        super();
    }
}