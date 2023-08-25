import java.util.Arrays;
import java.util.Scanner;

class XYZ
{     
    Scanner input = new Scanner(System.in);
    String name = input.nextLine();

    public String sayHello(String name)
    {
        return this.name = name;
    }   

    public String toString()
    {
        return "Hello, " + this.name;
    }
}

public class Pokemon_1
{
    // class-only information
    static final String GENERATION = "Gen-I";
    // final = no further changes allowed
    static final String LOCATION = "Kanto";
    static final String[] VERSIONS = { "RED", "GREEN", "YELLOW", "BLUE"};

    // copy for each instance
    int health_max = 100;
    int moves_max = 5;
    String type = "";
    String name = "";

    /* this is a constructor, it 'creates' objects
     * @param String name: the name of the pokemon
     * @param String type: the type of pokemon
     * @patam int health_max: the ma health of that pokemon
     */

    public Pokemon_1(String name, String type, int health_max)
     {
         this.name = name;
         this.type = type;
         if(health_max > 0)
         {
             this.health_max = health_max;
         }
     }

     /* this is the "string" method that is called when we want to print
      * an instance as a string. In this case it returns the name and a
      * summary of that pokemon's type and max health
      */
    
    public String toString()
    {
        return this.name + " (" + this.type + ", " + this.health_max + ")";
    }

    public static void main(String[] args)
    {
        // class specific stuff
        System.out.println(Pokemon_1.GENERATION);
        System.out.println(Pokemon_1.LOCATION);
        System.out.println(Arrays.toString(Pokemon_1.VERSIONS));

        // creating instances with their own variables
        // check the outputs are distinct
        Pokemon_1 Charmander = new Pokemon_1("Charmander", "Fire", 39);
        System.out.println(Charmander);
        Pokemon_1 Bulbasaur = new Pokemon_1("Bulbasaur", "Grass", 45);
        System.out.println(Bulbasaur);
        Pokemon_1 Squirtle = new Pokemon_1("Squirtle", "Water", 44);

        // this also works! Class variables are shared amongst instances
        // check below output that shows this
        System.out.println(Charmander.GENERATION);
        //Charmander.GENERATION = "Gen-II";
        System.out.println(Bulbasaur.GENERATION);
        // change the GENERATION variablr to final and
        // check if the above is still allowed

        //System.out.println(XYZ.sayHello("Java"));
        XYZ x = new XYZ();
        System.out.println(x);
        //System.out.println(x.sayHello("Java"));
    }
}
