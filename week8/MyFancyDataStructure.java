import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*interface Comparable<Value>
{
    int compareTo(Value other);
}

interface Comparator<Value>
{
    int compare(Value other1, Value other2);
}*/

class Value implements Comparable<Value> 
{
    static private boolean SORT_LOWER;
    final int value;
    
    public Value(int value)
    {
        this.value = value;
    }

    static public boolean isSortLower()
    {
        return true;
    }
    static public boolean isSortHigher()
    {
        return false;
    }
    static public void setSortLower(boolean SORT_LOWER)
    {
        Value.SORT_LOWER = SORT_LOWER;
    }
    static public void setSortHigher(boolean SORT_LOWER)
    {
        Value.SORT_LOWER = SORT_LOWER;
    }

    public int compareTo(Value other)
    {
        Value object = (Value) other;
        if(this.value == other.value)
        {
            return 0;
        }
        else if(this.value > other.value)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public String toString()
    {
        return "" + this.value;
    }
}

class PreferLowerValues implements Comparator<Value>
{
    public int compare(Value other1, Value other2)
    {
        return Integer.compare(other1.value, other2.value);
    }
}

class PreferHigherValues implements Comparator<Value>
{
    public int compare(Value other1, Value other2)
    {
        return Integer.compare(other1.value, other2.value); 
    }

}

public class MyFancyDataStructure
{
    public static void main(String[] args)
    {
        List<Value> list = Arrays.asList(new Value(2), new Value(3), new Value(1));
        System.out.println(list);
        Value.isSortHigher();
        //Collections.sort(list);
        System.out.println(list);

        List<Value> list2 = Arrays.asList(new Value(2), new Value(3), new Value(1));
        //list2.sort(new PreferLowerValues());
        System.out.println(list2);
    }
}
