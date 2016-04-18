package nmarlor.kickabout.pitch;

public class UpdatePitchFeatureForm {

	private Long featureId;
	private Long pitchId;
	private String feature;
	
	public Long getFeatureId() {
		return featureId;
	}
	public Long getPitchId() {
		return pitchId;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}
	public void setPitchId(Long pitchId) {
		this.pitchId = pitchId;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	
	
}
