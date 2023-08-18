package MoviesList;

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
    int [] ratings;

    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings = ratings;
    }

    public double avg()
    {
        return  Arrays.stream(ratings).average().orElse(0);
    }

    public String getTitle() {
        return title;
    }
    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings", title, avg(), ratings.length);
    }
}
class MoviesList
{
    List<Movie> movies;

    public MoviesList( ){
       movies=new ArrayList<>();
    }
    public void addMovie(String title,int[] ratings)
    {
        Movie movie=new Movie(title,ratings);
        movies.add(movie);
    }
    public List<Movie> top10ByAvgRating()
    {
        return movies.stream().sorted(Comparator.comparing(Movie::avg).reversed().thenComparing(Movie::getTitle)).limit(10).collect(Collectors.toList());
    }
    public List<Movie> top10ByRatingCoef()
    {
        int maxRatings=0;
        for(Movie movie:movies)
            maxRatings=Math.max(maxRatings,movie.ratings.length);
        int finalMaxRatings = maxRatings;
        Comparator<Movie> coef=new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                int ar=Double.compare(o1.avg()*o1.ratings.length/ finalMaxRatings,o2.avg()*o2.ratings.length/finalMaxRatings);
                if(ar==0)
                    return o1.title.compareTo(o2.title);
                return -ar;
            }

        };
        Collections.sort(movies,coef);
        if(movies.size()>=10)
            return  movies.subList(0,10);
        return movies;
    }

}