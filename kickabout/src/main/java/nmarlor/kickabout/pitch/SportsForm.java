package nmarlor.kickabout.pitch;

import java.util.ArrayList;

public class SportsForm 
{
	private Long pitchId;
	private ArrayList<Sport> pitchSports;
	
	public Long getPitchId() {
		return pitchId;
	}
	
	public void setPitchId(Long pitchId) {
		this.pitchId = pitchId;
	}
	
	public ArrayList<Sport> getPitchSports() {
		return pitchSports;
	}

	public void setPitchSports(ArrayList<Sport> pitchSports) {
		this.pitchSports = pitchSports;
	}
	
}
