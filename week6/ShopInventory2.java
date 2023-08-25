import java.util.Stack;
import java.util.Queue;
import java.util.Map;
import java.util.LinkedList;
import java.util.LinkedHashMap;

class Item
{
    final String name;
    final double price;

    Item(String name, double price)
    {
        this.name = name;
        this.price = price;
    }

    public String getName()
    {
        return this.name;
    }

    public double getPrice()
    {
        return this.price;
    }

    public String toString()
    {
        return this.name + " " + this.price;
    }
}

class Basket
{
    private final Stack<Item> basket;

    Basket()
    {
        //TODO - create the stack
        this.basket = new Stack<Item>();
    }

    public void addItem(Item item)
    {
        //TODO
        this.basket.push(item);
    }

    public boolean isEmpty()
    {
        return this.basket.empty();
    }

    public Item removeItem()
    {
        if(this.basket.isEmpty())
        {
            return null;
        }
        else
        {
            return this.basket.pop();
        }
    }

    public String toString()
    {
        return "basket:" + this.basket.toString();
    }
}

class Checkout
{
    private final Queue<Item> checkout;

    Checkout(Basket basket)
    {
        //TODO - create the queue, add items from basket by using addItem()
        this.checkout = new LinkedList<Item>();
        Item item = basket.removeItem();
        basket.isEmpty();
        while(basket.isEmpty() == false)
        {
            this.checkout.add(item);
            item = basket.removeItem();
        }
        this.checkout.add(item);
    }

    public void addItem(Item item)
    {
        //TODO
        this.checkout.add(item);
    }

    public Item removeItem()
    {
        if(this.checkout.peek() != null)
        {
            return this.checkout.remove();
        }
        else
        {
            return this.checkout.peek();
        }
    }

    public boolean isEmpty()
    {
        return this.checkout.isEmpty();
    }

    public String toSting()
    {
        return "checkout:" + this.checkout.toString();
    }
}

class Bill
{
    private final Map<String, Integer> basket;
    private double price;

    Bill(Checkout checkout)
    {
        //TODO - initialise Map, set price, bill items from checkout
        this.basket = new LinkedHashMap<String, Integer>();
        this.price = 0;
        Item item = checkout.removeItem();
        checkout.isEmpty();
        while(checkout.isEmpty() == false)
        {
            this.billItem(item);
            item = checkout.removeItem();
        }
        this.billItem(item);
    }

    public void billItem(Item item)
    {
        //TODO - put item in map, keep count of same items being billed
        // Note that the Map is from String to Integer
        // Items have names as Strings and count of items is an integer
        if(this.basket.containsKey(item.getName()))
        {
            this.basket.put(item.getName(), this.basket.get(item.getName()) + 1);
            this.price += item.getPrice();

        }
        else
        {
            this.basket.put(item.getName(), 1);
            this.price += item.getPrice();

        } 
    }

    public double getTotal()
    {
        return this.price;
    }

    public String toString()
    {
        String output = "";
        for(String item: this.basket.keySet())
        {
            output += item + " (" + this.basket.get(item) + "nos)\n";
        }
        return output + "total: " + this.price;
    }
}

public class ShopInventory2
{
    public static void main(String[] args)
    {
        Basket basket = new Basket();
        loadBasket(basket);
        //System.out.println(basket); // for DEBUG
        Checkout checkout = new Checkout(basket);
        //System.out.println(checkout); // for DEBUG
        Bill bill = new Bill(checkout);
        System.out.println(bill);
    }

    static void loadBasket(Basket basket)
    {
        basket.addItem(new Item("Twinings Earl Grey Tea", 4.50));
        basket.addItem(new Item("Folans Orange Marmalade", 4.00));
        basket.addItem(new Item("Free-range Eggs", 3.35));
        basket.addItem(new Item("Brennan's Bread", 2.15));
        basket.addItem(new Item("Ferrero Rocher", 7.00));
        basket.addItem(new Item("Ferrero Rocher", 7.00));
    }
}
