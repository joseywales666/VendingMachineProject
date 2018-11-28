import java.util.Scanner;
import java.io.IOException;

/**   
A menu from the vending machine.
*/
public class VendingMachineMenu
{    
	private Scanner in;
	private static Coin[] coins;
	private OperatorMenu opMenu;
	/**
    Constructs a VendingMachineMenu object
	*/
	public VendingMachineMenu()
	{
		in = new Scanner(System.in);
		opMenu = new OperatorMenu();
		coins = new Coin[]{new Coin(0.50, "Fiddy"), new Coin(1.0, "Dolla")};
	}
   
	/**
	Runs the vending machine system.
	@param machine the vending machine
	*/
	public void run(VendingMachine machine) throws IOException
	{
		boolean more = true;
      
		while (more)
		{ 
			System.out.println("S)how Products  I)nsert Coin  B)uy  R)eturn Coins  O)perator Functions  Q)uit");
			String command = in.nextLine().toUpperCase();

			if (command.equals("S"))
			{  
				if(machine.getProductTypes(false).length == 0)
					System.out.println("No Options Currently Available");
				else
				{	
					/*
					getProductTypes() returns an array of products that doesn't contain duplicates
					*/
					for (Product p : machine.getProductTypes(false))
						System.out.println(p);
				}	
		
			}
			else if (command.equals("I")) //allows one coin be inserted at a time
			{ 
				try
				{	
					machine.addCoin((Coin) getChoice(coins));
				}
				catch(NullPointerException ex)
				{
					System.out.println("No Options Currently Available");
				}
			}
			else if (command.equals("B")) 
			{              
				if(machine.getProductTypes(false).length != 0)
				{
					try
					{
						Product p = (Product) getChoice(machine.getProductTypes(false));
						machine.buyProduct(p);
						System.out.println("Purchased: " + p);
					}
					catch(NullPointerException except)
					{
						System.out.println("No Options Currently Available");
					}
					catch (VendingException ex)
					{
						System.out.println(ex.getMessage());
					}
				}
				else
				{
					System.out.println("No Options Currently Available");
				}
			}
			else if (command.equals("R"))
			{
				System.out.println(machine.removeMoney(false));
			}
			else if (command.equals("O"))
			{  		
				System.out.println("Enter Operator ID:"); String id = in.nextLine();
				System.out.println("Enter Password:"); String pass = in.nextLine();
				try
				{
					if(machine.login(id, pass))
					{
						opMenu.run(machine);
					}
					else
					{
						System.out.println("LOGIN FAILED\nReturning to menu...");
					}
				}
				catch(NullPointerException ex)
				{
					System.out.println("LOGIN FAILED\nReturning to menu...");
				}
			}
			else if (command.equals("Q"))
			{ 
				more = false;
			}
		}
	}

	private Object getChoice(Object[] choices) throws NullPointerException
	{ 
		while (true)
		{
			char c = 'A';
			for (Object choice : choices)
			{
				System.out.println(c + ") " + choice); 
				c++;
			}
			try
			{
				String input = in.nextLine();
				int n = input.toUpperCase().charAt(0) - 'A';
				if (0 <= n && n < choices.length)
				return choices[n];
			}
			catch(StringIndexOutOfBoundsException ex)
			{
				System.out.println("Please Select An Option");
			}
		}   
	}
}

/*System.out.println("Description:");
            String description = in.nextLine();
            System.out.println("Price:");
            double price = in.nextDouble();
            System.out.println("Quantity:");
            int quantity = in.nextInt();
            in.nextLine(); // read the new-line character
            machine.addProduct(new Product(description, price), quantity);
*/