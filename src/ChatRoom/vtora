//package ChatRoom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

                        // Cast the params array to Object[] to handle potential varargs
                        m.invoke(cs, (Object[]) params);
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

    public void addUser(String username) {
        users.add(username);
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    public boolean hasUser(String username) {
        return users.contains(username);
    }

    public int numUsers() {
        return users.size();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append("\n");
        if (users.isEmpty()) return stringBuilder.append("EMPTY").append("\n").toString();


        for (String s : users)
            stringBuilder.append(s).append("\n");

        return stringBuilder.toString();
    }
}

class ChatSystem {
    TreeMap<String, ChatRoom> rooms;
    TreeSet<String> users;

    public ChatSystem() {
        rooms = new TreeMap<>();
        users = new TreeSet<>();
    }

    public void addRoom(String roomname) {
        ChatRoom chatRoom = new ChatRoom(roomname);
        rooms.put(roomname, chatRoom);
    }

    public void removeRoom(String roomname) {
        rooms.remove(roomname);
    }

    public ChatRoom getRoom(String roomname) throws NoSuchRoomException {
        noSuchRoom(roomname);
        return rooms.get(roomname);
    }

    public void register(String user) {
        users.add(user);
        LinkedList<ChatRoom> min_rooms = new LinkedList<ChatRoom>();
        int min = Integer.MAX_VALUE;
        for (ChatRoom cr : rooms.values()) {
            if (cr.numUsers() < min) {
                min_rooms = new LinkedList<ChatRoom>();
                min = cr.numUsers();
            }
            if (cr.numUsers() == min) min_rooms.add(cr);
        }
        if (min_rooms.isEmpty()) return;
        min_rooms.getFirst().addUser(user);
    }

    public void registerAndJoin(String userName, String roomname) throws NoSuchRoomException {
        register(userName);
        joinRoom(userName,roomname);
    }

    public void joinRoom(String user, String room) throws NoSuchRoomException {
        noSuchRoom(room);
        ChatRoom room1 = rooms.get(room);
        room1.addUser(user);
    }

    private void noSuchRoom(String room) throws NoSuchRoomException {
        if (!rooms.containsKey(room)) throw new NoSuchRoomException();
    }

    public void leaveRoom(String user, String room) throws NoSuchRoomException, NoSuchUserException {
        noSuchRoom(room);
        ChatRoom room1 = rooms.get(room);
        if (!room1.hasUser(user)) throw new NoSuchUserException(user);
        room1.removeUser(user);
    }

    public void followFriend(String username, String friend) throws NoSuchUserException, NoSuchRoomException {
        List<ChatRoom> rooms1 = rooms.values().stream().filter(i -> i.hasUser(friend)).collect(Collectors.toList());
        if (rooms1.isEmpty()) throw new NoSuchUserException(username);
        for (ChatRoom c : rooms1)
            joinRoom(username,c.name);
    }
}

class NoSuchRoomException extends Exception {
    public NoSuchRoomException() {
        super();
    }
}

class NoSuchUserException extends Exception {
    public NoSuchUserException(String msg) {
        super(msg);
    }
}

