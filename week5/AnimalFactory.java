interface SafetyRegulation
{
    int getMaxItemsPermitted();
}

abstract class Item implements SafetyRegulation
{
}

class Duck extends Item
{
    public int getMaxItemsPermitted()
    {
        return 5;
    }
}

class Swan extends Item
{
    public int getMaxItemsPermitted()
    {
        return 2;
    }
}

class Flamingo extends Item
{
    public int getMaxItemsPermitted()
    {
        return 3;
    }
}

class Dog extends Item
{
    public int getMaxItemsPermitted()
    {
        return 20;
    }
}

class Cat extends Item
{
    public int getMaxItemsPermitted()
    {
        return 20;
    }
}

class AnimalFactory<Animal extends Item & SafetyRegulation>
{
    int count;
    
    int getCount()
    {
        return this.count;
    }

    Animal continueProduction(Animal item)
    {
        if(count < item.getMaxItemsPermitted())
        {
            this.count += 1;
            return item;
        }
        else
        {
            return null;
        }
    }

    public static void main(String[] args)
    {
        // main() for testing:
        AnimalFactory<Duck> AF_D = new AnimalFactory<>();
        while(AF_D.continueProduction(new Duck()) != null);
        System.out.println("Total Ducks produced: " + AF_D.getCount());

        AnimalFactory<Swan> AF_S = new AnimalFactory<>();
        while(AF_S.continueProduction(new Swan()) != null);
        System.out.println("Total Swans produced: " + AF_S.getCount());

        AnimalFactory<Flamingo> AF_F = new AnimalFactory<>();
        while(AF_F.continueProduction(new Flamingo()) != null);
        System.out.println("Total Flamingos produced: " + AF_F.getCount());

        AnimalFactory<Dog> AF_G = new AnimalFactory<>();
        while(AF_G.continueProduction(new Dog()) != null);
        System.out.println("Total Dogs produced: " + AF_G.getCount());

        AnimalFactory<Cat> AF_C = new AnimalFactory<>();
        while(AF_C.continueProduction(new Cat()) != null);
        System.out.println("Total Cats produced: " + AF_C.getCount());

        // which produces the output
        // Total Ducks produced: 5
        // Total Swans produced: 2
        // Total Flamingos produced: 3
        // Total Dogs produced: 20
        // Total Cats produced: 20
    }
}
