/*
interface Order
{
    boolean lessThan(Order other);
}

interface Comparable extends Order
{
    int compareTo(Comparable other);
}
*/
public class Point implements Comparable
{
    double x, y;

    public Point(double newX, double newY)
    {
        x = newX;
        y = newY;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    private boolean checkType(Object object)
    {
        return object instanceof Point;
    }

    public boolean equals(Point other)
    {
        if(x == other.getX() && y == other.getY())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean lessThan(Order other)
    {
        return lessThan((Point) other);
    }

    public boolean lessThan(Point other)
    {
        if(equals(other))
        {
            return false;
        }
        else if(x < other.getX() || y < other.getY())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int compareTo(Comparable other)
    {
        return compareTo((Object) other);
    }

    public int compareTo(Object other)
    {
        if(!checkType(other))
        {
            return 0;
        }

        Point object = (Point) other;
        if(lessThan(object))
        {
            return -1;
        }
        if(object.lessThan(this))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    public static void main(String[] args)
    {
        Order O1 = new Point(0, 0);
        Order O2 = new Point(1, 1);
        Order O3 = new Point(0, 1);

        System.out.println("O1 less than O2: " + O1.lessThan(O2)); // true
        System.out.println("O1 less than O3: " + O1.lessThan(O3)); // true
        System.out.println("O2 less than O3: " + O2.lessThan(O3)); // false
        System.out.println("O3 less than O3: " + O3.lessThan(O3)); // false


        Comparable P1 = new Point(0, 0);
        Comparable P2 = new Point(1, 1);
        Comparable P3 = new Point(0, 1);

        System.out.println("P1 less than P2: " + P1.compareTo(P2)); // -1
        System.out.println("P1 less than P3: " + P1.compareTo(P3)); // -1
        System.out.println("P2 less than P3: " + P2.compareTo(P3)); // 1
        System.out.println("P3 less than P3: " + P3.compareTo(P3)); // 0
    }
}
