package nmarlor.kickabout.account;

import java.security.Principal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManageAccountController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping(value = "/manageAccount", method = RequestMethod.GET)
	public ModelAndView manageAccount(Principal principal)
	{	
		String name = principal.getName();
		Account account = accountRepository.findByEmail(name);
		String role = account.getRole();
		
		if (role.equals("ROLE_USER")) {
			ModelAndView manageClientAccount = new ModelAndView("manage/manageClientAccount");
			manageClientAccount.addObject("account", account);
			return manageClientAccount;
		}
		if (role.equals("ROLE_ADMIN")) {
			ModelAndView manageClientAccount = new ModelAndView("manage/manageAdminAccount");
			manageClientAccount.addObject("account", account);
			return manageClientAccount;
		}
		return new ModelAndView("home/homepage");
	}
}
