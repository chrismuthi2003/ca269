public class Book
{
    String title;
    String author;
    int publishedDate;
    int readDate;
    BookGenre genre;
    BookMedium medium;
    BookRating rating;

    Book(String title, String author, BookGenre genre)
    {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
    Book(String title, String author, BookGenre genre, int publishedDate)
    {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publishedDate = publishedDate;
    }
    Book(String title, String author, BookGenre genre, int publishedDate, int readDate, BookMedium medium, BookRating rating)
    {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publishedDate = publishedDate;
        this.readDate = readDate;
        this.medium = medium;
        this.rating = rating;
    }

    public String getTitle()
    {
        return title;
    }
    public BookGenre getGenre()
    {
        return genre;
    }
    public BookMedium getMedium()
    {
        return medium;
    }
    public String getAuthor()
    {
        return author;
    }
    public int getPublishedDate()
    {
        return publishedDate;
    }
    public int getReadDate()
    {
        return readDate;
    }
    public BookRating getRating()
    {
        return rating;
    }
    public void setGenre(BookGenre genre)
    {
        this.genre = genre;
    }
    public void setMedium(BookMedium medium)
    {
        this.medium = medium;
    }
    public void setPublishedDate(int publishedDate)
    {
        this.publishedDate = publishedDate;
    }
    public void setReadDate(int readDate)
    {
        this.readDate = readDate;
    }
    public void setRating(BookRating rating)
    {
        this.rating = rating;
    }
    

    public String toString()
    {
        if(this.publishedDate == 0)
        {
            String message = this.title + " by " + this.author;
            return message;
        }
        else if(this.readDate == 0)
        {
            String message = this.title + " by " + this.author + " (" + this.publishedDate + ")";
            return message;
        }
        else
        {
            String message = this.title + " by " + this.author + " (" + this.publishedDate + ")" + " - read in " + this.readDate + ", rated " + this.rating.getValue() + "/5";
            return message;
        }
    }

    public static void main(String[] args)
    {
        Book b1 = new Book("Children of Time", "Adrian Tchaikovsky", BookGenre.Fiction);
        System.out.println(b1);
        Book b2 = new Book("The Fifth Season", "N. K. Jemesin", BookGenre.Fiction, 2015);
        System.out.println(b2);
        Book b3 = new Book("Perdido Street Station", "China Mieville", BookGenre.Fiction, 2000, 2020, BookMedium.EBook, BookRating.Rating5);
        System.out.println(b3);
    }
}

enum BookGenre
{
    Fiction, NonFiction;
}

enum BookMedium
{
    PhysicalBook, EBook, AudioBook;
}

enum BookRating
{
    Rating5(5), Rating4(4), Rating3(3), Rating2(2), Rating1(1), Rating0(0);

    private int value;
    
    private BookRating(int value)
    {
        this.value = value;
    }
    public int getValue()
    {
        return value;
    }
}
