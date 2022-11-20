Indicaciones para correr el proyecto:

1. Para inciar las bases de datos (Postgress y Mongo) y Keycloak:  

`docker-compose up ` 

- Hay dos archivos docker-compose, pero el docker-compose1.yml no funciona. Hice pruebas y no logré hacer que se registren los microservicios en Eureka

2. En el siguiente orden ejecutar:
   1. eureka-server
   2. gateway-api
   3. Luego los demás microservicios:
      1. movies-api
      2. ms-bills
      3. users-service

3. Una vez que todos los microservicios están activos ingresar a las siguientes URLs para continuar:

- Para importar el Reino:

http://localhost:8080/admin/master/console/#/realms/DigitalMedia/partial-import

Subiendo el archivo realm-export.json que se encuentra en la raíz del proyecto

- Verificar que los clientId estén actualizados
- Los usuarios y contraseñas son:
  - Para el reino: 
    - user: admin
    - password: admin
  - Usuario Administrador
    - user: admin
    - password: 1234
  - Usuario cliente
    - user: client
    - password: 1234
  - Usuario proveedor
    - user: provider
    - password: 1234
    
- Para probar si funciona el gateway:

http://localhost:8090/actuator/metrics

- Para probar el endpoint de movies:

http://localhost:8090/api/movies/movies


**Aclaraciones**
- No realicé las configuraciones del microservicio bills
- Las configuraciones de seguridad están en todos los microservicios a pesar de que no logré que todos los microservicios funcionen como esperaba
- Las configuraciones en Keycloak están realizadas a pesar de que no pude probar todo lo que hubiera deseado