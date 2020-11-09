create schema banking;

create table banking.user_roles (
	roleID int auto_increment,
    role varchar(32) unique not null,
    primary key(roleID)
);

create table banking.users (
	userID int auto_increment,
    username varchar(16) unique not null,
    pword varchar(64) not null,
    fname varchar(16) not null,
    lname varchar(24) not null,
    email varchar(48) not null,
    roleID int,
    primary key(userID),
    foreign key(roleID) references user_roles(roleID)
);

create table banking.acc_statuses (
	statusID int auto_increment,
    status_name varchar(32) unique not null,
    primary key(statusID)
);

create table banking.acc_types(
	typeID int auto_increment,
    type_name varchar(32) unique not null,
    primary key(typeID)
);

create table banking.accounts(
	accID int auto_increment,
    balance double not null,
    statusID int,
    typeID int,
	primary key(accID),
    foreign key(statusID) references acc_statuses(statusID),
    foreign key(typeID) references acc_types(typeID)
);

create table banking.user_accounts(
	userID int not null,
    accID int not null,
    foreign key (userID) references users(userID)
		on delete restrict,
	foreign key (accID) references accounts(accID)
		on delete cascade
);
