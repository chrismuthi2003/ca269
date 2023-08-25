public class MQ2b
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

interface FamilyMember extends PersonalDetails
{
    String getNickname();
    void setNickname(String name);
}

interface PersonalDetails
{
    String getName();
    int getAge();
}

class Person extends Individual implements FamilyMember
{
    protected String nickname;
    private int age;

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

    public String getNickname()
    {
        return this.nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getName()
    {
        return this.name;
    }

    public int getAge()
    {
        return this.age;
    }
}
