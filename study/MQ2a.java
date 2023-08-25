public class MQ2a
{
    public static void main(String[] args)
    {
        Person A = new Person("Christopher", "Chris");
        System.out.println(A);
    }
}

class Individual
{
    String name;

    Individual(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        String message = this.name;
        return message;
    }
}

class Person extends Individual
{
    protected String nickname;

    Person(String name, String nickname)
    {
        super(name);
        this.nickname = nickname;
    }

    public String toString()
    {
        String message = this.name + " (" + this.nickname + ")";
        return message;
    }
}

