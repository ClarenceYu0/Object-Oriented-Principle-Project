import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.List;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;

/**
 * Write a description of class BigSleepHostel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BigSleepHostel
{
    public static void main(String[] args) throws IOException, ClassNotFoundException 
    {
        Scanner input = new Scanner(System.in);
        int response;
        final int numberOfRooms = 10;
        final int months = 12;
        TenantList tenantList = new TenantList(numberOfRooms);  
        int[][] month = new int[numberOfRooms][months];
        Tenant tenantInfo;
        
        // Set up arraylist for serialization
        List<Tenant> tenantStringList = new ArrayList<>();
        List<Payment> paymentStringList = new ArrayList<>();

        // Initialize month values for each tenant to 0 
        for (int i = 0; i < numberOfRooms; i++)    
        {
            for (int j = 0; j < months; j++) 
            {
                month[i][j] = 0;
            }
        }
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        
        do
        {
            System.out.println("[1] Add a Tenant");
            System.out.println("[2] List all Tenant");
            System.out.println("[3] Add a Payment");
            System.out.println("[4] List all Payment");
            System.out.println("[5] Remove a Tenant");
            System.out.println("[6] Get total Payments made per month for the entire hostel displaying \n the room and amount, including the total payment at the bottom");
            System.out.println("[7] Quit");
            
            while (true) 
            {
                System.out.print("Enter option [1-7]: ");
                //validate the input is a int
                if (input.hasNextInt()) 
                {
                    response = input.nextInt();
                    input.nextLine(); // Clear the input buffer
                    if (response <= 0 && response > 7) //if option is not between 1-7
                    {
                        System.out.print("Enter option between [1-7]: ");
                        response = input.nextInt();
                        input.nextLine(); // Clear the input buffer
                    }
                    break;
                } else 
                {
                    System.out.println("Invalid input. Please enter an integer.");
                    input.nextLine(); // Clear the input buffer
                }
            }
            
            switch(response)
            {
                case 1:
                    //Add Tenant
                    // Ask for tenant's info
                    System.out.println("Enter the tenant's name: ");
                    String name = input.nextLine();
                    
                    //Validate the input is a name
                    while (name.isEmpty() || name.matches(".*\\d.*")) 
                    {
                        System.out.println("Error: Invalid input. Please enter the tenant's name: ");
                        name = input.nextLine();
                    }
                    
                    int room = 0;
                    boolean validInput = false;
                    //Validate the input is a room number
                    System.out.println("Enter the tenant's room number: ");
                    while (!validInput)
                    {
                        try 
                        {
                            room = input.nextInt();
                            validInput = true;
                        } catch (InputMismatchException e) 
                        {
                            System.out.println("Error: Invalid input. Please enter the tenant's room number:");
                            input.nextLine(); // Clear the input buffer
                        }
                    }
                    
                    //Validate the room number isnt already in the system
                    boolean isRoomNumberUnique = true;
                    for (int i = 0; i < numberOfRooms; i++) 
                    {
                        Tenant existingTenant = tenantList.search(i);
                        if (existingTenant != null && room == existingTenant.getRoom()) 
                        {
                            isRoomNumberUnique = false;
                            break;
                        }
                    }
                    
                    if (isRoomNumberUnique) //checks if number is unique and act accordingly
                    {
                        Tenant newTenant = new Tenant(name, room);
                        tenantList.add(newTenant);
                        // Add the tenant to the tenant arrayList
                        tenantStringList.add(newTenant);
                        try
                        {
                            // serialize the tenant array list
                            tenantList.serialize(tenantStringList);
                        }
                        catch (IOException ioe)
                        {
                            ioe.printStackTrace();
                        }
                        System.out.println("Tenant added successfully.");
                    }
                    else
                    {
                        System.out.println("Room number is already occupied.");
                    }
                    break;
                    
                case 2:
                    //List all tenants
                    System.out.println("Tenant List:");    
                    for (int i = 0; i < numberOfRooms; i++) //through the number of rooms
                    {
                        Object tenant = tenantList.getTenant(i);
                        if (tenant != null)//print tenant if the index i is occupied
                        {
                            System.out.println("Tenant " + (i+1));
                            
                            try
                            {
                                //deserialize the tenantList
                                List<Tenant> deserializedTenant = tenantList.deserialize();
                                Tenant specificTenant = deserializedTenant.get(i);
                                System.out.println(specificTenant);
                            } catch (IOException | ClassNotFoundException e) 
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                    
                case 3:
                    //Add a payment
                    System.out.println("Enter the room number being paid for");
                    room = 0;
                    validInput = false;
                    //Validate the input is a room number
                    while (!validInput) 
                    {
                        try 
                        {
                            room = input.nextInt();
                            validInput = true;
                        } catch (InputMismatchException e) 
                        {
                            System.out.println("Error: Invalid input. Please enter a valid tenant's room number:");
                            input.nextLine(); // Clear the input buffer
                        }
                    }
                    
                    Tenant tenant = tenantList.search(room);
                    if (tenant != null) 
                    {
                        int[] tenantMonth = month[room - 1];//initialise the month starting at january for each tenant
                        for (int i = 0; i < months; i++)
                        {
                            if (tenantMonth[i] < months)//if the month integer is less then the 12 months
                            {
                                System.out.println("Add payment for " + monthNames[tenantMonth[i]]);
                                
                                double amount = 0.0;
                                boolean isValidInput = false;
                                //vaidate the user has enter a double
                                while (!isValidInput) 
                                {
                                    if (input.hasNextDouble()) 
                                    {
                                        amount = input.nextDouble();
                                        isValidInput = true;
                                    } else 
                                    {
                                        System.out.println("Invalid input. Please enter a payment value ");
                                        input.nextLine(); // Clear the input buffer
                                    }
                                }
                                input.nextLine();
            
                                String monthDisplay = monthNames[tenantMonth[i]];
                                
                                Payment payment = new Payment(monthDisplay, amount);
                                
                                tenant.makePayment(payment, room);//add payment 
                                
                                tenantMonth[i]++;//increment to the next month
                                break;
                            }
                            else
                            {
                                System.out.println("Maximum payment has been made");//tells the user the paymentList is full
                                break;
                            }
                        }
                    }
                    else 
                    {
                        System.out.println("Unable to make payment. Invalid room number.");
                        break;
                    }
                    
                    break;
                    
                case 4:
                    //List all Payment for each tenant
                    System.out.println("Enter the tenant's room number: ");
                    room = 0;
                    validInput = false;
                    //Validate the input is a room number
                    while (!validInput) 
                    {
                        try 
                        {
                            room = input.nextInt();
                            validInput = true;
                        } catch (InputMismatchException e) 
                        {
                            System.out.println("Invalid input. Enter the tenant's room number:");
                            input.nextLine(); // Clear the input buffer
                        }
                    }
                    
                    System.out.println("Payment List:");
                    
                    Tenant existingTenant = tenantList.search(room);//search for room number
                    if (existingTenant != null) //print payment if tenant is occupied
                    {
                        for (int i = 0; i < months; i++) 
                        {                   
                            System.out.println("Payment " + (i+1));
                            Payment payment = existingTenant.getPayment().getPayment(i);
                            
                            try {
                                // Create List that get the payment of the room the user pick
                                List<Payment> deserializePaymentList = existingTenant.getPayment().deserialize(room);
                                if (i >= 0 && i < deserializePaymentList.size()) // makes sure the the Paymentlist is between 0 and 12
                                {
                                    // deserialize the the payment list of the room
                                    Payment incrementedPayment = deserializePaymentList.get(i);
                                } 
                                else 
                                {
                                    System.out.println("Null");
                                }
                            } catch (IOException | ClassNotFoundException e) 
                            {
                                e.printStackTrace();
                            }
                                                        
                            if (payment == null)//print unpaid if payment is null
                            {
                                System.out.println("Unpaid");
                            }
                        }
                    }
                    else
                    {
                        System.out.println("Tenant not found.");
                    }  
                    break;
                    
                case 5:
                    //Remove Tenant
                    System.out.println("Enter the tenant's room number: ");
                    room = 0;
                    validInput = false;
                    //Validate the input is a room number
                    while (!validInput) 
                    {
                        try 
                        {
                            room = input.nextInt();
                            input.nextLine();
                            validInput = true; // The input is a valid integer
                            
                            // Create file path 
                            String filePath = "RoomPayment" + room + ".ser";
                            File file = new File(filePath);
                            if (file.exists()) // if the file path exist
                            {
                                if (file.delete()) // delete file
                                {
                                    System.out.println("Tenant removed successfully.");
                                } 
                                else 
                                {
                                    System.out.println("Failed to delete the file.");
                                }
                            } 
                            else 
                            {
                                System.out.println("File does not exist.");
                            }
                            if (room >= 1 && room <= numberOfRooms) 
                            {
                                existingTenant = tenantList.search(room);//search the room number
                                if (existingTenant != null)//if tenant exist is null
                                {
                                    tenantList.removeTenant(room);//remove the tenant
                                    
                                    Object payment = existingTenant.getPayment();
                                    
                                    tenantStringList.remove(existingTenant);
                                    tenantList.serialize(tenantStringList);
                                }
                            }
                        } catch (InputMismatchException e) 
                        {
                            System.out.println("Invalid input. Please enter the tenant's room number.");
                            input.nextLine(); // Clear the input buffer
                        }
                    }
                    break;
                    
                case 6:
                    //Display Total amount payed for every room
                    System.out.println("Total Payments");
                    int roomNumber = 1;
                    for (int i = 0; i < numberOfRooms; i++) 
                    {
                        existingTenant = tenantList.search(i+1);
                        System.out.println("Room " + roomNumber);
                        roomNumber++;
                        //Checks if tenant and payment exist
                        if (existingTenant != null && existingTenant.getPayment() != null) 
                        {
                            Object totalPayment = existingTenant.getPayment().calculateTotalPaid();//get the total paid for the tenant chosen
                            System.out.printf("€%.2f\n", totalPayment);// print the payment in decimal place 
                        }
                        else
                        {
                            System.out.println("No payment information available.");
                        }
                    }
                    break;
                    
                case 7:
                    try
                    {
                        tenantList.serialize(tenantStringList);
                    }
                    catch (IOException ioe)
                    {
                        ioe.printStackTrace();
                    }
                    //Quits Program
                    break;
                    
                default:System.out.println("Options 1-7 only");//display if user pick options unavailable
            }
        }while (response != 7);
    }
}

