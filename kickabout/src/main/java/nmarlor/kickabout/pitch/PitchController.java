package nmarlor.kickabout.pitch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


@Controller
public class PitchController {
	
	@Autowired
	private PitchLocationService pitchLocationService;
	
	@Autowired
	private PitchesService pitchService;
	
	@Autowired
	private NewPitchValidator pitchValidator;
	
	@Autowired
	private NewPitchFeatureValidator newPitchFeatureValidator;
	
	@Autowired
	private UpdateFeatureValidator updateFeatureValidator;
	
	@Autowired
	private PitchFeatureService pitchFeatureService;

	@RequestMapping(value = "addPitch", method = RequestMethod.GET)
	public ModelAndView addPitch(Long locationId){
		ModelAndView mv = new ModelAndView("pitches/newPitch");
		
		NewPitchForm pitchForm = new NewPitchForm();
		pitchForm.setLocationId(locationId);
		
		mv.addObject("pitchForm", pitchForm);
		return mv;
	}
	
	@RequestMapping(value = "addPitch", method = RequestMethod.POST)
	public ModelAndView submitPitch(@ModelAttribute("pitchForm") NewPitchForm pitchForm, BindingResult bindingResult){
		ModelAndView thisMv = new ModelAndView("pitches/newPitch");
		Long locationId = pitchForm.getLocationId();
		PitchLocation pitchLocation = pitchLocationService.retrieve(locationId);
		Integer pitchNumber = pitchForm.getPitchNumber();
		
		pitchValidator.validate(pitchForm, bindingResult);
		if (bindingResult.hasErrors()) 
		{
			thisMv.addObject("errors", bindingResult);
			return thisMv;
		}
		
		Pitch retrievedPitch = pitchService.findPitchByLocationAndNumber(pitchLocation, pitchNumber);
		if (retrievedPitch != null) {
			bindingResult.rejectValue("pitchNumber", "pitchNumberDuplicate.message");
			thisMv.addObject("errors", bindingResult);
			return thisMv;
		}
		
		Pitch pitch = new Pitch();
		pitch.setPitchLocation(pitchLocation);
		pitch.setAvailableFrom(pitchForm.getAvailableFrom());
		pitch.setAvailableTo(pitchForm.getAvailableTo());
		pitch.setCost(pitchForm.getCost());
		pitch.setPitchNumber(pitchNumber);
		pitch.setPitchSize(pitchForm.getPitchSize());
		
		pitchService.createPitch(pitch);
		
		List<Pitch> pitches = new ArrayList<>();
		pitches = pitchService.findPitchesByLocation(pitchLocation);
		
		ModelAndView successMv = new ModelAndView("pitches/successfulPitchCreation");
		successMv.addObject("locationId", locationId);
		successMv.addObject("pitches", pitches);
		successMv.addObject("pitchForm", pitchForm);
		
		return successMv;
	}
	
	@RequestMapping(value = "managePitch", method = RequestMethod.GET)
	public ModelAndView managePitch(Long pitchId){
		ModelAndView mv = new ModelAndView("pitches/managePitch");
		
		Pitch pitch = pitchService.retrievePitch(pitchId);
		List<PitchFeature> pitchFeatures = pitchFeatureService.findPitchFeaturesByPitch(pitch);
		
		PitchForm pitchForm = new PitchForm();
		pitchForm.setPitchId(pitchId);
		
		mv.addObject("pitch", pitch);
		mv.addObject("pitchId", pitchId);
		mv.addObject("pitchFeatures", pitchFeatures);
		mv.addObject("pitchForm", pitchForm);
		return mv;
	}
	
