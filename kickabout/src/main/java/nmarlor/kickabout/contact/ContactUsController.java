package nmarlor.kickabout.contact;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
class ContactUsController {
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView contactUs()
	{
		ModelAndView mv = new ModelAndView("contact/contact");
		return mv;
	}
}