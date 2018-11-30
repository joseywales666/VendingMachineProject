import java.util.*;
import java.io.*;

public class Writer
{ 
	public Writer()
	{
		super();
	}
	public static void stockToFile(String fileName, ArrayList<LineItem> list) throws IOException 
	{
		FileWriter writer = new FileWriter(fileName);
		for (LineItem str : list) 
		{
			writer.write(str.toCSV() + "\n");
		}
		writer.close();
	}	
	
	public static void coinsToFile(String fileName, ArrayList<CoinLineItem> list) throws IOException 
	{
		FileWriter writer = new FileWriter(fileName);
		for (CoinLineItem str : list) 
		{
			writer.write(str.toCSV() + "\n");
		}
		writer.close();
	}	
}