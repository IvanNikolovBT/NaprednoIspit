//package MoviesList;

import java.util.*;
import java.util.stream.Collectors;

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

class Movie
{
    String title;
    List<Integer> ratings;

    public Movie(String title, int [] ratings) {
        this.title = title;
        this.ratings=new ArrayList<>();
        toList(ratings);

    }


    private void toList(int[] ratings) {
        for(int i: ratings)
            this.ratings.add(i);
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings",title,avg(),ratings.size());
    }

    public String getTitle() {
        return title;
    }

    public double avg()
    {
        int sum=ratings.stream().mapToInt(i-> i).sum();
        return (double) sum /ratings.size();
    }

}
class MoviesList
{
    List<Movie>movies;
    int len;
    public MoviesList() {
        this.movies = new ArrayList<>();
    }
    public  void addMovie(String title, int[] ratings)
    {
        movies.add(new Movie(title,ratings));
    }
    public List<Movie> top10ByAvgRating()
    {
        return movies.stream().sorted(Comparator.comparing(Movie::avg,Comparator.reverseOrder()).thenComparing(Movie::getTitle)).limit(10).collect(Collectors.toList());
    }

    private  double calculate(Movie m)
    {
        return m.avg()*m.ratings.size()/len;
    }
    public List<Movie> top10ByRatingCoef()
    {
        int maxLen=0;
        for(Movie m:movies)
            maxLen=Math.max(maxLen,m.ratings.size());
        len=maxLen;
        return  movies.stream().sorted(Comparator.comparing(this::calculate,Comparator.reverseOrder()).thenComparing(m->m.title)).limit(10).collect(Collectors.toList());
    }


}
