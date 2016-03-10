package nmarlor.kickabout.pitch;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PitchAvailabilityController {
	
	@Autowired
	private PitchAvailabilityService pitchAvailabilityService;
	
	@Autowired
	private PitchesService pitchesService;
	
	@Autowired
	private PitchFeatureService PitchFeatureService;
	
	@RequestMapping(value = "/availability", method = RequestMethod.GET)
	public ModelAndView pitchAvailability(Long pitchId){
		ModelAndView mv = new ModelAndView("pitchAvailability/availabilityAndFeatures");
		
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		Date formattedDate = getTodaysDate();
		
		List<PitchFeature> pitchFeatures = PitchFeatureService.findPitchFeaturesByPitch(pitch);
		List<PitchAvailability> pitchAvailabilities = pitchAvailabilityService.findPitchAvailabilityByPitchAndDate(pitch, formattedDate);
		
		PitchForm pitchForm = new PitchForm();
		pitchForm.setPitchId(pitchId);
		
		// Date to be displayed on the front end
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String date = df.format(formattedDate);
		
		mv.addObject("pitchId", pitchId);
		mv.addObject("pitch", pitch);
		mv.addObject("pitchFeatures", pitchFeatures);
		mv.addObject("pitchForm", pitchForm);
		mv.addObject("date", date);
		mv.addObject("pitchAvailabilities", pitchAvailabilities);
		
		return mv;
	}
	
	/**
	 * Service for getting todays date and converting it to SQL format
	 * @return Date
	 */
	private Date getTodaysDate()
	{
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate = calendar.getTime();
		Date date = new Date(currentDate.getTime());
		return date;
	}
	
	@RequestMapping(value = "/availability", method = RequestMethod.POST)
	public ModelAndView pitchAvailability(@Valid @ModelAttribute("pitchForm") PitchForm pitchForm, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("pitchAvailability/availabilityAndFeatures");
		mv.addObject("pitchForm", pitchForm);
		
		String date = pitchForm.getDate();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
		LocalDate availabilityDate = LocalDate.parse(date, formatter);
		Date checkDate = Date.valueOf(availabilityDate);
		
		Long pitchId = pitchForm.getPitchId();
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		
		List<PitchFeature> pitchFeatures = PitchFeatureService.findPitchFeaturesByPitch(pitch);
		List<PitchAvailability> pitchAvailabilities = pitchAvailabilityService.findPitchAvailabilityByPitchAndDate(pitch, checkDate);

		mv.addObject("pitchForm", pitchForm);
		mv.addObject("pitch", pitch);
		mv.addObject("date", date);
		mv.addObject("pitchFeatures", pitchFeatures);
		mv.addObject("pitchAvailabilities", pitchAvailabilities);
		
		return mv;
	}
}
