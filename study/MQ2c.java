public class MQ2c
{
    public static void main(String[] args)
    {
        Person person = new Person("Chris");
        System.out.println(person);
        Employee employee = new Employee("Chris");
        CompanyGathering output = new CompanyGathering();
        System.out.println(output.admitPerson(person));
        System.out.println(output.admitPerson(employee));
    }
}

class Person
{
    private String name;

    Person(String name)
    {
        this.name = name;
    }
}

class Employee extends Person
{
    private String empoyeeID;

    Employee(String name)
    {
        super(name);
    }
}

interface VIP {
    String VIPStatus();
}

class CompanyGathering
{
    public boolean admitPerson(Person person)
    {
        return ((person instanceof VIP) || (person instanceof Employee));
    }
}
