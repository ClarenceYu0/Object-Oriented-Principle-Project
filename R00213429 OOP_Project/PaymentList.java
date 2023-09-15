import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Write a description of class PaymentList here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PaymentList extends ObjectList implements Serializable
{ 
    public void serialize(List<Payment> stI, int index) throws IOException
    {  
        // Create a FileOutputStream to write data to a file named "RoomPayment.ser" with the index as what room number the file is for
        FileOutputStream fileOut = new FileOutputStream("RoomPayment" + index + ".ser");
        // Create an ObjectOutputStream to serialize and write objects to the file
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        
        // Write the provided List of Payment objects (stI) to the ObjectOutputStream
        out.writeObject(stI);
        
        //Close the fileOutputStream and ObjectOutputStream
        out.close();
        fileOut.close();
        
        System.out.println("Save Success");        
    }
    
    public static List<Payment> deserialize(int index) throws IOException, ClassNotFoundException
    {
        List<Payment> paymentSave = null;
          
        // Create a FileInputStream to read data from the file "Roomnumber.ser"
        FileInputStream fileIn = new FileInputStream("C:\\Users\\clare\\R00213429 OOP_Project_Repeat\\RoomPayment" + index + ".ser");
        // Create an ObjectInputStream to deserialize and read objects from the file input stream
        ObjectInputStream in = new ObjectInputStream(fileIn);
        
        // Read the serialized data
        paymentSave = (List<Payment>) in.readObject();
        
        //Close the fileInputStream and ObjectInputStream
        in.close();
        fileIn.close();
        
        return paymentSave;
    }
    
    public PaymentList(int size)
    { 
        //Uses the size from object list using super
        super(size);
    }
    
    public Payment getPayment(int index)
    {
        //Retrive the object at a certain index
        Payment payment = (Payment) getItem(index);
        if (payment != null)//If a payment is occupying the index it return the toString method from payment
        {
            payment.print();
        }
        return payment;
    }
    
    public double calculateTotalPaid()
    {
        //initialise a total payment value
        double totalPaid = 0.0;
        
        // iterates through the list
        for (int i = 0; i < getTotal(); i++)
        {
            //get the payment at the current position
            Payment payment = (Payment) getItem(i);
            if (payment != null)//if it not null add the amount value to the total paid 
            {
                totalPaid += payment.getAmount();
            }
            else //else stop the loop
            {
                break;
            }
        }
        //return the total paid
        return totalPaid;
    }
}
