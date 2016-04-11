package nmarlor.kickabout.account;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import nmarlor.kickabout.company.Company;
import nmarlor.kickabout.company.CompanyService;
import nmarlor.kickabout.pitch.PitchLocation;
import nmarlor.kickabout.pitch.PitchLocationService;

@Controller
public class ManageAccountController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UpdateAccountInfoValidator accountInfoValidator;
	
	@Autowired
	private UpdateAdminAccountInfoValidator adminAccountInfoValidator;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private PitchLocationService pitchLocationService;

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
			manageClientAccount.addObject("accountId", accountId);
			return manageClientAccount;
		}
		if (role.equals("ROLE_ADMIN")) {
			ModelAndView manageAdminAccount = new ModelAndView("manage/manageAdminAccount");
			manageAdminAccount.addObject("account", account);
			
			UpdateAdminAccountForm accountForm = new UpdateAdminAccountForm();
			accountForm.setAccountId(accountId);
			accountForm.setEmail(email);
			accountForm.setName(accountName);
			
			manageAdminAccount.addObject("accountForm", accountForm);
			manageAdminAccount.addObject("accountId", accountId);
			return manageAdminAccount;
		}
		return new ModelAndView("home/homepage");
	}
	
	@RequestMapping(value = "/manageClientAccount", method = RequestMethod.POST)
	public ModelAndView updateClientAccount(@ModelAttribute("accountForm") UpdateAccountInfoForm accountForm, BindingResult bindingResult)
	{	
		ModelAndView mv = new ModelAndView("manage/manageClientAccount");
		
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
		
		ModelAndView successMv = new ModelAndView("manage/successfulAccountUpdate");
		successMv.addObject("accountForm", accountForm);
		successMv.addObject("accountId", accountId);
		
		return successMv;
	}
	
	@RequestMapping(value = "/manageAdminAccount", method = RequestMethod.POST)
	public ModelAndView updateAdminAccount(@ModelAttribute("accountForm") UpdateAdminAccountForm accountForm, BindingResult bindingResult)
	{	
		ModelAndView mv = new ModelAndView("manage/manageAdminAccount");
		
		adminAccountInfoValidator.validate(accountForm, bindingResult);
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
		
		ModelAndView successMv = new ModelAndView("manage/successfulAccountUpdate");
		successMv.addObject("accountForm", accountForm);
		successMv.addObject("accountId", accountId);
		
		return successMv;
	}
	
	@RequestMapping(value = "companiesPitches", method = RequestMethod.GET)
	public ModelAndView viewPitchBookings(Long accountId){
		ModelAndView mv = new ModelAndView("locations/adminsPitches");
		
		Account account = accountService.retrieveAccount(accountId);
		List<Company> accountsCompanies = companyService.getAccountsCompanies(account);
		List<PitchLocation> locationsForCompany = new ArrayList<>();
		
		for (Company company : accountsCompanies) {
			List<PitchLocation> locationsForCompanyFound = pitchLocationService.findPitchLocationsByCompany(company);
			for (PitchLocation pitchLocation : locationsForCompanyFound) {
				locationsForCompany.add(pitchLocation);
			}
		}
		
		mv.addObject("locationsForCompany", locationsForCompany);
		mv.addObject("accountsCompanies", accountsCompanies);
		return mv;
	}
}
