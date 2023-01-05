# Spring Boot 3 JWT implementation

<br>

## Project feaures:
1. JWT generation
2. Refresh token generation
3. Create/read Users
4. Create/read Roles
5. Attach Roles to Users
    *    Many users ↔ Many roles
6. Postman collections with default endpoints and examples of each ones

> Note: The revoke token feature is not implemented yet.

<br>

---
<br>

## Requirements to run and test:

1. MariaDB database, the name must be `demojwt`, user: `root` and pass: `Password1.a12`. 
    *   You can modify this properties in the `src/main/resources/application.properties` file.
    *   If you have docker and docker compose, check the `utils/mariadb/docker-compose.yml` file. Check docs [here](https://docs.docker.com/compose/gettingstarted/#step-3-define-services-in-a-compose-file).

<br>

2. Once the database is created, open a terminal (or powershell) on the root project (where is the pom.xml) and execute: `./mvnw spring-boot:run`.

    *   The first startup, one user is created with `admin@admin.com` email, `123456` password, and `ROLE_ADMIN` role attached. This user can be used to authenticate and perform any API operation (you can check the postman collection).

<br>

3. You can import the `utils/JWT.postman_collection.json` file in postman (as a collection) and test each endpoint.

<br>

--- 

<br>

## TODO:

Currently the refresh token is not stored and cannot be revoked, perhaps this may be a security issue, this is likely to be added to the project in the future. You are also welcome to suggest changes or contribute to the project.

> Greetings : )
