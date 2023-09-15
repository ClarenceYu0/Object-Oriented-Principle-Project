import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class Tenant here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Tenant implements Serializable
{
    String name;
    int room;
    private PaymentList[] payments;
    private final int maxNoOfPayments = 12;
    PaymentList paymentList = new PaymentList(maxNoOfPayments);
    
    // ArrayList for serialization
    List<Payment> paymentStringList = new ArrayList<>();
    public Tenant(String name, int room)
    {
        this.name = name;
        this.room = room;
        this.payments = new PaymentList[0]; // Initialize the payments array starting at the size 0 so tenant can be added later
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getRoom()
    {
        return room;
    }
    
    public void makePayment(Payment payment, int room)
    {
        //add paymment to paymentList to the arrayList
        paymentStringList.add(payment);
        try
        {
            // serialise the paymentList array list
            paymentList.serialize(paymentStringList, room);
        }
        catch (java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
        System.out.println("Payment made successfully");
    }
    
    public PaymentList getPayment()
    {
        return paymentList;//return paymentlist to allow access to paymentlist class  
    }
    
    public String toString()
    {
        return "Name : " + name + "\nRoom Number : " + room;
    }
    
    public void print()
    {
        System.out.println("Name : " + name + "\nRoom Number: " + room);
    }
}
