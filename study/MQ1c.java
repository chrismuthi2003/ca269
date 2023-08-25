public class MQ1c
{
    public static void main(String[] args)
    {
        Q1c A = new Q1c("Test", 1, -4);
        System.out.println(A);
        Q1c B = new Q1c("Test2", -2);
        System.out.println(B);
        Q1c C = new Q1c("Test3", -3);
        System.out.println(C);
    }
}

/**
 * class Q1c representing Questions
 */

class Q1c
{
    private String title; /** Question title */
    int number; /** Question number */
    private int marks; /** Question marks */
    private static int MQUESTION_COUNT = 0; /** Count of Questions created */

    /**
     * Default Constructor, sets title to "untitled" and number and marks to 0
     */

    Q1c()
    {
        this.title = "untitled";
        this.number = 0;
        this.marks = 0;
    }

    /**
     * Constructor for creating a Question
     * @param   title   Question title
     * @param   number  Question number
     * @param   marks   Question marks
     */

    Q1c(String title, int number, int marks)
    {
        this.title = title;
        this.number = number;
        setMarks(marks);
    }

    /**
     * Constructor for creating a Question
     * @param   title   Question title
     * @param   marks   Question marks
     * The number is automatically set incrementally using a central counter
     */


    Q1c(String title, int marks)
    {
        this.title = title;
        this.MQUESTION_COUNT += 1;
        setMarks(marks);
    }

    /**
     * Getters and Setters
     */

    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public int getNumber()
    {
        return number;
    }
    public void setNumber(int number)
    {
        this.number = number;
    }
    public int getMarks()
    {
        return marks;
    }

    /**
     * setMarks method checks the inputted mark
     * @param   marks   Marks for the question, valid only when 0 or above. Invalid marks are set to 0
     */

    public void setMarks(int marks)
    {
        if(marks < 0)
        {
            marks = 0;
        }
        this.marks = marks;
    }

    /**
     * toString method prints out the question number, the question title and the question's marks
     * @return  String  description as <number> <title> (<marks> marks)
     */

    public String toString()
    {
        String message = this.MQUESTION_COUNT + " "+ title + " (" + marks + " marks)";
        return message;
    }
}