/*
 * ShopInventory - applying OOP to managing items in a Shop
 */

import java.text.DecimalFormat;

/*
 * Class Item
 * It is abstract so that Shop explicitly declares what items it has through subclasses.
 */

abstract class Item
{
    String name; // TODO: specify the name should not change once assigned expiry is a function of days rather than
    int expiresInDays; // this can also be a Period from the LocalDate library price is a double to allow decimals
    double price;      // and fractional values

    /*
     * TODO: Constructor that assigns a name
     */
    Item(String name)
    {
        this.name = name;
    }

    /*
     * TODO: toString that returns the name of the item
     */
    public String toString()
    {
         return this.name;
    }
}

/*
 * TODO: StorageCondition represents a series of behaviours for how that item should be stored. It consits of a
 * storageProcedure() method that return a String message for how an item should be stored.
 */

interface StorageCondition
{
    String storageProcedure();
}

/*
 * TODO: Refrigerate is a kind of StorageCondition where items are refrigerated based on whether the current is
 * enough for the item.
 */

interface Refrigerate extends StorageCondition
{
    /*
     * Take the current temperature and returns whether the item should be refrigerated or not.
     */
    boolean refrigerate(double currentTemp);
}

/*
 * SecureItem is a kind of storage condition where items are locked with security tags which must be removed
 *before checkout.
 */

interface SecureItem extends StorageCondition
{
    /*
     * Attaches the security tag
     */
    void attachSecurityTag();

    /*
     * Removes the security tag.
     * TODO: Returns a boolean success value.
     */
    boolean removeSecurityTag();
}

/*
 * ReducedPrice represents an item price reduction.
 */

interface ReducedPrice
{
    /*
     * reduction takes the total amount which could be for one or more items and returns the reduced amount.
     */
    double reduction(double totalAmount);
}

/*
 * TODO: OnSale is a specific kind of price reduction based on seasonal or special sales run by the store.
 */

interface OnSale extends ReducedPrice
{
    /*
     * TODO: saleCondition takes an array of items and returns a boolean success value based whether the items
     * qualify for a sale.
     */
    boolean saleCondition(Item[] items);
}

/*
 * TODO: Milk is an Item that must be Refrigerated.
 */

class Milk extends Item implements Refrigerate
{
    int maxRefrigerateTemp; // the maximum safe temperature for cooling this milk

    /*
     * TODO: The inherited constructor which takes only a name.
     * Is 'disabled' by making it a private/hidden
     */
    private Milk(String name)
    {
        super(name);
    }

    Milk(String name, int expiryDays, double price, int maxRefrigerateTemp)
    {
        this(name);
        this.expiresInDays = expiryDays;
        this.price = price;
        this.maxRefrigerateTemp = maxRefrigerateTemp;
    }

    /*
     * TODO: Refrigerate implementation based on current temperature. Returns boolean condition if item should be
     * refrigerated based on current temperature being greater than refrigeration temperature.
     */
    public boolean refrigerate(double currentTemp)
    {
        if(currentTemp > maxRefrigerateTemp)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
     * StorageCondition implementation specifying milk should be stored in a "cool area".
     */
    public String storageProcedure()
    {
        return "Cool area";
    }
}

/*
 * TODO: Bread is a kind of Item that requires a StorageCondition.
 */
class Bread extends Item implements StorageCondition
{
    /*
     * TODO: The inherited constructor which takes only a name. Is 'disabled' by making it private/hidden.
     */
    private Bread(String name)
    {
        super(name);
    }

    /*
     * TODO: Constructor that takes and sets name, expiry days, and price.
     */
    Bread(String name, int expiryDays, double price)
    {
        this(name);
        this.expiresInDays = expiryDays;
        this.price = price;
    }

