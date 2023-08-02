package ChatRoom;

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
            System.out.println();
            System.out.println(cr);
            n = jin.nextInt();
            if (n == 0) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr2.addUser(jin.next());
                if (k == 1) cr2.removeUser(jin.next());
                if (k == 2) cr2.hasUser(jin.next());
            }
            System.out.println(cr2);
        }
        if (k == 1) {
            ChatSystem cs = new ChatSystem();
            Method[] mts = cs.getClass().getMethods();
            while (true) {
                String cmd = jin.next();
                if (cmd.equals("stop")) break;
                if (cmd.equals("print")) {
                    System.out.println(cs.getRoom(jin.next()) + "\n");
                    continue;
                }
                for (Method m : mts) {
                    if (m.getName().equals(cmd)) {
                        String[] params = new String[m.getParameterTypes().length];
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
    List<String> users;

    public ChatRoom(String name) {
        users = new ArrayList<>();
        this.name = name;
    }

    public void addUser(String username) {
        users.add(username);
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s\n", name));
        if (users.isEmpty())
            return stringBuilder.append("EMPTY\n").toString();
        Collections.sort(users);
        for (String u : users)
            stringBuilder.append(u).append("\n");
        return stringBuilder.toString();
    }

    public boolean hasUser(String username) {
        return users.contains(username);
    }

    public int numUsers() {
        return users.size();
    }
}

class ChatSystem {

    TreeMap<String, ChatRoom> rooms;

    public ChatSystem() {
        rooms = new TreeMap<>();
    }

    public void addRoom(String roomName) {
        rooms.put(roomName, new ChatRoom(roomName));
    }

    public void removeRoom(String roomName) throws NoSuchRoomException {
        if (rooms.containsKey(roomName))
            rooms.remove(roomName);
        else
            throw new NoSuchRoomException(roomName);
    }

    public ChatRoom getRoom(String name) throws NoSuchRoomException {
        if (rooms.containsKey(name))
            return rooms.get(name);
        else
            throw new NoSuchRoomException(name);
    }

    public void register(String name) {
        int min = Integer.MAX_VALUE;
        LinkedList<ChatRoom> rooms1 = new LinkedList<ChatRoom>();

        for (ChatRoom ch : rooms.values()) {
            if (ch.numUsers() < min)
                min = ch.numUsers();
            if (min == ch.numUsers())
                rooms1.add(ch);
        }
        if (rooms1.isEmpty())
            return;
        rooms1.getFirst().addUser(name);

    }

    public void joinRoom(String userName, String roomName) throws NoSuchRoomException {
        ChatRoom ch = getRoom(roomName);
        ch.addUser(userName);
    }

    public void registerAndJoin(String userName, String roomName) throws NoSuchRoomException {
    register(userName);
    joinRoom(userName,roomName);
    }
    public void leaveRoom(String userName,String roomName) throws NoSuchRoomException {
        ChatRoom ch=getRoom(roomName);
        ch.removeUser(userName);
    }
    public void followFriend(String user,String friend) throws NoSuchUserException {
        List<ChatRoom> rooms1=presentIn(friend);
        if(rooms1.isEmpty())
            throw new NoSuchUserException(friend);
        for(ChatRoom room:rooms1)
        {
            room.addUser(user);
        }
    }

    private List<ChatRoom> presentIn(String friend) {
        List<ChatRoom> rooms1=new ArrayList<>();
        for(ChatRoom room: rooms.values())
        {
            if(room.hasUser(friend))
                rooms1.add(room);
        }
        return  rooms1;
    }


}

class NoSuchRoomException extends Exception {
    public NoSuchRoomException(String name) {
        super(name);
    }
    public NoSuchRoomException() {
        super("default");
    }
}
class NoSuchUserException extends  Exception
{
    public NoSuchUserException() {
        super("default");
    }

    public NoSuchUserException(String user) {
        super(user);
    }
}