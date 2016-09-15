package nmarlor.kickabout.account;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;

public class UserService implements UserDetailsService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@PostConstruct	
	protected void initialize() {
		accountRepository.save(new Account("user", "test user", "user", "0161 234 8953", "ROLE_USER"));
		accountRepository.save(new Account("admin", "test admin", "admin", "0161 766 1092", "ROLE_ADMIN"));
		accountRepository.save(new Account("testkickabout@gmail.com", "kickabout", "kickabout", "0161 794 6835", "ROLE_SUPER_ADMIN"));
		accountRepository.save(new Account("anotheradmin@admin", "another admin", "admin", "0161 773 4192", "ROLE_ADMIN"));
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(username);
		if(account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return createUser(account);
	}
	
	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}
	
	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUser(account), null, Collections.singleton(createAuthority(account)));		
	}
	
	private User createUser(Account account) {
		return new User(account.getEmail(), account.getPassword(), Collections.singleton(createAuthority(account)));
	}

	private GrantedAuthority createAuthority(Account account) {
		return new SimpleGrantedAuthority(account.getRole());
	}

}
