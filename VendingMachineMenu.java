import java.util.Scanner;
import java.io.IOException;
import java.io.Console;
/**   
A menu from the vending machine.
*/
public class VendingMachineMenu extends Menu
{    
	private static Coin[] coins;
	private OperatorMenu opMenu;
	/**
    Constructs a VendingMachineMenu object
	*/
	public VendingMachineMenu() throws IOException
	{
		super();
		opMenu = new OperatorMenu();
		coins = Reader.currencyReader("Money.txt");
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
					System.out.println(machine.addCoin((Coin) getChoice(coins)));
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
				Console con = System.console(); String pass = "";
				System.out.println("Enter Operator ID:"); String id = in.nextLine();
				System.out.println("Enter Password:"); char[] passArray = con.readPassword();
				for(char c : passArray)
					pass += c;
				
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
				System.out.println("\nReturning Unused Coins");
				System.out.println(machine.removeMoney(false));
				Writer.stockToFile("Stock.txt", machine.getStock());
				Writer.coinsToFile("Money.txt", machine.getCoins());
				more = false;
			}
		}
	}
}