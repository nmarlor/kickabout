package nmarlor.kickabout.home;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String index(Principal principal) {
//		return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
//	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(Principal principal){
		ModelAndView mv = new ModelAndView("home/homepage");
		return mv;
	}
}
