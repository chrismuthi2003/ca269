class MQ1b
{
    public static void main(String[] args)
    {
        Q1b A = new Q1b("Test", 1, 3);
        System.out.println(A);
    }
}

class Q1b
{
    private String title;
    int number;
    private int marks;

    public String getTitle()
    {
        return this.title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public int getNumber()
    {
        return this.number;
    }
    public void setNumber(int number)
    {
        this.number = number;
    }
    public int getMarks()
    {
        return this.marks;
    }
    public void setMarks(int marks)
    {
        if(marks < 0)
        {
            marks = 0;
        }
        this.marks = marks;
    }

    Q1b()
    {
        this.title = "untitled";
        this.number = 0;
        this.marks = 0;
    }

    Q1b(String title, int number, int marks)
    {
        this.title = title;
        this.number = number;
        setMarks(marks);
    }

    public String toString()
    {
        String message = number + " " + title + " (" + marks + " marks)";
        return message;
    }
}