    /*
     * StorageCondition implementation that specifies Bread must be stored in an "Airtight Storage".
     */
    public String storageProcedure()
    {
        return "Airtight Storage";
    }
}

/*
 * TODO: Pefume is an Item that requires SecureItem storage.
 */
class Perfume extends Item implements SecureItem
{
    private boolean locked; // representing whether the item is securely locked
                            // cannot be checked out until lock is removed
    /*
     * TODO: The inherited constructor which takes only a name.
     * Is 'disabled' by makng it private/hidden.
     */
    private Perfume(String name)
    {
        super(name);
    }

    /*
     * TODO: Constructor that takes and sets name, expiry days, price.
     * Sets locked to True.
     */
    Perfume(String name, int expiryDays, double price)
    {
        this(name);
        this.expiresInDays = expiryDays;
        this.price = price;
        this.attachSecurityTag();
    }

    /*
     * TODO: Attaching a security tag, Sets item to locked.
     */
    public void attachSecurityTag()
    {
        this.locked = true;
    }

    /*
     * TODO: Removing a secuirty tag.
     * If item is not locked, process fails. Otherwise succeeds.
     * Returns success value based on whether lock is successfully removed.
     */
    public boolean removeSecurityTag()
    {
        if(this.locked != true)
        {
            return false;
        }
        else
        {
            return true;
        }
        return this.locked;
    }

    /*
     * TODO: getter for checking whether item is locked.
     */
    public boolean getLocked()
    {
        return this.locked;
    }

    /*
     * Implementation of StorageCondition that specifies this item. Must be stored "Away from Sunlight"
     */
    public String stroageProcedure()
    {
        return "Away from Sunlight";
    }
}

/*
 * PlasticCup is a kind of Item
 */
class PlasticCup extends Item
{
    /*
     * TODO: The inherited constructor which takes only a name.
     * Is 'disabled' by making it private/hidden.
     */
    private PlasticCup(String name)
    {
        super(name);
    }

    /*
     * TODO: Constructor that takes and sets name, expiry days, price
     */
    PlasticCup(String name, int expiryDays, double price)
    {
        this(name);
        this.expiresInDays = expiryDays;
        this.price = price;
    }
}

/*
 * TODO: EasterSale is a kind of On Sale event
 */
class EasterSale implements OnSale
{
    final String message; // sale message
    final double minimumAmount; // minimum amount for the sale to qualify
    final double salePercent; // sale percentage

    /*
     * Constructor that takes and sets a sale percentage and minimum amount for sale to qualify.
     * The percent is in an 'easy form' i.e. as a percentage like 7.5 or 25.
     * This needs to be converted to 'normal form' which is a multiplier e.g. 0.075 for 7.5% and 0.25 for 25%.
     * The constructor also sets the message based on minimum amount and the sale percent as "Spend over M, Get P off for Easter!"
     * where M = minimum amount, and P = sale percent.
     */
    EasterSale(double salePercent, double minimumAmount)
    {
        this.salePercent = salePercent / 100;
        this.minimumAmount = minimumAmount;
        DecimalFormat format = new DecimalFormat("0.#");
        this.message = "Spend over " + format.format(minimumAmount) + ", Get" + (this.salePercent*100) + "% off for Easter!";
    }

    /*
     * Implementation of OnSale reduction
     * Takes the total amount and returns the reduced amount
     * based on applying sale percent discount
     */
    public double reduction(double totalAmount)
    {
        return totalAmount - (totalAmount * salePercent);
    }

