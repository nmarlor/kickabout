package nmarlor.kickabout.account;

import java.security.Principal;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import nmarlor.kickabout.pitch.LocationForm;
import nmarlor.kickabout.pitch.Pitch;
import nmarlor.kickabout.pitch.PitchLocation;
import nmarlor.kickabout.pitch.PitchLocationService;
import nmarlor.kickabout.pitch.PitchesService;

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
	
	@Autowired
	private PitchesService pitchesService;
	
	@Autowired
	private PitchLocationService locationService;

	@RequestMapping(value = "/manageAccount", method = RequestMethod.GET)
	public ModelAndView manageAccount(Principal principal)
	{	
		String name = principal.getName();
		Account account = accountRepository.findByEmail(name);
		String role = account.getRole();
		Long accountId = account.getId();
		String accountName = account.getName();
		String email = account.getEmail();
		String telephone = account.getTelephone();
		
		if (role.equals("ROLE_USER")) {
			ModelAndView manageClientAccount = new ModelAndView("manage/manageClientAccount");
			manageClientAccount.addObject("account", account);
			
			UpdateAccountInfoForm accountForm = new UpdateAccountInfoForm();
			accountForm.setAccountId(accountId);
			accountForm.setEmail(email);
			accountForm.setName(accountName);
			accountForm.setTelephone(telephone);
			
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
			accountForm.setTelephone(telephone);
			
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
			accountForm.setTelephone(telephone);
			
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
		account.setTelephone(accountForm.getTelephone());
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
		account.setTelephone(accountForm.getTelephone());
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
		String name = principal.getName();
		Account account = accountRepository.findByEmail(name);
		String role = account.getRole();
		
		// Need to check if user is a super admin, to prevent any user from accessing page
		if (role.equals("ROLE_SUPER_ADMIN")) 
		{
			ModelAndView mv = new ModelAndView("manage/newUser");
			
			NewAccountForm accountForm = new NewAccountForm();
			
			mv.addObject("accountForm", accountForm);
			return mv;
		}
		
		ModelAndView mv = new ModelAndView("home/homepage");
		
		LocationForm locationForm = new LocationForm();
		mv.addObject("locationForm", locationForm);
		
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
											accountForm.getTelephone(),
											accountForm.getRole()));
		
		return thisMv;
	}
	
	@RequestMapping(value = "viewAdminUsers", method = RequestMethod.GET)
	public ModelAndView viewAdminUsers(Principal principal){
		String name = principal.getName();
		Account account = accountRepository.findByEmail(name);
		String role = account.getRole();
		
		// Need to check if user is a super admin, to prevent any user from accessing page
		if (role.equals("ROLE_SUPER_ADMIN")) 
			{
				ModelAndView mv = new ModelAndView("manage/viewAdminUsers");
				
				List<Account> adminAccounts = accountService.findAllAdmins("ROLE_ADMIN");
				
				mv.addObject("adminAccounts", adminAccounts);
				return mv;
			}
		
		ModelAndView mv = new ModelAndView("home/homepage");
		
		LocationForm locationForm = new LocationForm();
		mv.addObject("locationForm", locationForm);
		
		return mv;
	}
	
	@RequestMapping(value = "adminAccountInfo", method = RequestMethod.GET)
	public ModelAndView adminAccountInfo(Long id){
		ModelAndView mv = new ModelAndView("manage/adminAccountInfo");
		
		Account account = accountService.retrieveAccount(id);
		
		mv.addObject("account", account);
		return mv;
	}
	
	@RequestMapping(value = "editAdminAccountInfo", method = RequestMethod.GET)
	public ModelAndView editAdminAccountInfoRequest(Long id){
		ModelAndView mv = new ModelAndView("manage/editAdminAccountInfo");
		
		Account account = accountService.retrieveAccount(id);
		
		UpdateAdminAccountForm accountForm = new UpdateAdminAccountForm();
		accountForm.setAccountId(id);
		accountForm.setEmail(account.getEmail());
		accountForm.setName(account.getName());
		accountForm.setTelephone(account.getTelephone());
		
		mv.addObject("account", account);
		mv.addObject("accountForm", accountForm);
		return mv;
	}
	
	@RequestMapping(value = "editAdminAccountInfo", method = RequestMethod.POST)
	public ModelAndView editAdminAccountInfo(@ModelAttribute("accountForm") UpdateAdminAccountForm accountForm, BindingResult result, HttpServletRequest request){
		ModelAndView thisMv = new ModelAndView("manage/editAdminAccountInfo");
		Account account = accountService.retrieveAccount(accountForm.getAccountId());
		
		adminAccountInfoValidator.validate(accountForm, result);
		if (result.hasErrors()) 
		{
			thisMv.addObject("errors", result);
			return thisMv;
		}
		
		String email = accountForm.getEmail();
		String name = accountForm.getName();
		String telephone = accountForm.getTelephone();
		
		account.setEmail(email);
		account.setName(name);
		account.setTelephone(telephone);
		
		accountService.updateAccount(account);
		
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setModelKey("redirect");
		return new ModelAndView (jsonView, "redirect", request.getContextPath() + "manage/viewAdminUsers");
	}
	
	@RequestMapping(value = "/viewBookingsForAccount", method = RequestMethod.GET)
	public ModelAndView viewBookingsForAllPitches(Long accountId){
		ModelAndView mv = new ModelAndView("booking/viewBookingsForAccount");
		
		Date formattedDate = dateService.getTodaysDate();
		
		Account account = accountService.retrieveAccount(accountId);
		List<PitchLocation> locations = pitchLocationService.findPitchLocationsForAccount(account);
		
		LocationForm locationForm = new LocationForm();
		locationForm.setAccountId(accountId);
		
		// Date to be displayed on the front end
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String date = df.format(formattedDate);
		
		List<Booking> bookings = new ArrayList<>();
		
		if (!locations.isEmpty()) 
		{
			Set<Long> locationIds = new HashSet<>();
			
			for (PitchLocation location : locations) 
			{
				List<Pitch> pitches = pitchesService.findPitchesByLocation(location);
				
				Long locationId = location.getId();
				locationIds.add(locationId);
				
				for (Pitch pitch : pitches) 
				{
					List<Booking> pitchAvailabilities = bookingService.findBookingsByPitchAndDate(pitch, formattedDate);
					bookings.addAll(pitchAvailabilities);
				}
			}
			
			locationForm.setLocationIds(locationIds);
		
			mv.addObject("locationForm", locationForm);
			mv.addObject("account", account);
			mv.addObject("date", date);
			mv.addObject("bookings", bookings);
			
			return mv;
		}
		
		//Else there are no locations for the account so screen shows empty table
		mv.addObject("locationForm", locationForm);
		mv.addObject("account", account);
		mv.addObject("date", date);
		mv.addObject("bookings", bookings);
		
		return mv;
	}
	
	@RequestMapping(value = "/viewBookingsForAccount", method = RequestMethod.POST)
	public ModelAndView viewBookingsForAccount(@Valid @ModelAttribute("locationForm") LocationForm locationForm, BindingResult bindingResult, String date) {
		ModelAndView mv = new ModelAndView("booking/viewBookingsForAccount");
		mv.addObject("locationForm", locationForm);
		
		Set<Long> locationIds = locationForm.getLocationIds();
		
		Long accountId = locationForm.getAccountId();
		Account account = accountService.retrieveAccount(accountId);
		
		List<Booking> bookings = new ArrayList<>();
		
		Date availabilityDate = dateService.stringToDate(date);
		
		for (Long locationId : locationIds) 
		{
			PitchLocation location = locationService.retrieve(locationId);
			List<Pitch> pitches = pitchesService.findPitchesByLocation(location);
			
			if (availabilityDate != null) 
			{
				for (Pitch pitch : pitches) 
				{
					List<Booking> pitchAvailabilities = bookingService.findBookingsByPitchAndDate(pitch, availabilityDate);
					bookings.addAll(pitchAvailabilities);
				}
			}
		}

		// Date to be displayed on the front end
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		date = df.format(availabilityDate);
		
		mv.addObject("locationForm", locationForm);
		mv.addObject("date", date);
		mv.addObject("bookings", bookings);
		mv.addObject("account", account);
				
		return mv;
	}
	
	@RequestMapping(value = "viewStandardUsers", method = RequestMethod.GET)
	public ModelAndView viewStandardUsers(Principal principal){
		String name = principal.getName();
		Account account = accountRepository.findByEmail(name);
		String role = account.getRole();
		
		// Need to check if user is a super admin, to prevent any user from accessing page
		if (role.equals("ROLE_SUPER_ADMIN")) 
			{
				ModelAndView mv = new ModelAndView("manage/viewStandardUsers");
				
				List<Account> standardAccounts = accountService.findAllAdmins("ROLE_USER");
				
				mv.addObject("standardAccounts", standardAccounts);
				return mv;
			}
		
		ModelAndView mv = new ModelAndView("home/homepage");
		
		LocationForm locationForm = new LocationForm();
		mv.addObject("locationForm", locationForm);
		
		return mv;
	}
	
	@RequestMapping(value = "viewUsersBookings", method = RequestMethod.GET)
	public ModelAndView viewUsersBookings(Long accountId){
		Account account = accountService.retrieveAccount(accountId);
		
		ModelAndView mv = new ModelAndView("booking/viewUsersBookings");
		
		List<Booking> bookings = bookingService.findBookingsForAccount(account);
		
		mv.addObject("account", account);
		mv.addObject("bookings", bookings);
		return mv;
	}
}
