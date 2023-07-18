package TaskManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;


interface ITask {
    public String getCategory();

    public String getName();

    public String getDesc();

    public int getPriority();

    public LocalDateTime getDue();

}

class DeadlineNotValidException extends Exception {
    public DeadlineNotValidException(LocalDateTime deadline) {
        super(String.format("The deadline %s has already passed", deadline));
    }
}

class SimpleTask implements ITask {
    String category;
    String name;
    String desc;

    public SimpleTask(String category, String name, String desc) {
        this.category = category;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public LocalDateTime getDue() {
        return LocalDateTime.MAX;
    }

    @Override
    public String toString() {
        return "Task{name='" + getName() + "', " + "description='" + getDesc() + "'}";
    }
}

abstract class TaskDecorator implements ITask {
    ITask iTask;

    public TaskDecorator(ITask iTask) {
        this.iTask = iTask;
    }
}

class PriorityTask extends TaskDecorator {
    int priority;

    public PriorityTask(ITask iTask, int priority) {
        super(iTask);
        this.priority = priority;
    }


    @Override
    public String getCategory() {
        return iTask.getCategory();
    }

    @Override
    public String getName() {
        return iTask.getName();
    }

    @Override
    public String getDesc() {
        return iTask.getDesc();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public LocalDateTime getDue() {
        return iTask.getDue();
    }

    @Override
    public String toString() {
        return iTask.toString().substring(0, iTask.toString().length() - 1) + ", priority=" + getPriority() + "}";
    }
}

class TimeTaskDecorator extends TaskDecorator {
    LocalDateTime deadline;

    public TimeTaskDecorator(ITask iTask, LocalDateTime deadline) {
        super(iTask);
        this.deadline = deadline;
    }

    @Override
    public String getCategory() {
        return iTask.getCategory();
    }

    @Override
    public String getName() {
        return iTask.getName();
    }

    @Override
    public String getDesc() {
        return iTask.getDesc();
    }

    @Override
    public int getPriority() {
        return iTask.getPriority();
    }

    @Override
    public LocalDateTime getDue() {
        return deadline;
    }

    @Override
    public String toString() {
        return iTask.toString().substring(0, iTask.toString().length() - 1) + ", deadline=" + deadline + '}';
    }
}

class TaskFactory {
    public static ITask createTask(String line) throws DeadlineNotValidException {
        String[] parts = line.split(",");
        String category = parts[0];
        String name = parts[1];
        String desc = parts[2];
        SimpleTask base = new SimpleTask(category, name, desc);
        if (parts.length == 4) {
            try {
                int priority = Integer.parseInt(parts[3]);
            } catch (Exception e) {
                LocalDateTime localDateTime = LocalDateTime.parse(parts[3]);
                checkDeadline(localDateTime);
                return new TimeTaskDecorator(base, localDateTime);
            }
        } else if (parts.length == 5) {
            LocalDateTime localDateTime = LocalDateTime.parse(parts[3]);
            checkDeadline(localDateTime);
            int priority = Integer.parseInt(parts[4]);
            return new PriorityTask(new TimeTaskDecorator(base, localDateTime), priority);

        }
        return base;
    }

    private static void checkDeadline(LocalDateTime localDateTime) throws DeadlineNotValidException {
        if (localDateTime.isBefore(LocalDateTime.now())) throw new DeadlineNotValidException(localDateTime);
    }
}

class TaskManager {
    Map<String, List<ITask>> tasks;

    public TaskManager() {
        this.tasks = new TreeMap<>();
    }

    public void readTasks(InputStream inputStream) {
        tasks=new BufferedReader(new InputStreamReader(inputStream)).lines()
                .map(line -> {
                    try {
                        return TaskFactory.createTask(line);
                    } catch (DeadlineNotValidException e) {
                        System.out.println(e.getMessage());
                    }
                    return  null;
                }).filter(Objects::nonNull).collect(Collectors.groupingBy(ITask::getCategory,TreeMap::new,Collectors.toList()));
    }

    public void printTasks(OutputStream os, boolean includePriority, boolean includeCategory) {

    }
}

public class TasksManagerTest {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        System.out.println("Tasks reading");
        manager.readTasks(System.in);
        System.out.println("By categories with priority");
        manager.printTasks(System.out, true, true);
        System.out.println("-------------------------");
        System.out.println("By categories without priority");
        manager.printTasks(System.out, false, true);
        System.out.println("-------------------------");
        System.out.println("All tasks without priority");
        manager.printTasks(System.out, false, false);
        System.out.println("-------------------------");
        System.out.println("All tasks with priority");
        manager.printTasks(System.out, true, false);
        System.out.println("-------------------------");

    }
}
