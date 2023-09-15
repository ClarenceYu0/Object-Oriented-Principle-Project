import java.io.Serializable;

/**
 * Write a description of class Payment here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Payment implements Serializable
{
    private String month;
    private double amount;
    
    public Payment(String month, double amount)
    {
        this.month = month;
        this.amount = amount;
    }
    
    public String getMonth()
    {
        return month;
    }
    
    public double getAmount()
    {
        return amount;
    }
    
    public String toString()
    {
        return "Month : " + month + "\nAmount : €" + amount;
    }
    
    public void print()
    {
        System.out.printf("Month : %s\nAmount : €%.2f\n", month, amount);
    }
}
