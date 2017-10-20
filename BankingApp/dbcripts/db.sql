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