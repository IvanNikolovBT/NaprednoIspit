package FrontPageTest;

import java.util.*;
import java.util.stream.Collectors;

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal;
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

class Category
{
    String category;

    public Category(String category) {
        this.category = category;
    }
}
abstract class NewsItem
{
    String title;
    Date published;
    Category category;

    public NewsItem(String title, Date published, Category category) {
        this.title = title;
        this.published = published;
        this.category = category;
    }
    abstract public String getTeaser();
    public int when()
    {
        Date now=new Date();
        long ms= now.getTime()-published.getTime();
        return (int)(ms/1000)/60;
    }
}
class TextNewsItem extends  NewsItem
{
    String text;



    public TextNewsItem(String title, Date published, Category category, String text) {
        super(title, published, category);
        this.text = text;
    }
    @Override
    public String getTeaser() {
        String val;
        if(text.length()<=80)
            val=text;
        else
            val=text.substring(0,80);
        return String.format("%s\n%d\n%s\n",title,when(),val);

    }
}
class MediaNewsItem extends  NewsItem
{
    String url;



    int views;

    public MediaNewsItem(String title, Date published, Category category, String url, int views) {
        super(title, published, category);
        this.url = url;
        this.views = views;
    }
    @Override
    public String getTeaser() {
        return String.format("%s\n%d\n%s\n%d\n",title,when(),url,views);
    }
}
class FrontPage
{
    List<Category> categories;
    List<NewsItem> newsItems;

    public FrontPage(Category[] categories) {
        this.categories=new ArrayList<>();
        addCategories(categories);
        newsItems=new ArrayList<>();
    }

    public void addNewsItem(NewsItem newsItem)
    {
        newsItems.add(newsItem);
    }

    public List<NewsItem> listByCategory(Category category)
    {
        return  newsItems.stream().filter(i->i.category.category.equals(category.category)).collect(Collectors.toList());
    }
    public List<NewsItem>listByCategoryName(String category) throws CategoryNotFoundException {

        check(category);
        return newsItems.stream().filter(i->i.category.category.equals(category)).collect(Collectors.toList());
    }

    private void check(String category) throws CategoryNotFoundException {
        for(Category c:categories)
            if(c.category.equals(category))
                return;
        throw new CategoryNotFoundException(category);
    }

    private void addCategories(Category[] categories) {
        this.categories.addAll(Arrays.asList(categories));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        for(NewsItem n:newsItems)
            stringBuilder.append(n.getTeaser());
        return stringBuilder.toString();
    }
}
class CategoryNotFoundException  extends  Exception
{
    public  CategoryNotFoundException()
    {
        super();
    }
    public CategoryNotFoundException(String name)
    {
        super("Category "+name+" was not found");
    }
}
