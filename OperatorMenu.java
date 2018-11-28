import java.util.Scanner;
import java.io.IOException;

public class OperatorMenu
{
	private Scanner in;
	
	public OperatorMenu()
	{
		in = new Scanner(System.in);
	}
	
	public void run(VendingMachine machine) throws IOException
	{
		boolean more = true;
		while(more)	
		{	
			System.out.println("W)ithdraw Coins  R)estock  A)dd New Stock  E)xit");
			String option = in.nextLine().toUpperCase();
			if (option.equals("W"))
			{  
				System.out.println(machine.removeMoney(true));
			}
			else if (option.equals("R")) 
			{ 
				if(machine.getProductTypes(true).length != 0)
				{
					try
					{
						System.out.println("Choose Product To Add:");
						Product p = (Product) getChoice(machine.getProductTypes(true));
						System.out.println("Quantity:");
						String command = in.nextLine();
						if(verifyInt(command))
						{
							int q = Integer.parseInt(command);
							if(!(q > 0))
							{
								System.out.println("\nInvalid Quantity");
							}
							else
							{
								machine.addProduct(p, q);
								System.out.println(p + " : " + q + " Added");
							}
						}
						else
						{
							System.out.println("\nInvalid Quantity");
						}
					}
					catch(NullPointerException ex)
					{
						System.out.println("No Options Currently Available");
					}
				}
				else
				{
					System.out.println("No Options Currently Available");
				}
			}
			else if (option.equals("A"))
			{      
				System.out.println("Description:");
				String description = in.nextLine();
				System.out.println("Price:");
				String priceStr = in.nextLine();
				System.out.println("Quantity:");
				String quantityStr = in.nextLine();
				if(verifyDouble(priceStr) && verifyInt(quantityStr))
				{
					double price =  Double.parseDouble(priceStr); int quantity = Integer.parseInt(quantityStr);
					if(price > 0 && quantity > 0)
					{
						if(!(machine.containsProduct(price, description)))
						machine.addProduct(new Product(description, price), quantity);
						else
						System.out.println("Product Already In Vending Machine.\nPlease Select \"R)estock\" Option"); 
					}
					else
					{
						System.out.println("Invaldid Input");
					}
				}
				else
				{
					System.out.println("Invaldid Input");
				}
			}        
			else if (option.equals("E"))
			{      
				more = false;
			}        
		}
	}
	
	public static boolean verifyInt(String v)
	{
		String intPattern = "(-)?[0-9]+";
		return (v.matches(intPattern));
	}
	
	public static boolean verifyDouble(String d)
	{
		String dubPattern = "(-)?([0-9]*\\.)?[0-9]+";
		return (d.matches(dubPattern));
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
