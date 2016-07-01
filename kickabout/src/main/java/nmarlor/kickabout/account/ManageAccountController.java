package nmarlor.kickabout.account;

import java.security.Principal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import nmarlor.kickabout.booking.Booking;
import nmarlor.kickabout.booking.BookingService;
import nmarlor.kickabout.company.Company;
import nmarlor.kickabout.company.CompanyService;
import nmarlor.kickabout.date.DateService;
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
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private DateService dateService;
	
	@Autowired
	private NewAccountValidator newAccountValidator;

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
		if (role.equals("ROLE_SUPER_ADMIN")) {
			ModelAndView manageSuperAdminAccount = new ModelAndView("manage/manageSuperAdminAccount");
			manageSuperAdminAccount.addObject("account", account);
			
			UpdateAdminAccountForm accountForm = new UpdateAdminAccountForm();
			accountForm.setAccountId(accountId);
			accountForm.setEmail(email);
			accountForm.setName(accountName);
			
			manageSuperAdminAccount.addObject("accountForm", accountForm);
			manageSuperAdminAccount.addObject("accountId", accountId);
			return manageSuperAdminAccount;
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
		successMv.addObject("account", account);
		
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
	
	@RequestMapping(value = "accountsPitchLocations", method = RequestMethod.GET)
	public ModelAndView accountsPitchLocations(Long accountId){
		ModelAndView mv = new ModelAndView("locations/adminsPitches");
		
		Account account = accountService.retrieveAccount(accountId);
		List<PitchLocation> locationsForAccount = pitchLocationService.findPitchLocationsForAccount(account);
		
		mv.addObject("locationsForAccount", locationsForAccount);
		mv.addObject("accountId", accountId);
		return mv;
	}
	
	@RequestMapping(value = "deleteMyAccount", method = RequestMethod.GET)
	public ModelAndView deleteMyAccountRequest(Long id){
		ModelAndView mv = new ModelAndView("manage/deleteMyAccount");
		
		Account account = accountService.retrieveAccount(id);
		
		UpdateAccountInfoForm accountForm = new UpdateAccountInfoForm();
		accountForm.setAccountId(account.getId());
		
		mv.addObject("accountForm", accountForm);
		mv.addObject("account", account);
		
		return mv;
	}
	
	@RequestMapping(value = "deleteMyAccount", method = RequestMethod.POST)
	public ModelAndView deleteMyAccount(@ModelAttribute("accountForm") UpdateAccountInfoForm accountForm, BindingResult result, HttpServletRequest request){		
		ModelAndView mv = new ModelAndView("manage/deleteMyAccount");
		Account account = accountService.retrieveAccount(accountForm.getAccountId());
		mv.addObject("account", account);
		
		List<Booking> bookings = bookingService.findBookingsForAccount(account);
		Date todaysDate = dateService.getTodaysDate();
		List<Booking> bookingsToDelete = new ArrayList<>();
		List<Booking> bookingsToReassignAccount = new ArrayList<>();
		
		Calendar cal = Calendar.getInstance();
		Time currentTime = Time.valueOf(
		          cal.get(Calendar.HOUR_OF_DAY) + ":" +
		          cal.get(Calendar.MINUTE) + ":" +
		          cal.get(Calendar.SECOND));
		
		if (!bookings.isEmpty()) 
		{
			for (Booking booking : bookings) 
			{
				Date date = booking.getDate();
				LocalDate localDate = date.toLocalDate();
				LocalDate todayLocalDate = todaysDate.toLocalDate();
				
				if (localDate.equals(todayLocalDate) || localDate.isAfter(todayLocalDate) && booking.getBookedFrom().after(currentTime) || booking.getBookedTo().after(currentTime)) 
				{
					bookingsToDelete.add(booking);
				}
				else
				{
					bookingsToReassignAccount.add(booking);
				}
			}
			for (Booking booking : bookingsToDelete) 
			{
				bookingService.delete(booking);
			}
			for (Booking booking : bookingsToReassignAccount) 
			{
				Account adminsAccount = booking.getPitch().getPitchLocation().getAccount();
				booking.setAccount(adminsAccount);
				bookingService.update(booking);
			}
			accountService.deleteAccount(account);
		}
		else 
		{
			accountService.deleteAccount(account);
		}
		
		//TODO redirect needs to be fixed, throws null pointer!
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setModelKey("redirect");
		return new ModelAndView (jsonView, "redirect", request.getContextPath()+ "signin/signin");
	}
	
	@RequestMapping(value = "addNewUser", method = RequestMethod.GET)
	public ModelAndView addNewUserRequest(Principal principal){
		ModelAndView mv = new ModelAndView("manage/newUser");
		
		NewAccountForm accountForm = new NewAccountForm();
		
		mv.addObject("accountForm", accountForm);
		return mv;
	}
	
	@RequestMapping(value = "addNewUser", method = RequestMethod.POST)
	public ModelAndView addNewUser(@ModelAttribute("accountForm") NewAccountForm accountForm, BindingResult bindingResult){
		ModelAndView thisMv = new ModelAndView("manage/newUser");
		
		newAccountValidator.validate(accountForm, bindingResult);
		if (bindingResult.hasErrors()) 
		{
			thisMv.addObject("errors", bindingResult);
			return thisMv;
		}
		
		String email = accountForm.getEmail();
		List<Account> accounts = accountService.findAll();
		for (Account account : accounts) 
		{
			if (account.getEmail().equals(email)) 
			{
				bindingResult.rejectValue("email", "email.exists.message");
				thisMv.addObject("errors", bindingResult);
				return thisMv;
			}
		}
		
		accountRepository.save(new Account(accountForm.getEmail(),
											accountForm.getName(), 
											accountForm.getPassword(), 
											accountForm.getRole()));
		
		return thisMv;
	}
}
