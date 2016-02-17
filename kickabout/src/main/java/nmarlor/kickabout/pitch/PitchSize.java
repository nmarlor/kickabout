package nmarlor.kickabout.pitch;

public enum PitchSize {

	SMALL("Small"),
	MEDIUM("Medium"),
	LARGE("Large");
	
	private String prettyName;
	
	private PitchSize(String prettyName)
	{
		this.prettyName = prettyName;
	}
	
	public String getPrettyName()
	{
		return prettyName;
	}
}
