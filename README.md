> *Puedes consultar la documentacion en español [aqui]()*

### Description
Account Service following the principles of a REST API + Authentication + Authorization + Persistence.

### Specification
Available endpoints:
- Authentication 
  1. POST api/auth/signup allows the user to register on the service.
  2. POST api/auth/changepass changes a user password.

- Service functionality 
  1. PUT api/admin/user/role changes user roles.
  2. DELETE api/admin/user deletes a user.
  3. GET api/admin/user displays information about all users.

Test all this endpoints on your own machine using [this postman collection]().

You can find details on [how to import a Postman collection here](https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-data-into-postman)

### Technologies
- Java 11
- Maven
- Spring Boot
- Spring Data
- Spring Security
- MySQL