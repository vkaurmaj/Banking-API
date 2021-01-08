# Banking API
Banking endpoints written in Java 8 using [Spring](https://spring.io/), [Maven](https://maven.apache.org/), JDBC w/ [MySQL](https://dev.mysql.com/downloads/mysql/) and a [Tomcat](https://tomcat.apache.org/) HTTP server.

The Banking API manages the bank accounts of its users. It will be managed by the Bank's employees and admins. Employees and Admins count as Standard users with additional abilities.
* Employees can view all customer information, but not modify in any way.
* Admins can both view all user information, as well as directly modify it.
* Standard users should be able to register and login to see their account information.They can have either Checking or Savings accounts.
* All users must be able to update their personal information, such as username, password, first and last names, as well as email.
* Accounts owned by users must support withdrawal, deposit, and transfer.
* Transfer of funds should be allowed between accounts owned by the same user, as well as between accounts owned by different users.

## Models

### User

The __User__ model keeps track of and the formatting of a user's information. The User model contains the __Role__ model as well, which is used to
keep track of the user's role: __Administrator__, __Employee__, __Premium__, and __Standard__.

```java
public class User {
  private Integer userID;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private Role role;
}
```

Role objects are built using these four options:
  1. Administrator
  2. Employee
  3. Premium
  4. Standard

```java
public class Role {
  private Integer roleID;
  private String role;
}
```
### Account

The __Account__ model keeps track of and the formatting of an account's information. The Account model contains the __AccountStatus__ and 
__AccountType__, which are used to keep track of the account's status ( __Pending__, __Open__, __Closed__, __Denied__) and type (__Checking__, __Savings__)
respectively.

```java
public class Account {
    private Integer accountID;
    private double balance;
    private AccountStatus status;
    private AccountType type;
}
```

Status objects are built using these four options:
  1. Pending
  2. Open
  3. Closed
  4. Denied

```java
public class AccountStatus {
    private Integer statusID;
    private String status;
}
```
Type objects are built using these two options:
  1. Checking
  2. Savings
  
```java
public class AccountType {
    private Integer typeID;
    private String type;
}
```
## Running

* __Step 1__: Initialize and fill in the required SQL database using the scripts provided.
* __Step 2__: Using your favorite flavor of IDE, edit the __application.properties__ file;
              edit the __url__, __username__, and __password__ fields accordingly for your datasource.
* __Step 3__: Initialize the HTTP server and run the API

From here, you are open to send requests to the API using the proper URIs. Examples of these URIs are 
contained within the provided collection of tests.

## Testing

** The Banking API must be running for tests to be successful **

In order to run the test collection, you will need to have [Postman](https://www.postman.com/downloads/) and import 
the __.json__ file by going to __File__-> __Import__.

Additionally, you will need to ensure that the environmental variable for __base_url__ is set correctly:
 * base_url | http://{your-hostname}:{your-port}/bankingAPI_Spring
