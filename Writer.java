import java.util.*;
import java.io.*;
public class Writer extend FileManipulator
{
	Private FileWriter aFileWriter; 
	public Writer()
	{
		super();
	}
	public static void fileObjectWriter(String fileName, ArrayList<Object> writer) throws IOException 
	{
		 		
		aFileWriter = new FileWriter(fileName);
		for (Object str : writer) 
		{
			aFileWriter.write(str.toString());
		}
		aFileWriter.close();
	}	
}