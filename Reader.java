import java.util.*;
import java.io.*;
public class Reader extend FileManipulator 
{
	Private File aFileReader = new File(file);
	Private Scanner in = new Scanner(aFileReader);
	Private ArrayList<Object> list = new ArrayList<Object>();
	Private	Object [] elements;
	Private	Object line = new Object();
	Private	int count =0;
	public Reader()
	{
		super();
	}
	public static ArrayList<Object> fileObjectReader(String file) throws IOException
	{
		
		if(aFileReader.exists())
		{		
			while(in.hasNext())
			{
				line = in.nextLine();
				elements[count]= line;	
				count++;		
			}
			for(int i = 0; i < elements.length;i++)
				{
					list.get(i).add(elements[i]);
				}
		}
		in.close();
		return list;
	}
}