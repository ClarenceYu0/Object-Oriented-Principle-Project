import java.io.Serializable;

/**
 * Write a description of class ObjectList here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ObjectList implements Serializable
{
    private Object[] object; // Array to store the objects
    private int total; // Total object in the list currently
    private int size; // Maximum size of the list
    
    ObjectList(int size)
    {
        this.size = size;
        object = new Object[size];
        total = 0;
    }
    
    public boolean add(Object obj)
    {
        if(!isFull())
        {
            object[total] = obj; //Add the object to the next available position in the array
            total++; //Increment the total count 
            return true;
        }
        else
        {
            System.out.println("ObjectList is full. Cannot add more objects.");
            return false;
        }
    }
    
    public boolean remove(int i)
    {
        if(!isEmpty() && i >= 0 && i < total) //Checks if the list is empty and checks if there is i in the list )
        {
            for (int j = i; j < total - 1; j++) 
            {
                object[j] = object[j + 1]; // Shift elements to the left
            }
            object[total - 1] = null; // Set the last element to null so it wont occupied the space
            total--;//Reduce the total currentlty in the list
            return true;
        }
        return false;
    }
    
    public boolean isEmpty()
    {
        // iterates through the list 
        for(int i = 0; i < object.length; i++) 
        {
            //if space in object list isnt empty return false
            if(object[i] != null)
            {
                return false;
            }
        }
        return true;
    }
    public boolean isFull()
    {
        //return true if the total count of obect is equal to size
        return total == size;
    }
    
    public Object getItem(int index)
    {
        //return the index of the object
        return object[index];
    }
    public int getTotal()
    {
        //return the total size of the object list
        return total;
    }
}

