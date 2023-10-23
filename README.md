# NoteApp

- this application is meant to be simple and was created to demonstrate basic simplicity / complexity and capabilities of SpringBoot framework

- it was originally written:
    - as simple as possible to make it work
    - with minimum dependencies
    - for educational purposes

- purpose of the app can be changed in the future only by the author

## Dependencies included

- `spring-boot-starter-web` - preconfigured Spring for web, including REST, runs TomCat container

- `org.projectlombok:lombok` - service/logic
- `jakarta.validation-api` - service/logic

- `com.h2database:h2` - data
- `spring-boot-starter-data-jpa`- data

- `spring-boot-starter-thymeleaf`- view ("FrondEnd")

### Service layer

- `lombok` is used mainly to omit a need to write tons getters and setters and it's easy to understand
- `jakarta.validation` is used to validate inputs (e.g. to prevent creating title thousands lines long)
- Does NOT include `mapstract` for now (even-though mapstract reduces boiler code significantly, it does so by "hiding" the mapping logic from newcomers, and it can get into conflict with `lombok` requiring "bridge" to solve . Also `lombok` is easier to grabs on IMHO.)

### Data layer

- `Spring boot starter data jpa` -> allows app to communicate with database(s).
- `H2` includes in-memory database `H2` to simplify deploying at dev's local machine
- `H2` can be switched to persistent one (PostgreSQL, MySQL, ect) but it requires the database to be setup e.g. as service on local machine or on server

### View aka "FrontEnd"

- includes simple `thymeleaf` template made frontend for demonstrating and educational purposes. It allows to experiment and learn without building standalone front end.

### Testing

- `Sprint boot starter test` - used in testing
- `JUnit5` or other testing "tools" might be used sooner or later

## Disclaimer!

- this application:
    - is no way or shape meant as the (only) proper way to write Spring app.
    - Use at your own risk. Author or any possible contributors are not responsible for any kind of damage, security breakage or malfunction that may or may not arise from using any part of the app for any purpose.
    - It's NOT to be taken as finished product.
    - It is not meant for production use.
