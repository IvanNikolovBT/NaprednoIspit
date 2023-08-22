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
        Calendar cal = Calendar.getInstance();
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
        for (Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch (CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

class Category {
    String name;

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

abstract class NewsItem {
    String title;
    Date date;
    Category cateogry;

    public NewsItem(String title, Date date, Category cateogry) {
        this.title = title;
        this.date = date;
        this.cateogry = cateogry;
    }
    public abstract String getTeaser();

    public int when()
    {
        Date now=new Date();
        long ms= now.getTime()-date.getTime();
        return (int)(ms/1000)/60;
    }

}

class TextNewsItem extends NewsItem {
    String text;

    public TextNewsItem(String title, Date date, Category cateogry, String text) {
        super(title, date, cateogry);
        this.text = text;
    }

    @Override
    public String getTeaser() {
        String teaser = text;
        if (text.length() > 80) {
            teaser = text.substring(0, 80);
        }
        return String.format("%s\n%d\n%s\n", title, when(), teaser);
    }
}

class MediaNewsItem extends NewsItem {
    String url;
    int views;

    public MediaNewsItem(String title, Date date, Category cateogry, String url, int views) {
        super(title, date, cateogry);
        this.url = url;
        this.views = views;
    }
    @Override
    public String getTeaser() {
        return String.format("%s\n%d\n%s\n%d\n", title, when(), url, views);
    }
}

class FrontPage {

    Category[] categories;
    List<NewsItem> itemList;

    public FrontPage(Category[] categories) {
        this.categories = categories;
        itemList = new ArrayList<>();
    }

    public void addNewsItem(NewsItem newsItem) {
        itemList.add(newsItem);
    }

    public List<NewsItem> listByCategory(Category category)  {
        return itemList.stream().filter(i -> i.cateogry.equals(category)).collect(Collectors.toList());
    }

    void checkCategory(Category category) throws CategoryNotFoundException {
        for (Category c : categories)
            if (c.name.equals(category.name)) return;
        throw new CategoryNotFoundException(category.toString());
    }
    void checkCategoryByName(String name) throws CategoryNotFoundException {
        for (Category c : categories)
            if (c.name.equals(name)) return;
        throw new CategoryNotFoundException(name);
    }
    public List<NewsItem> listByCategoryName(String name) throws CategoryNotFoundException {
        checkCategoryByName(name);
        int index=-1;
        for(int i=0;i< categories.length;i++)
        {
            if(categories[i].name.equals(name))
            {
                index=i;
                break;
            }
        }
        if(index==-1)
            throw new CategoryNotFoundException(name);
        return listByCategory(categories[index]);
    }

    @Override
    public String toString() {
     StringBuilder stringBuilder=new StringBuilder();
     for(NewsItem i:itemList)
         stringBuilder.append(i.getTeaser());
     return stringBuilder.toString();
    }
}

class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException() {
        super();
    }
    public CategoryNotFoundException(String name)
    {
        super("Category "+name+" was not found");
    }
}