import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.util.Date;

/**
 * Write a description of class TenantList here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TenantList extends ObjectList
{
    
    public void serialize(List<Tenant> stI) throws IOException
    {   
        // Create a FileOutputStream to write data to a file named "TenantList.ser"
        FileOutputStream fileOut = new FileOutputStream("TenantList.ser"); 
        // Create an ObjectOutputStream to serialize and write objects to the file
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        
        // Write the provided List of Tenant objects (stI) to the ObjectOutputStream
        out.writeObject(stI);
        
        //Close the fileOutputStream and ObjectOutputStream
        out.close();
        fileOut.close();
        
        System.out.println("Save Success");        
    }
     
    public static List<Tenant> deserialize() throws IOException, ClassNotFoundException
    {
        List<Tenant> tenantSave = null;
          
        // Create a FileInputStream to read data from the file "TenantList.ser"
        FileInputStream fileIn = new FileInputStream("TenantList.ser");
        // Create an ObjectInputStream to deserialize and read objects from the file input stream
        ObjectInputStream in = new ObjectInputStream(fileIn);
        
        // Read the serialized data
        tenantSave = (List<Tenant>) in.readObject();
        
        //Close the fileInputStream and ObjectInputStream
        in.close();
        fileIn.close();
        
        return tenantSave;
    }
    
    public TenantList(int size)
    {  
        //Uses the size from object list using super
        super(size);
    }
    
    public Tenant getTenant(int index) 
    {
        //Retrive the object at a certain index
        Tenant tenant = (Tenant) getItem(index);
        if (tenant != null)//If a tenant is occupying the index it return the toString method from tenant
        {
            tenant.toString();
        }
        return tenant;
    }
    
    public Tenant search(int roomNumber)
    { 
        // iterates through the list
        for(int i = 0; i < getTotal(); i++)
        {
            //Get tenant object at i
            Tenant tenant = (Tenant) getItem(i);
            
            //Checks if tenant is not null and if room and number matches
            if (tenant != null && tenant.getRoom() == roomNumber)
            {
                return tenant; //return matching tenant
            }
        }
        return null; 
     }
    
    public boolean removeTenant(int roomNumber)
    {
        // iterates through the list
        for (int i = 0; i < getTotal(); i++) 
        { 
            Tenant tenant = (Tenant) getItem(i);
            //Checks if tenant is not null and if room and number matches
            if (tenant != null && tenant.getRoom() == roomNumber) 
            {
                remove(i); // remove tenant at index i
                return true; //indicate removal is successful
            }
        }
        return false;
    }
}
