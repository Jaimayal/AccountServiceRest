> *You can check the english docs [here](./README.md)*

### Descripcion
Servicio de cuentas construido siguiendo los principios de una REST API + Autenticacion + Autorizacion + Persistencia.

### Especificacion
Endpoints que provee este servicio:
- Autenticacion
    1. POST api/auth/signup permite el registro de un usuario.
    2. POST api/auth/changepass cambio de contraseña.

- Funcionalidad del Servicio
    1. PUT api/admin/user/role cambiar los roles de un usuario.
    2. DELETE api/admin/user eliminar un usuario
    3. GET api/admin/user mostrar informacion acerca de todos los usuarios.

Puedes probarlos todos importando [esta coleccion de Postman]().

Mira mas detalles sobre [como importar una coleccion a Postman aqui](https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-data-into-postman)

### Tecnologias
- Java 11
- Maven
- Spring Boot
- Spring Data
- Spring Security
- MySQL