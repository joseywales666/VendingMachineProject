import java.util.Scanner;
import java.io.IOException;

public abstract class Menu
{
	protected Scanner in;
	
	public Menu()
	{
		in = new Scanner(System.in);
	}
	
	public abstract void run(VendingMachine machine) throws IOException; 
	
	protected Object getChoice(Object[] choices) throws NullPointerException
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