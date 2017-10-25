-- ACCOUNTS
CREATE TABLE internal_users (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(40) NOT NULL,
  role varchar(15) NOT NULL,
  address varchar(50) NOT NULL,
  city varchar(20) NOT NULL,
  state varchar(10) NOT NULL,
  country varchar(30) NOT NULL,
  zipcode varchar(10) NOT NULL,
  phone varchar(30) NOT NULL,
  email varchar(30) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE external_users (
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

CREATE TABLE checking_accounts (
    id int NOT NULL,
    external_users_id int NOT NULL,
    account_no varchar(20) NOT NULL,
    checking_card_no varchar(30) NOT NULL:
    balance double NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE savings_accounts (
    id int NOT NULL,
    external_users_id int NOT NULL,
    account_no varchar(20) NOT NULL,
    balance double NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE credit_accounts (
    id int NOT NULL,
    external_users_id int NOT NULL,
    account_no varchar(20) NOT NULL,
    balance double NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE credit_cards (
    id int NOT NULL,
    credit_card_number varchar(20) NOT NULL,
    external_users_id int NOT NULL,
    cvv int NOT NULL,
    credit_limit double NOT NULL,
    current_amount_due double NOT NULL,
    cyle_date varchar(10) NOT NULL,
    due_date varchar(10) NOT NULL,
    last_bill_amount double NOT NULL,
    apr double NOT NULL,
    PRIMARY KEY (id)
);

-- TRANSACTIONS
CREATE TABLE completed_transactions (
    id int NOT NULL,
    initiator_id int NOT NULL,
    amount double NOT NULL,
    stamp Timestamp NOT NULL,
    to_account_id int NOT NULL,
    description varchar(255) NOT NULL,
    from_account_id int NOT NULL,
    reviewer_id int NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE pending_transactions (
    id int NOT NULL,
    initiator_id int NOT NULL;
    amount double NOT NULL,
    stamp Timestamp NOT NULL,
    to_account_id int NOT NULL,
    description varchar(255) NOT NULL,
    from_account_id int NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE transaction (
    id INT NOT NULL AUTO_INCREMENT,
    payer_id int NOT NULL,
    payee_id int NOT NULL,
    amount double NOT NULL,
    hashvalue VARCHAR(255) NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    status varchar(20) NOT NULL,
    reviewer_id int NOT NULL,
    critical BOOLEAN NOT NULL,
    timestamp_created TIMESTAMP NOT NULL,
    timestamp_updated TIMESTAMP NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(payer_id) REFERENCES account(id)  ON UPDATE CASCADE,
    FOREIGN KEY(payee_id) REFERENCES account(id)  ON UPDATE CASCADE
) ;

CREATE TABLE account(
    id int NOT NULL,
    name varchar(20);
);


--REQUSTS
CREATE TABLE pending_external_requests (
    id int NOT NULL,
    amount double NOT NULL,
    stamp Timestamp NOT NULL,
    to_account_id int NOT NULL,
    from_account_id int NOT NULL,
    description varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE completed_external_requests (
  id int NOT NULL,
  amount double NOT NULL,
  stamp Timestamp NOT NULL,
  to_account_id int NOT NULL,
  from_account_id int NOT NULL,
  description varchar(255) NOT NULL,
  PRIMARY KEY (id)
);


-- PII
CREATE TABLE pii_info (
    id int NOT NULL AUTO_INCREMENT,
    userid int NOT NULL,
    dob varchar(10) NOT NULL,
    ssn varchar(20) NOT NULL,
    PRIMARY KEY (id)
);


-- USERS


-- EXTERNAL LOGIN
CREATE TABLE user_login (
  username varchar(20) NOT NULL,
  password varchar(20) NOT NULL,
  role varchar(20) NOT NULL,
  accountStatus int NOT NULL,
  otpExpireStatus int NOT NULL,
  attempts int NOT NULL,
  lastModified timestamp NOT NULL,
  userId int NOT NULL,

  PRIMARY KEY (userId)
);

CREATE TABLE internal_log(
  id int NOT NULL AUTO_INCREMENT,
  userid int NOT NULL,
  activity varchar(255) NOT NULL,
  details varchar(255) NOT NULL,
  stamp Timestamp NOT NULL,
  FOREIGN KEY(userid) REFERENCES internal_users(id) ON UPDATE CASCADE
);


CREATE TABLE external_log(
  id int NOT NULL AUTO_INCREMENT,
  user_id int NOT NULL,
  activity varchar(255) NOT NULL,
  details varchar(255) NOT NULL,
  stamp Timestamp NOT NULL,
  FOREIGN KEY(userid) REFERENCES external_users(id) ON UPDATE CASCADE
 );

CREATE TABLE merchant_payment(
    id int NOT NULL,
    amount double NOT NULL,
    stamp Timestamp NOT NULL,
    payer_id int NOT NULL,
    token int NOT NULL,
    description varchar(255) NOT NULL,
    status varchar(10) NOT NULL,
    merchant_id int NOT NULL,
    PRIMARY KEY (id)
); 

CREATE TABLE OTP(
	email varchar(40) NOT NULL,
	otp varchar(10) NOT NULL,
	stamp Timestamp NOT NULL,
	attempts int NOT NULL	
);

CREATE TABLE user_authentication(

);


INSERT INTO user_login (role, username, password, user_id) VALUES ("adim", "admin_username", "1234", 1234)

 INTERNAL REQUESTS (CHANGE USER ACCOUNT INFO)
 CREATE TABLE pending_internal_requests (
   requestId int NOT NULL,
   userid int NOT NULL,
   address varchar(255),
   city varchar(255),
   state varchar(255),
   zipcode varchar(255) ,
   country varchar(255),
   phone varchar(255),
   PRIMARY KEY (id)
 );
--
-- CREATE TABLE completed_internal_requests (
--   id int NOT NULL,
--   request_initiated varchar(255) NOT NULL,
--   timeStamp int NOT NULL,
--   to_account_id int NOT NULL,
--   from_account_id int NOT NULL,
--   description varchar(255) NOT NULL,
--   CONSTRAINT id PRIMARY KEY (id)
-- );

 
 