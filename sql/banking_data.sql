# data for the acc_types table
INSERT INTO banking.acc_types (typeID, type_name) VALUES (1,'Checking');
INSERT INTO banking.acc_types (typeID, type_name) VALUES (2,'Savings');

# data for the acc_statuses table
INSERT INTO banking.acc_statuses (statusID, status_name) VALUES (1, 'Pending');
INSERT INTO banking.acc_statuses (statusID, status_name) VALUES (2, 'Open');
INSERT INTO banking.acc_statuses (statusID, status_name) VALUES (3, 'Closed');
INSERT INTO banking.acc_statuses (statusID, status_name) VALUES (4, 'Denied');

# data for the roles table
INSERT INTO banking.user_roles (roleID, role) VALUES (1, 'Administrator');
INSERT INTO banking.user_roles (roleID, role) VALUES (2, 'Employee');
INSERT INTO banking.user_roles (roleID, role) VALUES (3, 'Premium');
INSERT INTO banking.user_roles (roleID, role) VALUES (4, 'Standard');

# test data for the users table
INSERT INTO banking.users (userID, username, pword, fname, lname, email, roleID) VALUES ( 1,'bankadmin1', 'admin1', 'Bill', 'Willson', 'bw@gmail.com', 1);
INSERT INTO banking.users (userID, username, pword, fname, lname, email, roleID) VALUES ( 2,'bankempl1', 'empl1', 'Jill', 'Killson', 'jk@gmail.com', 2);
INSERT INTO banking.users (userID, username, pword, fname, lname, email, roleID) VALUES ( 3,'bankempl2', 'empl2', 'Will', 'Billson', 'wb@gmail.com', 2);
INSERT INTO banking.users (userID, username, pword, fname, lname, email, roleID) VALUES ( 4,'premuser1', 'prem1', 'Gary', 'Griggs', 'gg@gmail.com', 3);
INSERT INTO banking.users (userID, username, pword, fname, lname, email, roleID) VALUES ( 5,'premuser2', 'prem2', 'Jerry', 'Rig', 'jr@gmail.com', 3);
INSERT INTO banking.users (userID, username, pword, fname, lname, email, roleID) VALUES ( 6,'reguser1', 'reg1', 'Eric', 'Cherm', 'ec@gmail.com', 4);
INSERT INTO banking.users (userID, username, pword, fname, lname, email, roleID) VALUES ( 7,'reguser2', 'reg2', 'Arthur', 'Morgan', 'am@gmail.com', 4);
INSERT INTO banking.users (userID, username, pword, fname, lname, email, roleID) VALUES ( 8,'reguser3', 'reg3', 'Gerrick', 'Keke', 'gk@gmail.com', 4);
INSERT INTO banking.users (userID, username, pword, fname, lname, email, roleID) VALUES ( 9,'reguser4', 'reg4', 'Quincy', 'McQueen', 'qm@gmail.com', 4);

# test data for the accounts table
INSERT INTO banking.accounts (accID, balance, statusID, typeID) VALUES (1, 10354.43, 2, 1);
INSERT INTO banking.accounts (accID, balance, statusID, typeID) VALUES (2, 1234652.78, 2, 2);
INSERT INTO banking.accounts (accID, balance, statusID, typeID) VALUES (3, 50232.32, 2, 1);
INSERT INTO banking.accounts (accID, balance, statusID, typeID) VALUES (4, 2325.23, 2, 1);
INSERT INTO banking.accounts (accID, balance, statusID, typeID) VALUES (5, 10323.56, 2, 2);
INSERT INTO banking.accounts (accID, balance, statusID, typeID) VALUES (6, 324.23, 2, 1);
INSERT INTO banking.accounts (accID, balance, statusID, typeID) VALUES (7, 1234.23, 2, 2);
INSERT INTO banking.accounts (accID, balance, statusID, typeID) VALUES (8, 12.23, 2, 1);
INSERT INTO banking.accounts (accID, balance, statusID, typeID) VALUES (9, 1246.64, 2, 2);
INSERT INTO banking.accounts (accID, balance, statusID, typeID) VALUES (10, 0.00, 1, 1);
INSERT INTO banking.accounts (accID, balance, statusID, typeID) VALUES (11, 500000.00, 4, 2);
INSERT INTO banking.accounts (accID, balance, statusID, typeID) VALUES (12, 0.00, 3, 1);

#data for user_accounts table
INSERT INTO banking.user_accounts (userID, accID) VALUES (4, 1);
INSERT INTO banking.user_accounts (userID, accID) VALUES (4, 2);
INSERT INTO banking.user_accounts (userID, accID) VALUES (4, 3);
INSERT INTO banking.user_accounts (userID, accID) VALUES (5, 4);
INSERT INTO banking.user_accounts (userID, accID) VALUES (6, 5);
INSERT INTO banking.user_accounts (userID, accID) VALUES (6, 6);
INSERT INTO banking.user_accounts (userID, accID) VALUES (7, 7);
INSERT INTO banking.user_accounts (userID, accID) VALUES (7, 8);
INSERT INTO banking.user_accounts (userID, accID) VALUES (8, 9);
INSERT INTO banking.user_accounts (userID, accID) VALUES (8, 10);
INSERT INTO banking.user_accounts (userID, accID) VALUES (9, 11);
INSERT INTO banking.user_accounts (userID, accID) VALUES (5, 12);
