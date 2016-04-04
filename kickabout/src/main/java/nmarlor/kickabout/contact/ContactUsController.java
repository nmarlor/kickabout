package nmarlor.kickabout.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
class ContactUsController {
	
	@Autowired
	private ContactService contactService;
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView contactUs()
	{
		ModelAndView mv = new ModelAndView("contact/contact");
		
		ContactForm contactForm = new ContactForm();
		
		mv.addObject("contactForm", contactForm);
		return mv;
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public ModelAndView sendEmail(@ModelAttribute("contactForm") ContactForm contactForm, BindingResult bindingResult)
	{
		contactService.sendEmail(contactForm);
		ModelAndView successMv = new ModelAndView("contact/contactSuccessful");
		return successMv;
	}
}