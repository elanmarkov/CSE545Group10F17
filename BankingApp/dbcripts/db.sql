CREATE TABLE internal_user (
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
designation VARCHAR(15) NOT NULL,
address VARCHAR(50) NOT NULL,
city VARCHAR(20) NOT NULL,
state VARCHAR(20) NOT NULL,
country VARCHAR(20) NOT NULL,
pincode INT NOT NULL,
phone INT NOT NULL,
PRIMARY KEY(id)
) ENGINE=InnoDB;

CREATE TABLE external_user (
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
address VARCHAR(50) NOT NULL,
city VARCHAR(20) NOT NULL,
state VARCHAR(20) NOT NULL,
country VARCHAR(20) NOT NULL,
pincode INT NOT NULL,
phone INT NOT NULL,
PRIMARY KEY(id)
) ENGINE=InnoDB;

CREATE TABLE internal_log (
activity VARCHAR(255) NOT NULL,
userid INT NOT NULL,
details VARCHAR(255),
timestamp TIMESTAMP NOT NULL,
FOREIGN KEY(userid) REFERENCES internal_user(id) ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE external_log (
activity VARCHAR(255) NOT NULL,
userid INT NOT NULL,
details VARCHAR(255),
timestamp TIMESTAMP NOT NULL,
FOREIGN KEY(userid) REFERENCES external_user(id) ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE internal_request (
id INT NOT NULL AUTO_INCREMENT,
requesterid INT NOT NULL,
request_type VARCHAR(20) NOT NULL,
current_value VARCHAR(50) NOT NULL,
requested_value VARCHAR(50) NOT NULL,
status VARCHAR(20) NOT NULL,
approver VARCHAR(50) NOT NULL,
description VARCHAR(50),
timestamp_created TIMESTAMP NOT NULL,
timestamp_updated TIMESTAMP NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(requesterid) REFERENCES internal_user(id) ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE external_request (
id INT NOT NULL AUTO_INCREMENT,
requesterid INT NOT NULL,
request_type VARCHAR(20) NOT NULL,
current_value VARCHAR(50) NOT NULL,
requested_value VARCHAR(50) NOT NULL,
status VARCHAR(20) NOT NULL,
approver VARCHAR(50) NOT NULL,
description VARCHAR(50),
timestamp_created TIMESTAMP NOT NULL,
timestamp_updated TIMESTAMP NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(requesterid) REFERENCES external_user(id) ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE account (
id INT NOT NULL AUTO_INCREMENT,
PRIMARY KEY(id)
) ENGINE=InnoDB;

CREATE TABLE transaction (
id INT NOT NULL AUTO_INCREMENT,
payer_id INT NOT NULL,
payee_id INT NOT NULL,
amount DECIMAL NOT NULL,
hashvalue VARCHAR(255) NOT NULL,
transaction_type VARCHAR(20) NOT NULL,
description VARCHAR(255),
status varchar(20) NOT NULL,
approver VARCHAR(50) NOT NULL,
critical BOOLEAN NOT NULL,
timestamp_created TIMESTAMP NOT NULL,
timestamp_updated TIMESTAMP NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(payer_id) REFERENCES account(id)  ON UPDATE CASCADE,
FOREIGN KEY(payee_id) REFERENCES account(id)  ON UPDATE CASCADE
) ENGINE=InnoDB;


CREATE TABLE checkings_accounts (
    id int NOT NULL,
    external_users_id int NOT NULL,
    checkings_account_no int NOT NULL,
    checkings_card_no int NOT NULL,
    balance double, 
    CONSTRAINT checkings_accounts_pk PRIMARY KEY (id)
);

-- Table: credit_card_account
CREATE TABLE credit_card_account (
    id int NOT NULL,
    external_users_id int NOT NULL,
    interest int NOT NULL,
    credit_card_no int NULL,
    CONSTRAINT credit_card_account_pk PRIMARY KEY (id)
);

-- Table: external_users
CREATE TABLE external_users (
    id INT(10) NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    designation VARCHAR(15) NOT NULL,
    address VARCHAR(50) NOT NULL,
    city VARCHAR(20) NOT NULL,
    state VARCHAR(20) NOT NULL,
    country VARCHAR(20) NOT NULL,
    pincode INT(10) NOT NULL,
    phone INT(20) NOT NULL,
    CONSTRAINT external_users_pk PRIMARY KEY (id)
);

-- Table: savings_accounts
CREATE TABLE savings_accounts (
    id int NOT NULL,
    external_users_id int NOT NULL,
    savings_acc_no int NOT NULL,
    savings_card_no int NOT NULL,
    balance double,
    CONSTRAINT savings_accounts_pk PRIMARY KEY (id)
);

-- Table: transaction
CREATE TABLE transaction (
    id int NOT NULL,
    amount int NOT NULL,
    timeStamp int NOT NULL,
    payee int NOT NULL,
    owner int NOT NULL,
    payment_type int NOT NULL,
    Description varchar(255) NOT NULL,
    CONSTRAINT id PRIMARY KEY (id)
);

-- foreign keys
-- Reference: checkings_accounts_external_users (table: checkings_accounts)
ALTER TABLE checkings_accounts ADD CONSTRAINT checkings_accounts_external_users FOREIGN KEY checkings_accounts_external_users (external_users_id)
    REFERENCES external_users (id);

-- Reference: credit_card_account_external_users (table: credit_card_account)
ALTER TABLE credit_card_account ADD CONSTRAINT credit_card_account_external_users FOREIGN KEY credit_card_account_external_users (external_users_id)
    REFERENCES external_users (id);

-- Reference: savings_accounts_external_users (table: savings_accounts)
ALTER TABLE savings_accounts ADD CONSTRAINT savings_accounts_external_users FOREIGN KEY savings_accounts_external_users (external_users_id)
    REFERENCES external_users (id);

-- Reference: transaction_external_users (table: transaction)
ALTER TABLE transaction ADD CONSTRAINT transaction_external_users FOREIGN KEY transaction_external_users (payee)
    REFERENCES external_users (id);

-- End of file.

-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2016-10-15 01:03:16.102

-- tables
-- Table: External_Logger
CREATE TABLE External_Logger (
    id int NOT NULL,
    action varchar(255) NOT NULL,
    user_id int NOT NULL,
    timestamp date NOT NULL,
    CONSTRAINT id PRIMARY KEY (id)
);

-- Table: bank_accounts
CREATE TABLE bank_accounts (
    id int NOT NULL,
    external_users_id int NOT NULL,
    account_type varchar(255) NOT NULL,
    card_number varchar(255) NOT NULL,
    balance int NOT NULL,
    CONSTRAINT bank_accounts_pk PRIMARY KEY (id)
);

-- Table: completed_transactions
CREATE TABLE completed_transactions (
    id int NOT NULL,
    amount int NOT NULL,
    timeStamp int NOT NULL,
    payee_id int NOT NULL,
    payment_type int NOT NULL,
    Description varchar(255) NOT NULL,
    payer_id int NOT NULL,
    initiator_id int NOT NULL,
    CONSTRAINT id PRIMARY KEY (id)
);

-- Table: credit_card_account_details
CREATE TABLE credit_card_account_details (
    id int NOT NULL,
    interest int NOT NULL,
    credit_card_no int NULL,
    available_balance int NOT NULL,
    last_bill_amount int NOT NULL,
    due_date date NOT NULL,
    apr float NOT NULL,
    bank_accounts_id int NOT NULL,
    CONSTRAINT credit_card_account_details_pk PRIMARY KEY (id)
);

-- Table: external_completed_requests
CREATE TABLE external_completed_requests (
    id int NOT NULL,
    request_type int NOT NULL,
    requestor_id int NOT NULL,
    previous_value varchar(255) NOT NULL,
    new_value varchar(255) NOT NULL,
    approver_type char NOT NULL,
    external_users_id int NOT NULL,
    CONSTRAINT id PRIMARY KEY (id)
);

-- Table: external_login
CREATE TABLE external_login (
    user varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    external_users_id int NOT NULL,
    CONSTRAINT external_login_pk PRIMARY KEY (external_users_id)
);

-- Table: external_pending_requests
CREATE TABLE external_pending_requests (
    id int NOT NULL,
    request_type int NOT NULL,
    requestor_id int NOT NULL,
    previous_value varchar(255) NOT NULL,
    new_value varchar(255) NOT NULL,
    approver_type char NOT NULL,
    external_users_id int NOT NULL,
    CONSTRAINT id PRIMARY KEY (id)
);

-- Table: external_users
CREATE TABLE external_users (
    id int NOT NULL,
    full_name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    CONSTRAINT external_users_pk PRIMARY KEY (id)
);

-- Table: merchant_payment
CREATE TABLE merchant_payment (
    id int NOT NULL,
    amount int NOT NULL,
    timeStamp int NOT NULL,
    payer_id int NOT NULL,
    token int NOT NULL,
    Description varchar(255) NOT NULL,
    status int NOT NULL,
    merchant_id int NOT NULL,
    CONSTRAINT id PRIMARY KEY (id)
);

-- Table: pending_transactions
CREATE TABLE pending_transactions (
    id int NOT NULL,
    amount int NOT NULL,
    timeStamp int NOT NULL,
    payee_account_number int NOT NULL,
    payment_type varchar(255) NOT NULL,
    Description varchar(255) NOT NULL,
    payer_account_number int NOT NULL,
    initiator_id int NOT NULL,
    CONSTRAINT id PRIMARY KEY (id)
);

-- Table: pii_info
CREATE TABLE pii_info (
    id int NOT NULL,
    external_users_id int NOT NULL,
    date_of_birth int NOT NULL,
    ssn_number int NOT NULL,
    CONSTRAINT pii_info_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: credit_card_account_details_bank_accounts (table: credit_card_account_details)
ALTER TABLE credit_card_account_details ADD CONSTRAINT credit_card_account_details_bank_accounts FOREIGN KEY credit_card_account_details_bank_accounts (bank_accounts_id)
    REFERENCES bank_accounts (id);

-- Reference: external_completed_requests_external_users (table: external_completed_requests)
ALTER TABLE external_completed_requests ADD CONSTRAINT external_completed_requests_external_users FOREIGN KEY external_completed_requests_external_users (external_users_id)
    REFERENCES external_users (id);

-- Reference: external_login_external_users (table: external_login)
ALTER TABLE external_login ADD CONSTRAINT external_login_external_users FOREIGN KEY external_login_external_users (external_users_id)
    REFERENCES external_users (id);

-- Reference: pending_requests_external_users (table: external_pending_requests)
ALTER TABLE external_pending_requests ADD CONSTRAINT pending_requests_external_users FOREIGN KEY pending_requests_external_users (external_users_id)
    REFERENCES external_users (id);

-- Reference: pii_info_external_users (table: pii_info)
ALTER TABLE pii_info ADD CONSTRAINT pii_info_external_users FOREIGN KEY pii_info_external_users (external_users_id)
    REFERENCES external_users (id);

-- Reference: savings_accounts_external_users (table: bank_accounts)
ALTER TABLE bank_accounts ADD CONSTRAINT savings_accounts_external_users FOREIGN KEY savings_accounts_external_users (external_users_id)
    REFERENCES external_users (id);

-- Reference: transation_external_users (table: pending_transactions)
ALTER TABLE pending_transactions ADD CONSTRAINT transation_external_users FOREIGN KEY transation_external_users (payee_account_number)
    REFERENCES external_users (id);

-- End of file.