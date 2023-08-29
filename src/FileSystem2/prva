package FileSystem2;


import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

interface IFile  extends  Comparable<File>{
    public String getFileName();

    public long getFileSize();

    public String getFileInfo(int indent);

    public void sortBySize();

    public long findLargestFile();
}

class File implements IFile {
    String name;
    long size;

    @Override
    public int compareTo(File o) {
        return Long.compare(size,o.size);
    }

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }
    public File(String name) {
        this.name = name;
        this.size = 0;
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
        //TO DO
        return String.format("%sFile name: %10s File size: %10d\n",IndentPrinter.printIndentation(indent),getFileName(),getFileSize());
    }

    @Override
    public void sortBySize() {
        return;
    }

    @Override
    public long findLargestFile() {
        return this.size;
    }


}
class IndentPrinter{

    public  static String printIndentation(int level)
    {
        return IntStream.range(0,level).mapToObj(i->"\t").collect(Collectors.joining());
    }
}
class Folder extends File implements IFile {

    List<IFile> list;


    public Folder(String name) {
        super(name);
        list = new ArrayList<>();
    }
    private boolean checkNameExists(String fileName)
    {
        return list.stream().
                map(IFile::getFileName)
                .anyMatch(name->name.equals(fileName));
    }
    public void addFile(IFile file) throws FileNameExistsException {

        if (checkNameExists(file.getFileName()))
            throw new FileNameExistsException(file.getFileName(),getFileName());
        list.add(file);
        this.size+=file.getFileSize();
        //treba da se azurira spored goleminata
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        // treba goleminata na site datoteki
        return list.stream().mapToLong(IFile::getFileSize).sum();
    }


    @Override
    public String getFileInfo(int indent) {
        //TO DO
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(String.format("%sFile name: %10s File size: %10d\n",IndentPrinter.printIndentation(indent),getFileName(),getFileSize()));
        list.stream().forEach(file-> stringBuilder.append(file.getFileInfo(indent+1)));
    return stringBuilder.toString();
    }

    @Override
    public void sortBySize() {
        list.stream().sorted().forEach(IFile::sortBySize);
    }

    @Override
    public long findLargestFile() {
        OptionalLong largest = list.stream().mapToLong(IFile::findLargestFile).max();
        if(largest.isPresent())
            return largest.getAsLong();
        return 0;
    }


}

class FileSystem {
    Folder root;

    public FileSystem() {
        root=new Folder("root");
    }

    public void addFile(IFile file) throws FileNameExistsException {
        root.addFile(file);

    }

    public long findLargestFile() {

    return root.findLargestFile();
    }
    public void sortBySize()
    {
        root.sortBySize();
    }

    @Override
    public String toString() {
        return this.root.getFileInfo(0);
    }
}

class FileNameExistsException extends Exception {
    public FileNameExistsException(String name, String foldername) {
        super(String.format("There is already a file named %s in the folder %s",name,foldername));
    }
}

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
