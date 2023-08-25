import java.util.Scanner;

public class VowelAge {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        int age = input.nextInt();
        int vowels = 0;
        String nameLower = name.toLowerCase();

        for(int i=0; i<name.length(); i++)
        {
            if(nameLower.charAt(i) == 'a' || nameLower.charAt(i) == 'e' || nameLower.charAt(i) == 'i' || nameLower.charAt(i) == 'o' || nameLower.charAt(i) == 'u')
                vowels = vowels + 1;
        }

        if(age < 18)
        {
            System.out.println("Hello " + name + ", you have " + vowels + " vowels, and you are a minor");
        }

        if(age >= 18)
        {
            System.out.println("Hello " + name + ", you have " + vowels + " vowels, and you are an adult");
        }
    }
}
