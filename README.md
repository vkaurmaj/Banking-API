# \textcolor{brown}{Banking API}
Banking endpoints written in Java using [Spring](https://spring.io/), [Maven](https://maven.apache.org/), and a [Tomcat](https://tomcat.apache.org/) HTTP server.

The Banking API manages the bank accounts of its users. It will be managed by the Bank's employees and admins. Employees and Admins count as Standard users with additional abilities.
* Employees can view all customer information, but not modify in any way.
* Admins can both view all user information, as well as directly modify it.
* Standard users should be able to register and login to see their account information.They can have either Checking or Savings accounts.
* All users must be able to update their personal information, such as username, password, first and last names, as well as email.
* Accounts owned by users must support withdrawal, deposit, and transfer.
* Transfer of funds should be allowed between accounts owned by the same user, as well as between accounts owned by different users.

## Models

### User

The User model keeps track of and the formatting of a user's information. The User model contains the Role model as well, which is used to
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

public class Role {
  private Integer roleID;
  private String role;
}
```
### Account

The Account model keeps track of and the formatting of an account's information. The Account model contains the AccountStatus and A