    /*
     * TODO: SaleCondition that take in an array of items and checks whether the total is greater than minimumAmount, and returns
     * a success value reflecting this.
     */
    public boolean saleCondtion(Item[] items)
    {
        double totalAmount = 0;

        for(Item item : items)
        {
            totalAmount += item.price;
        }
        if(totalAmount > this.minimumAmount)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
     * TODO: toString that returns the sale message
     */
    public String toString()
    {
        return this.message;
    }
}

/*
 * ShopInventory representing the main class.
 */
public class ShopInventory
{
    /*
     * TODO: billItems method that takes an array of items and an OnSale instance.
     * It calculates the total amount, checks if sale condition is applicable, and if so then applies the discount.
     * It prints an output for the bill in the following form:
     * <Item> (<Price>)
     * If storage condition is applicable:
     * <Item> (<price>) (Storage: <condition>)
     * If no sale condition is applicable, it just returns the total.
     * Actual Total: <total without sale reduction>
     * <sale message>
     * and then returns the total.
     */
    public static double billItems(Item[] items, OnSale Sale)
    {
        double totalAmount = 0;
        for(Item item : items)
        {        
            System.out.print(item);
            totalAmount += item.price;
            System.out.print(" (" + item.price + ")");
            if (item instanceof StorageCondition)
            {
                System.out.print(" (Storage: ");
                if(item instanceof Refrigerate && ((Refrigerate) item).refrigerate(getCurrentTemperature()))
                {
                    System.out.print(((Refrigerate) item).storageProcedure());
                }
                else if(item instanceof SecureItem)
                {
                    ((SecureItem) item).removeSecurityTag();
                    System.out.print(((StorageCondition) item).storageProcedure());
                }
                else if(item instanceof StorageCondition)
                {
                    System.out.print(((StorageCondition) item).storageProcedure());
                }
                System.out.print(")");
            }
            System.out.println();
        }

        if(Sale == null || Sale.saleCondition(items) == false)
        {
            return totalAmount;
        }

        System.out.println("Actual Total: " + totalAmount);
        System.out.println(Sale);
        totalAmount = Sale.reduction(totalAmount);
        return totalAmount;
    }

    /* getCurrentTemperature returns the current temperature
     * Returns 24 to ensure StorageConditions on cooling are triggered
     */
    private static int getCurrentTemperature()
    {
        return 24;
    }

    /*
     * main() for testing the code
     * #Update Feb-11
     * If your code is correct, it should produce the below ouputs
     * i.e. for Customer 1 and Customer 2 as shown in comments
     */
    public static void main(String[] args)
    {
        DecimalFormat df = new DecimalFormat("#.##"); // double with 2 decimal precision

        // Case1: No Sale
        System.out.println("--- Customer 1 ---");
        Item[] items_no_sale = {
            new Milk("Avenmore Fresh", 5, 1.90, 12),
            new Bread("Bretzel Tortano", 7, 4.50),
            new Perfume("Lynx Vanilla", 500, 7),
            new PlasticCup("Tea Mug", 1200, 12),
        };
        double total_no_sale = billItems(items_no_sale, null);
        System.out.println("Total Amount: " + df.format(total_no_sale));

        // --- Customer 1 ---
        // Avenmore Fresg (1.9) (Storage: refrigeration needed)
        // Bretzel Tortano (4.5) (Storage: Airtight Storage)
        // Lynx Vanilla (7.0) (Storage: Away from Sunlight)
        // Tea Mug (12.0)
        // Total Amount: 25.4

        // Case2: Easter Sale
        System.out.println("--- Customer 2 ---");
        Item[] items_easter_sale = {
            new Milk("Mulled Wine", 60, 22.20, 8),
            new Bread("Fruit Cakes", 20, 13.50),
            new Perfume("Pot-pourri", 500, 15),
            new PlasticCup("Party Cups (set of 12)", 1200, 2),
        };
        double total_easter_sale = billItems(items_easter_sale, new EasterSale(7.5, 50));
        System.out.println("Total Amount: " + df.format(total_easter_sale));


        // -- Customer 2 ---
        // Mulled Wine (22.2) (Storage: refrigeration needed))
        // Fruit Cakes (13.5) (Storage: Airtight Storage)
        // Pot-pourri (15.0) (Storage: Away from Sunlight)
        // Party Cups (set of 12) (2.0)
        // Actual Total: 52.7
        // Spend over 50, Get 7.5% off for Easter!
        // Total Amount: 48.75
    }
}
