
-- Done by Harsha
CREATE TABLE users (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(40) NOT NULL,
  role varchar(15) NOT NULL,
  address varchar(255) NOT NULL,
  city varchar(20) NOT NULL,
  state varchar(10) NOT NULL,
  country varchar(30) NOT NULL,
  zipcode varchar(10) NOT NULL,
  phone varchar(30) NOT NULL,
  email varchar(30) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE pii_info (
    id int NOT NULL AUTO_INCREMENT,
    userId int NOT NULL,
    dob varchar(20) NOT NULL,
    ssn varchar(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE logs(
  id int NOT NULL AUTO_INCREMENT,
  userId int NOT NULL,
  activity varchar(255) NOT NULL,
  details varchar(255) NOT NULL,
  stamp Timestamp NOT NULL,
  logtype varchar(10) NOT NULL,
  primary key(id)
);


CREATE TABLE pending_internal_requests (
    id int NOT NULL AUTO_INCREMENT,
    userId int NOT NULL,
    address varchar(255) NOT NULL,
    state varchar(20) NOT NULL,
    city varchar(20) NOT NULL,
  	country varchar(20) NOT NULL,
    zipcode varchar(20) NOT NULL,
    phone varchar(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE pending_ac_requests (
    id int NOT NULL AUTO_INCREMENT,
    userId int NOT NULL,
    address varchar(255) NOT NULL,
    state varchar(20) NOT NULL,
    city varchar(20) NOT NULL,
  	country varchar(20) NOT NULL,
    zipcode varchar(20) NOT NULL,
    phone varchar(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_login (
  username varchar(60) NOT NULL,
  password varchar(255) NOT NULL,
  enabled int NOT NULL,
  role varchar(20) NOT NULL,
  accountNonExpired int NOT NULL,
  accountNonLocked int NOT NULL,
  credentialsNonExpired int NOT NULL,
  otpNonLocked int NOT NULL,
  PRIMARY KEY(username)
);

create TABLE user_login_attempts(
	username varchar(60) NOT NULL,
	attempts int NOT NULL,
	PRIMARY KEY(username)
);
CREATE TABLE OTP(
	email varchar(50) NOT NULL,
	hexValOTP varchar(16) NOT NULL,
	issueTime Timestamp NOT NULL,
	attempts int NOT NULL
);

-- End by Harsha
-- Done by Kevin

CREATE TABLE checking_accounts (
    id int NOT NULL AUTO_INCREMENT,
    userId int NOT NULL,
    accountNumber varchar(255) NOT NULL,
    balance double NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE savings_accounts (
    id int NOT NULL AUTO_INCREMENT,
    userId int NOT NULL,
    accountNumber varchar(255) NOT NULL,
    balance double NOT NULL,
    PRIMARY KEY (id)
);

-- TRANSACTIONS
CREATE TABLE completed_transactions (
    id int NOT NULL AUTO_INCREMENT,
    amount double NOT NULL,
    initiatorID int NOT NULL,
    stamp Timestamp NOT NULL,
    completedStamp Timestamp NOT NULL,
    toAccountID varchar(255), -- Null means withdrawl
    description varchar(255) NOT NULL, -- This will state if it is a withdrawl or deposit for easier reading
    fromAccountID varchar(255), -- Null means deposit
    reviewerID int NOT NULL,
    status varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE pending_transactions (
    id int NOT NULL AUTO_INCREMENT,
    initiatorID int NOT NULL,
    amount double NOT NULL,
    stamp Timestamp NOT NULL,
    toAccountID varchar(255), -- Null means withdrawl
    description varchar(255) NOT NULL, -- This will state if it is a withdrawl or deposit for easier reading
    fromAccountID varchar(255), -- Null means deposit
    PRIMARY KEY (id)
);

-- REQUSTS
CREATE TABLE pending_external_requests (
    id int NOT NULL AUTO_INCREMENT,
    amount double NOT NULL,
    stamp Timestamp NOT NULL,
    toAccountID varchar(255) NOT NULL,
    fromAccountID varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    payerID int NOT NULL,
    initiatorID int NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE completed_external_requests (
  id int NOT NULL AUTO_INCREMENT,
  amount double NOT NULL,
  stamp Timestamp NOT NULL,
  completedStamp Timestamp NOT NULL,
  toAccountID varchar(255) NOT NULL,
  fromAccountID varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  payerID int NOT NULL,
  initiatorID int NOT NULL,
  PRIMARY KEY (id)
);

-- NEW
CREATE TABLE accNumToTableRel (
  id int NOT NULL AUTO_INCREMENT,
  accountNumber varchar(255) NOT NULL,
  `table` varchar(255) NOT NULL,
  userId int NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE credit_accounts (
    id int NOT NULL AUTO_INCREMENT,
    userId int NOT NULL,
    accountNumber varchar(255) NOT NULL, -- Credit card account numbers will be larger ints (full credit card number)
    balance double NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE credit_cards (
  -- NOTE: CREDIT  ACCOUNT NUMBER IS SAME AS CREDIT CARD NUMBER ASSOCIATED WITH THAT ACCOUNT
    id int NOT NULL AUTO_INCREMENT,
    creditCardNumber varchar(255) NOT NULL, -- Credit card account numbers will be larger ints (full credit card number)
    userId int NOT NULL,
    cvv int NOT NULL,
    creditLimit double NOT NULL,
    currentAmountDue double NOT NULL,
    cycleDate Timestamp NOT NULL,
    dueDate Timestamp NOT NULL,
    lastBillAmount double NOT NULL,
    apr double NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE completed_internal_requests (
  id int NOT NULL AUTO_INCREMENT,
  address varchar(255),
  city varchar(255),
  state varchar(255),
  zipcode varchar(255) ,
  country varchar(255),
  phone varchar(255),
  submitterID int NOT NULL, -- The Tier 1 employee that submitted the request
  reviewerID int NOT NULL, -- The Tier 2 employee that approved/denied the request
  status varchar(20) NOT NULL, -- Approved or denied
  CONSTRAINT id PRIMARY KEY (id)
);




INSERT INTO user_login (username, password, enabled, role, accountNonExpired, accountNonLocked, credentialsNonExpired, otpNonLocked) VALUES ("secret@ss.com", "a0a1c1fd5bd5302996afd263581a22cf8aadcea2588018a617f5ab100c7c2c84", 1, "ROLE_ADMIN", 1, 1, 1, 1);
INSERT INTO users (id, name, role, address, city, state, country, zipcode, phone, email) VALUES (1, 'Administrator', 'ROLE_ADMIN', '123 Administrator Street', 'Temple', 'AZ', 'United States', '85281', '123-456-7890', 'secret@ss.com');
INSERT INTO user_login_attempts (username, attempts) VALUES ('secret@ss.com', 0);
INSERT INTO pii_info (id, userId, dob, ssn) VALUES (1, 1, '1-1-1970', '123-45-6789');
