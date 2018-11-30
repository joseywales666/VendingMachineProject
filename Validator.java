public class Validator
{
	public Validator()
	{
		super();
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
}