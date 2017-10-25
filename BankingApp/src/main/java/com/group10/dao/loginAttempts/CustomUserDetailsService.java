package com.group10.dao.loginAttempts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetailsService extends JdbcDaoImpl{
	
	public void setUsersByUsernameQuery(String usersByUsernameQueryString) {
		super.setUsersByUsernameQuery(usersByUsernameQueryString);
	}
	
	public void setAuthoritiesByUsernameQuery(String queryString) {
		super.setAuthoritiesByUsernameQuery(queryString);
	}
	
	public List<UserDetails> loadUsersByUsername(String username) {
		return getJdbcTemplate().query(super.getUsersByUsernameQuery(), new String[] { username },
				new RowMapper<UserDetails>() {
					public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
						String username = rs.getString("username");
						String password = rs.getString("password");
						String role = rs.getString("role");
						int accountStatus = rs.getInt("accountStatus");
						int otpExpireStatus = rs.getInt("otpExpireStatus");
						int attempts = rs.getInt("attempts");
						return new User(username, password, AuthorityUtils.NO_AUTHORITIES);
					}

				});
	}

	//override to pass accountNonLocked
	@Override
	public UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities) {
		String returnUsername = userFromUserQuery.getUsername();

		if (super.isUsernameBasedPrimaryKey()) {
			returnUsername = username;
		}

		return new User(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
				userFromUserQuery.isAccountNonExpired(), userFromUserQuery.isCredentialsNonExpired(),
				userFromUserQuery.isAccountNonLocked(), combinedAuthorities);
	}
}
