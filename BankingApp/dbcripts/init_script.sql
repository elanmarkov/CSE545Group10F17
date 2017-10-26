-- USERS
CREATE TABLE users (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(40) NOT NULL,
  role varchar(15) NOT NULL,
  address varchar(50) NOT NULL,
  state varchar(10) NOT NULL,
  city varchar(20) NOT NULL,
  zipcode varchar(10) NOT NULL,
  country varchar(30) NOT NULL,
  phone varchar(30) NOT NULL,
  email varchar(30) NOT NULL,
  PRIMARY KEY (id)
);


-- ACCOUNTS
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

CREATE TABLE credit_accounts (
    id int NOT NULL AUTO_INCREMENT,
    userId int NOT NULL,
    accountNumber varchar(255) NOT NULL, -- Credit card account numbers will be larger ints (full credit card number)
    currentAmountDue double NOT NULL,
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
    initiatorID int NOT NULL;
    amount double NOT NULL,
    stamp Timestamp NOT NULL,
    toAccountID varchar(255), -- Null means withdrawl
    description varchar(255) NOT NULL, -- This will state if it is a withdrawl or deposit for easier reading
    fromAccountID varchar(255), -- Null means deposit
    PRIMARY KEY (id)
);

--REQUSTS
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

-- LOGIN
CREATE TABLE user_login (
  id int NOT NULL AUTO_INCREMENT,
  role varchar(20) NOT NULL,
  username varchar(20) NOT NULL,
  password varchar(20) NOT NULL,
  user_id int NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE pii_info (
    id int NOT NULL AUTO_INCREMENT,
    userId int NOT NULL,
    dob varchar(10) NOT NULL,
    ssn varchar(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE OTP(
	email varchar(40) NOT NULL,
	otp varchar(10) NOT NULL,
	stamp Timestamp NOT NULL,
	attempts int NOT NULL
);

-- LOGS
CREATE TABLE internal_log(
  id int NOT NULL AUTO_INCREMENT,
  userId int NOT NULL,
  activity varchar(255) NOT NULL,
  details varchar(255) NOT NULL,
  stamp Timestamp NOT NULL,
  FOREIGN KEY(userid) REFERENCES internal_users(id) ON UPDATE CASCADE
);

CREATE TABLE external_log(
  id int NOT NULL AUTO_INCREMENT,
  userId int NOT NULL,
  activity varchar(255) NOT NULL,
  details varchar(255) NOT NULL,
  stamp Timestamp NOT NULL,
  FOREIGN KEY(userid) REFERENCES external_users(id) ON UPDATE CASCADE
 );

-- MAKE SUPERUSER FOR TESTING. CHANGE PASS FOR PROD
INSERT INTO user_login (role, username, password, user_id) VALUES ("adim", "admin_username", "1234", 1234)

 -- INTERNAL REQUESTS (CHANGE USER ACCOUNT INFO)
 -- The values here represent what values we want to change in the external users account
 CREATE TABLE pending_internal_requests (
   id int NOT NULL AUTO_INCREMENT,
   externalUserID int NOT NULL,
   address varchar(255),
   city varchar(255),
   state varchar(255),
   zipcode varchar(255) ,
   country varchar(255),
   phone varchar(255),
   submitterID int NOT NULL, -- The Tier 1 employee that submitted the request
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
