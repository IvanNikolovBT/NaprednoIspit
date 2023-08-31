package LogCollector;

import java.util.*;
import java.util.stream.Collectors;

public class LogsTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LogCollector collector = new LogCollector();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.startsWith("addLog")) {
                collector.addLog(line.replace("addLog ", ""));
            } else if (line.startsWith("printServicesBySeverity")) {
                collector.printServicesBySeverity();
            } else if (line.startsWith("getSeverityDistribution")) {
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                if (parts.length == 3) {
                    microservice = parts[2];
                }
                collector.getSeverityDistribution(service, microservice).forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
            } else if (line.startsWith("displayLogs")) {
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                String order = null;
                if (parts.length == 4) {
                    microservice = parts[2];
                    order = parts[3];
                } else {
                    order = parts[2];
                }
                System.out.println(line);

                collector.displayLogs(service, microservice, order);
            }
        }
    }
}

abstract class Log {
    TYPE type;
    String service;
    String microservice;
    String message;
    String timestamp;
    int severity;

    public Log(String service, String microservice, String message, String timestamp) {
        this.service = service;
        this.microservice = microservice;
        this.message = message;
        this.timestamp = timestamp;
    }

    abstract public int getSeverity();

    @Override
    public String toString() {
        return String.format("%s|%s [%s] %s T:%d", service, microservice, type, message, Integer.parseInt(timestamp));

    }

    public TYPE getType() {
        return type;
    }

    public String getService() {
        return service;
    }

    public String getMicroservice() {
        return microservice;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

class InfoLog extends Log {
    public InfoLog(String service, String microservice, String message, String timestamp) {
        super(service, microservice, message, timestamp);
        type = TYPE.INFO;
        setSeverity();
    }

    @Override
    public int getSeverity() {

        return 0;
    }
    public void setSeverity()
    {
        severity=getSeverity();
    }
}

class WarnLog extends Log {
    public WarnLog(String service, String microservice, String message, String timestamp) {
        super(service, microservice, message, timestamp);
        type = TYPE.WARN;
        setSeverity();
    }

    @Override
    public int getSeverity() {
        return 1 + contains();
    }

    private int contains() {
        return message.toLowerCase().contains("might cause error") ? 1 : 0;
    }
    public void setSeverity()
    {
        severity=getSeverity();
    }
}

class ErrorLog extends Log {
    public ErrorLog(String service, String microservice, String message, String timestamp) {
        super(service, microservice, message, timestamp);
        type = TYPE.ERROR;
        setSeverity();
    }

    @Override
    public int getSeverity() {
        return 3 + contains();
    }

    private int contains() {
        int val=0;
        if (message.toLowerCase().contains("fatal")) val+=2;
        if (message.toLowerCase().contains("exception")) val+=3;
        return val;
    }
    public void setSeverity()
    {
        severity=getSeverity();
    }

}

class MicroService {
    String microservice;
    List<Log> logs;

    public MicroService(String microservice) {
        this.microservice = microservice;
        logs = new ArrayList<>();
    }

    public MicroService(Log log) {
        microservice = log.microservice;
        logs = new ArrayList<>();
        logs.add(log);
    }

    public void addLog(Log l) {
        logs.add(l);
    }
}

class Service {

    Map<String, MicroService> microMap;
    String service;



    public Service(Log log) {
        this.service = log.service;
        microMap = new TreeMap<>();
        microMap.put(log.microservice, new MicroService(log));
    }




    public void addMicro(Log log) {
        microMap.computeIfPresent(log.microservice,(k,v)->{v.addLog(log);return v;});
        microMap.putIfAbsent(log.microservice,new MicroService(log));
    }
    public double getAvgSeverity()
    {
        return microMap.values().stream().map(i->i.logs).flatMap(Collection::stream).mapToDouble(Log::getSeverity).average().orElse(0);
    }

    @Override
    public String toString() {
        IntSummaryStatistics allSeveritiesStats = microMap.values().stream().flatMap(microservice -> microservice.logs.stream()).mapToInt(Log::getSeverity).summaryStatistics();

        return String.format("Service name: %s" + " Count of microservices: %d" + " Total logs in service: %d" + " Average severity for all logs: %.2f" + " Average number of logs per microservice: %.2f", service, microMap.size(), allSeveritiesStats.getCount(), allSeveritiesStats.getAverage(), allSeveritiesStats.getCount() / (float) microMap.size());
    }
}

class LogCollector {
    Map<String, Service> serviceMap;

    public LogCollector() {
        this.serviceMap = new TreeMap<>();
    }

    public void addLog(String l) {
        Log log = LogFactory.create(l);
        serviceMap.computeIfPresent(log.service, (k, v) -> {
            v.addMicro(log);
            return v;
        });
        serviceMap.putIfAbsent(log.service, new Service(log));

    }
    public void printServicesBySeverity()
    {
        serviceMap.values().stream().sorted(Comparator.comparing(Service::getAvgSeverity,Comparator.reverseOrder())).forEach(System.out::println);
    }
    TreeMap<Integer, Long> getSeverityDistribution (String service, String microservice)
    {
        if(microservice==null)
            return serviceMap.get(service).microMap.values().stream().map(i->i.logs).flatMap(Collection::stream).collect(Collectors.groupingBy(Log::getSeverity,TreeMap::new,Collectors.counting()));
            else return serviceMap.get(service).microMap.get(microservice).logs.stream().collect(Collectors.groupingBy(Log::getSeverity,TreeMap::new,Collectors.counting()));
    }

    public void displayLogs(String service, String microservice, String order) {
        Comparator<Log> c=ComparatorFactory.create(order);
        if(microservice!=null)
        serviceMap.get(service).microMap.get(microservice).logs.stream().sorted(c).forEach(System.out::println);
        else serviceMap.get(service).microMap.values().stream().map(i->i.logs).flatMap(Collection::stream).sorted(c).forEach(System.out::println);
    }
}

class LogFactory {

    public static Log create(String line) {
        String[] splitter = line.split("\\s+");
        String service = splitter[0];
        String microservice = splitter[1];
        String timestamp = splitter[splitter.length - 1];
        String message = Arrays.stream(splitter).skip(3).limit(splitter.length - 1).collect(Collectors.joining(" "));
        if (splitter[2].equals("INFO")) return new InfoLog(service,microservice,message,timestamp);
        else if (splitter[2].equals("WARN")) return new WarnLog(service,microservice,message,timestamp);
        else return new ErrorLog(service,microservice,message,timestamp);
    }
}
class ComparatorFactory
{

    static Comparator<Log> create(String order)
    {
        switch (order)
        {
            case "NEWEST_FIRST": return Comparator.comparing(Log::getTimestamp).reversed();
            case "OLDEST_FIRST": return Comparator.comparing(Log::getTimestamp);
            case "MOST_SEVERE_FIRST":return Comparator.comparing(Log::getSeverity).reversed();
            case "LEAST_SEVERE_FIRST": return Comparator.comparing(Log::getSeverity);
        }
        return null;
    }
}

enum TYPE {INFO, WARN, ERROR}
