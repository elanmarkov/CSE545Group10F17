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
    completedStamp Timestamp DEFAULT CURRENT_TIMESTAMP,
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
  completedStamp Timestamp DEFAULT CURRENT_TIMESTAMP,
  toAccountID varchar(255) NOT NULL,
  fromAccountID varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  payerID int NOT NULL,
  initiatorID int NOT NULL,
  PRIMARY KEY (id)
);



 -- Kevin
