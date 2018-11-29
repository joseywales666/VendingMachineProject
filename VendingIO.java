import java.lang.Object;
import java.util.Scanner;
import java.io.*;

		
public class VendingIO
{
	public Coin[] currencyReader(String fileName) throws IOException, FileNotFoundException
	{
		File f = new File(fileName); Scanner in = new Scanner(f);
		Scanner counter = new Scanner(f); int lines = 0;
		while(counter.hasNextLine())
			lines++;
		Coin[] coins = new Coin[lines]; int i = 0;
		String[] fileLine = new String[3];
		while(in.hasNextLine())
		{
			fileLine = in.nextLine().split(",");
			coins[i] = new Coin(Double.parseDouble(fileLine[1]), fileLine[0]);
			i++;
		}
		counter.close(); in.close();
		return coins;
	}
	
		public ArrayList<LineItem> stockReader(String fileName)
	{
		File f = new File(fileName); Scanner in = new Scanner(f).useDelimiter(",");
	}
	
	public ArrayList<CoinLineItem> coinReader(String fileName)
	{
		File f = new File(fileName); Scanner in = new Scanner(f).useDelimiter(",");
	}
	
	public ArrayList<Operator> operatorReader(String fileName)
	{
		File f = new File(fileName); Scanner in = new Scanner(f).useDelimiter(",");
	}

}