package MP3Player;

import java.util.ArrayList;
import java.util.List;

class Song {
    String title;
    String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title=" + title +
                ", artist=" + artist +
                '}';
    }
}

class MP3Player {

    List<Song> songs;
    boolean paused;
    int playing;
    boolean stopped;

    public MP3Player() {
        this.songs = new ArrayList<>();
        paused = false;
        playing = 0;
    }

    public MP3Player(List<Song> songs) {
        this.songs = songs;
        paused = true;
        playing = 0;
    }

    public void pressPlay() {
        if (paused) {
            System.out.println("Song " + playing + " is playing");
            paused = false;
            stopped=false;
        } else System.out.println("Song is already playing");
    }

    public void pressStop() {
        if(stopped)
        {
            System.out.println("Songs are already stopped");
            return;
        }
        if (paused) {
            System.out.println("Songs are stopped");
            playing = 0;
            stopped=true;
        } else {
            System.out.println("Song " + playing + " is paused");
            paused = true;
        }
    }

    public void pressFWD() {
        paused = true;
        if (playing + 1 == songs.size()) playing = 0;
        else playing++;
        System.out.println("Forward...");
    }

    public void pressREW() {

        paused = true;
        if (playing == 0) {
            playing = songs.size() - 1;


        } else {
            playing--;

        }
        System.out.println("Reward...");
    }

    public void printCurrentSong() {
        System.out.println(songs.get(playing));
    }

    @Override
    public String toString() {
        return "MP3Player{currentSong = "+playing+", songList = "+songs+"}";

    }
}

public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);


        System.out.println(player.toString());
        System.out.println("First test");


        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");


        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");


        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}

//Vasiot kod ovde