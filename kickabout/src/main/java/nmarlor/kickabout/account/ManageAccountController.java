package nmarlor.kickabout.account;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManageAccountController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UpdateAccountInfoValidator accountInfoValidator;

	@RequestMapping(value = "/manageAccount", method = RequestMethod.GET)
	public ModelAndView manageAccount(Principal principal)
	{	
		String name = principal.getName();
		Account account = accountRepository.findByEmail(name);
		String role = account.getRole();
		Long accountId = account.getId();
		String accountName = account.getName();
		String email = account.getEmail();
		
		if (role.equals("ROLE_USER")) {
			ModelAndView manageClientAccount = new ModelAndView("manage/manageClientAccount");
			manageClientAccount.addObject("account", account);
			
			UpdateAccountInfoForm accountForm = new UpdateAccountInfoForm();
			accountForm.setAccountId(accountId);
			accountForm.setEmail(email);
			accountForm.setName(accountName);
			
			manageClientAccount.addObject("accountForm", accountForm);
			return manageClientAccount;
		}
		if (role.equals("ROLE_ADMIN")) {
			ModelAndView manageAdminAccount = new ModelAndView("manage/manageAdminAccount");
			manageAdminAccount.addObject("account", account);
			return manageAdminAccount;
		}
		return new ModelAndView("home/homepage");
	}
	
	@RequestMapping(value = "/manageClientAccount", method = RequestMethod.POST)
	public ModelAndView updateClientAccount(@ModelAttribute("accountForm") UpdateAccountInfoForm accountForm, BindingResult bindingResult)
	{	
		ModelAndView mv = new ModelAndView("manage/manageClientAccount");
		mv.addObject("accountForm", accountForm);
		
		accountInfoValidator.validate(accountForm, bindingResult);
		if (bindingResult.hasErrors()) 
		{
			mv.addObject("errors", bindingResult);
			return mv;
		}
		
		Long accountId = accountForm.getAccountId();
		Account account = accountService.retrieveAccount(accountId);
		
		account.setEmail(accountForm.getEmail());
		account.setName(accountForm.getName());
		accountService.updateAccount(account);
		
		return mv;
	}
}
