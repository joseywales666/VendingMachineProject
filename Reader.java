import java.util.*;
import java.io.*;
public class Reader
{
	public Reader()
	{
		super();
	}
	
	public static Coin[] currencyReader(String fileName) throws IOException
	{
		File f = new File(fileName); 
		Coin[] coins;
		if(f.exists())
		{
			Scanner in = new Scanner(f);
			Scanner counter = new Scanner(f); int lines = 0;
			while(counter.hasNextLine())
			{
				lines++;
				counter.nextLine();
			}
			coins = new Coin[lines];
			int i = 0;
			String[] fileLine;
			while(in.hasNextLine())
			{
				fileLine = in.nextLine().split(",");
				coins[i] = new Coin(Double.parseDouble(fileLine[1]), fileLine[0]);
				i++;
			}
			counter.close(); in.close();
		}
		else
		{
			coins = new Coin[1];
		}

		return coins;
	}
	
	public static ArrayList<LineItem> stockReader(String fileName) throws IOException
	{
		ArrayList<LineItem> list = new ArrayList<LineItem>();
		File f = new File(fileName); 
		if(f.exists())
		{
			Scanner in = new Scanner(f);
			String[] fileLine;
			while(in.hasNextLine())
			{
				fileLine = in.nextLine().split(",");
				list.add(new LineItem(new Product(fileLine[0], Double.parseDouble(fileLine[1])), Integer.parseInt(fileLine[2])));
			}
			in.close();
		}
		
		return list;
	}
	
	public static ArrayList<CoinLineItem> coinReader(String fileName) throws IOException
	{
		ArrayList<CoinLineItem> list = new ArrayList<CoinLineItem>();
		File f = new File(fileName); 
		if(f.exists())
		{
			Scanner in = new Scanner(f);
			String[] fileLine;
			while(in.hasNextLine())
			{
				fileLine = in.nextLine().split(",");
				list.add(new CoinLineItem(new Coin(Double.parseDouble(fileLine[1]), fileLine[0]), Integer.parseInt(fileLine[2])));
			}
			in.close();
		}

			return list;
	}
	
	public static ArrayList<Operator> operatorReader(String fileName) throws IOException
	{
		ArrayList<Operator> list = new ArrayList<Operator>();
		File f = new File(fileName); 
		if(f.exists())
		{
			Scanner in = new Scanner(f);
			String[] fileLine;
			while(in.hasNextLine())
			{
				fileLine = in.nextLine().split(",");
				list.add(new Operator(fileLine[0], fileLine[1]));
			}
			in.close();
		}

		return list;
	}
}