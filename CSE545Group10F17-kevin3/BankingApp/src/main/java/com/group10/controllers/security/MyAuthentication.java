package com.group10.controllers.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.group10.dao.loginAttempts.LoginAttemptsDaoImpl;

public class MyAuthentication extends DaoAuthenticationProvider {
	LoginAttemptsDaoImpl ldao;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username= authentication.getName();
		try {
			Authentication auth = super.authenticate(authentication);
			// if reach here, means login success, else exception will be thrown
			// reset the user_attempts\
			ldao.resetFailAttempts(username);
			return auth;
		} catch (BadCredentialsException e) {
			ldao.updateFailAttempts(username);
			throw e;
		} catch (LockedException e) {
			String error = "";
			int userAttempts = ldao.getNoUserAttempts(username);
			if (userAttempts>3) {
				ldao.lockUserAccount(username);

			} else {
				error = e.getMessage();
			}

			throw new LockedException(error);
		}
	}
	public LoginAttemptsDaoImpl getUserDetails() {
		return ldao;
	}

	public void setUserDetails(LoginAttemptsDaoImpl userDetails) {
		this.ldao = userDetails;
	}


}