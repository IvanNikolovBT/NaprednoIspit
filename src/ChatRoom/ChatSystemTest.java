package ChatRoom;

import javax.print.CancelablePrintJob;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;

public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr.addUser(jin.next());
                if (k == 1) cr.removeUser(jin.next());
                if (k == 2) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if (n == 0) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr2.addUser(jin.next());
                if (k == 1) cr2.removeUser(jin.next());
                if (k == 2) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if (k == 1) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while (true) {
                String cmd = jin.next();
                if (cmd.equals("stop")) break;
                if (cmd.equals("print")) {
                    System.out.println(cs.getRoom(jin.next()) + "\n");
                    continue;
                }
                for (Method m : mts) {
                    if (m.getName().equals(cmd)) {
                        String params[] = new String[m.getParameterTypes().length];
                        for (int i = 0; i < params.length; ++i) params[i] = jin.next();
                        m.invoke(cs, params);
                    }
                }
            }
        }
    }

}

class ChatRoom {
    String name;
    Set<String> users;

    public ChatRoom(String name) {
        this.name = name;
        users = new TreeSet<>();
    }

    public void addUser(String userName) {
        users.add(userName);
    }

    public void removeUser(String user) {
        users.remove(user);
    }

    boolean hasUser(String user) {
        return users.contains(user);
    }

    public int numUsers() {
        return users.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (users.isEmpty()) return "EMPTY";
        sb.append(name).append("\n");
        users.forEach(i -> sb.append(i).append("\n"));
        return sb.toString();
    }

    public String name() {
        return name;
    }
}

class ChatSystem {
    Set<String> users;
    Map<String, ChatRoom> rooms;

    public ChatSystem() {
        users = new HashSet<>();
        rooms = new TreeMap<>();
    }

    public void addRoom(String rname) {
        ChatRoom chatRoom = new ChatRoom(rname);
        rooms.put(rname, chatRoom);
    }

    public void removeRoom(String rm) {
        rooms.remove(rm);
    }

    public ChatRoom getRoom(String rm) throws NoSuchRoomException {
        if (!rooms.containsKey(rm)) throw new NoSuchRoomException();
        return rooms.get(rm);
    }
    public String getUser(String user) throws NoSuchUserException {
        if(!users.contains(user))
            throw new NoSuchUserException();
        return user;
    }

    public void register(String user) {
        users.add(user);
        ChatRoom chatRoom = rooms.values().stream().min(Comparator.comparing(ChatRoom::numUsers).thenComparing(ChatRoom::name)).orElse(null);
        chatRoom.addUser(user);
    }
    public void registerAndJoin(String user,String room) throws NoSuchRoomException, NoSuchUserException {
        users.add(user);
        joinRoom(user,room);
    }
    public void joinRoom(String user,String room) throws NoSuchRoomException, NoSuchUserException {
        getRoom(room).addUser(getUser(user));
    }
    public void leaveRoom(String user,String room) throws NoSuchRoomException, NoSuchUserException {
        getRoom(room).removeUser(getUser(user));
    }
    public void followFriend(String user,String user2) throws NoSuchRoomException, NoSuchUserException {
        for(Map.Entry<String , ChatRoom> r:rooms.entrySet())
        {
            if(r.getValue().hasUser(user))
                joinRoom(user,r.getKey());
        }
    }
}

class NoSuchRoomException extends Exception {
    public NoSuchRoomException() {
        super();
    }

}
class NoSuchUserException  extends  Exception
{
    public NoSuchUserException()
    {
        super();
    }
}