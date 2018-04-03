package com.ops.app.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ops.jpa.entities.User;
import com.ops.jpa.entities.UserRole;
import com.ops.jpa.repository.UserRepository;


@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {

        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase();

        User userFromDatabase;
        if(lowercaseLogin.contains("@")) {
            userFromDatabase = userRepository.findUserByEmail(lowercaseLogin);
        } else {
            userFromDatabase = userRepository.findByUsernameCaseInsensitive(lowercaseLogin);
        }

        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        }

        boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		AuthorizedUserDetails authorizedUserDetails =
				new AuthorizedUserDetails(userFromDatabase.getEmailId(), userFromDatabase.getPassword(), true, accountNonExpired,
						credentialsNonExpired, accountNonLocked, getAuthorities(userFromDatabase.getUserRoles()));
		authorizedUserDetails.setUser(userFromDatabase);
		return authorizedUserDetails;

    }

    private Collection<? extends GrantedAuthority> getAuthorities( List<UserRole> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(UserRole role:roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole().getRoleName()));
		}

		return authorities;
	}

}
