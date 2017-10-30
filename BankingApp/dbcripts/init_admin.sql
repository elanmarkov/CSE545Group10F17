-- Plaintext password: abcXYZ123!
INSERT INTO user_login (username, password, enabled, role, accountNonExpired, accountNonLocked, credentialsNonExpired, otpNonLocked) VALUES ("admin@ss-g10.com", "a0a1c1fd5bd5302996afd263581a22cf8aadcea2588018a617f5ab100c7c2c84", 1, "ROLE_ADMIN", 1, 1, 1, 1);
INSERT INTO users (id, name, role, address, city, state, country, zipcode, phone, email) VALUES (0, 'Admin Istrator', 'ROLE_ADMIN', '123 Administrator Street', 'Temple', 'AZ', 'United States', '85281', '123-456-7890', 'admin@ss-g10.com');
INSERT INTO user_login_attempts (username, attempts) VALUES ('admin@ss-g10.com', 0);
