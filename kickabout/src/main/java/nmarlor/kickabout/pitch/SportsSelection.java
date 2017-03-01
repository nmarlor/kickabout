package nmarlor.kickabout.pitch;

public enum SportsSelection {
	
	SMALL("Small"),
	MEDIUM("Medium"),
	LARGE("Large");
	
	private String prettyName;
	
	private SportsSelection(String prettyName)
	{
		this.prettyName = prettyName;
	}
	
	public String getPrettyName()
	{
		return prettyName;
	}

}