	@RequestMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE) 
	public ResponseEntity<byte[]> getImage(Long pitchId) throws IOException 
	{ 
		Pitch pitch = pitchService.retrievePitch(pitchId);
		byte[] imageContent =  pitch.getImage();
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.IMAGE_JPEG); 
		return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "editFeature", method = RequestMethod.GET)
	public ModelAndView editFeatureRequest(Long id){
		ModelAndView mv = new ModelAndView("pitches/editFeature");
		
		PitchFeature pitchFeature = pitchFeatureService.retrieve(id);
		Long pitchId = pitchFeature.getPitch().getId();
		String feature = pitchFeature.getFeature();
		
		UpdatePitchFeatureForm featureForm = new UpdatePitchFeatureForm();
		featureForm.setFeature(feature);
		featureForm.setFeatureId(id);
		featureForm.setPitchId(pitchId);
		
		mv.addObject("featureForm", featureForm);
		return mv;
	}
	
	@RequestMapping(value = "editFeature", method = RequestMethod.POST)
	public ModelAndView editFeature(@ModelAttribute("featureForm") UpdatePitchFeatureForm featureForm, BindingResult result, HttpServletRequest request){
		ModelAndView thisMv = new ModelAndView("pitches/editFeature");
		PitchFeature pitchFeature = pitchFeatureService.retrieve(featureForm.getFeatureId());
		
		updateFeatureValidator.validate(featureForm, result);
		if (result.hasErrors()) 
		{
			thisMv.addObject("errors", result);
			return thisMv;
		}
		
		pitchFeature.setFeature(featureForm.getFeature());
		
		pitchFeatureService.update(pitchFeature);
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setModelKey("redirect");
		return new ModelAndView (jsonView, "redirect", request.getContextPath() + "pitches/managePitch");
	}
	
	@RequestMapping(value = "deleteFeature", method = RequestMethod.GET)
	public ModelAndView deleteFeatureRequest(Long id){
		ModelAndView mv = new ModelAndView("pitches/deleteFeature");
		
		PitchFeature pitchFeature = pitchFeatureService.retrieve(id);
		Long pitchId = pitchFeature.getPitch().getId();
		String feature = pitchFeature.getFeature();
		
		UpdatePitchFeatureForm featureForm = new UpdatePitchFeatureForm();
		featureForm.setFeature(feature);
		featureForm.setFeatureId(id);
		featureForm.setPitchId(pitchId);
		
		mv.addObject("featureForm", featureForm);
		return mv;
	}
	
	@RequestMapping(value = "deleteFeature", method = RequestMethod.POST)
	public ModelAndView deleteFeature(@ModelAttribute("featureForm") UpdatePitchFeatureForm featureForm, BindingResult result, HttpServletRequest request){
		PitchFeature pitchFeature = pitchFeatureService.retrieve(featureForm.getFeatureId());
		
		pitchFeatureService.delete(pitchFeature);
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setModelKey("redirect");
		return new ModelAndView (jsonView, "redirect", request.getContextPath() + "pitches/managePitch");
	}
	
	@RequestMapping(value = "addFeature", method = RequestMethod.GET)
	public ModelAndView addFeatureRequest(Long id){
		ModelAndView mv = new ModelAndView("pitches/addFeature");
		
		NewPitchFeatureForm featureForm = new NewPitchFeatureForm();
		featureForm.setPitchId(id);
		
		mv.addObject("featureForm", featureForm);
		return mv;
	}
	
	@RequestMapping(value = "addFeature", method = RequestMethod.POST)
	public ModelAndView addFeature(@ModelAttribute("featureForm") NewPitchFeatureForm featureForm, BindingResult result, HttpServletRequest request){
		ModelAndView thisMv = new ModelAndView("pitches/addFeature");

		PitchFeature pitchFeature = new PitchFeature();
		Pitch pitch = pitchService.retrievePitch(featureForm.getPitchId());
		String feature = featureForm.getFeature();
		
		newPitchFeatureValidator.validate(featureForm, result);
		if (result.hasErrors()) 
		{
			thisMv.addObject("errors", result);
			return thisMv;
		}
		
		PitchFeature retrievedPitchFeature = pitchFeatureService.findPitchFeatureByPitchAndFeature(pitch, feature);
		if (retrievedPitchFeature != null) {
			result.rejectValue("feature", "featureDuplicate.message");
			thisMv.addObject("errors", result);
			return thisMv;
		}
		
		pitchFeature.setFeature(feature);
		pitchFeature.setPitch(pitch);
		pitchFeatureService.create(pitchFeature);
		
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setModelKey("redirect");
		return new ModelAndView (jsonView, "redirect", request.getContextPath() + "pitches/managePitch");
	}
	
	@RequestMapping(value = "uploadImage", method = RequestMethod.POST)
	public ModelAndView uploadImage(@ModelAttribute("pitchForm") PitchForm pitchForm, Principal principal, BindingResult result, @RequestParam("file") MultipartFile uploadedFile) throws ClassNotFoundException, SQLException, IOException
	{
		ModelAndView mv = new ModelAndView("pitches/managePitch");
		
		Long pitchId = pitchForm.getPitchId();
		Pitch pitch = pitchService.retrievePitch(pitchId);
		
		// create a java mysql database connection
	    String myDriver = "com.mysql.jdbc.Driver";
	    String myUrl = "jdbc:mysql://localhost:3306/kickabout";
	    Class.forName(myDriver);
	    Connection conn = DriverManager.getConnection(myUrl, "root", "");
	    
	    String query = "update pitches set image = ? where id = ?";
		
		FileInputStream fis = null;
		PreparedStatement preparedStmt = null;
		
		if (!uploadedFile.isEmpty()) 
		{
			try 
			{
				conn.setAutoCommit(false);
			    File convFile = new File(uploadedFile.getOriginalFilename());
			    convFile.createNewFile(); 
			    fis = new FileInputStream(convFile);
			    
			    // create the java mysql update prepared statement
			    preparedStmt = conn.prepareStatement(query);
			    preparedStmt.setBlob(1, fis);
			    preparedStmt.setLong(2, pitch.getId());

			    // execute the java prepared statement
			    preparedStmt.executeUpdate();
			    conn.commit();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally
			{
				preparedStmt.close();
				fis.close();
			}
			
		}
        
		List<PitchFeature> pitchFeatures = pitchFeatureService.findPitchFeaturesByPitch(pitch);
        
		mv.addObject("pitch", pitch);
		mv.addObject("pitchId", pitchId);
		mv.addObject("pitchFeatures", pitchFeatures);
		mv.addObject("pitchForm", pitchForm);
		
		return mv;
	}
	 
}
