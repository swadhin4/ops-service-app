package com.ops.app.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.ops.jpa.entities.ServiceProvider;

public class AuthorizedUserDetails extends User {

	private static final long serialVersionUID = -7720854002321418429L;

	private String salt;

	private com.ops.jpa.entities.User user;
	
	private ServiceProvider serviceProvider;

	public AuthorizedUserDetails(
			final String userName, 
			final String password, 
			final boolean enabled,
			final boolean accountNonExpired, final boolean credentialsNonExpired, final boolean accountNonLocked,
			final Collection<? extends GrantedAuthority> authorities) {
		super(userName, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(final String salt) {
		this.salt = salt;
	}

	public com.ops.jpa.entities.User getUser() {
		return user;
	}

	public void setUser(com.ops.jpa.entities.User user) {
		this.user = user;
	}

	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}


}
