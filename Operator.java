public class Operator
{
	private String id;
	private String pWord;
	
	public Operator(String idx, String pass)
	{
		id = idx;
		pWord = pass;
	}
	
	public boolean assertDetails(String idx, String pass)
	{
		return(id.compareTo(idx) == 0 && pWord.compareTo(pass) == 0);
	}
}