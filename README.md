# SafetyNet-Alerts
An API allowing the management and consultation of information relating to the inhabitants of an area.
This API works with Java Spring and stores data in H2.

## Composing

### DataBase
The H2 database is temporary, a `DataBaseInit` method is used to initialize it at launch using the` data.Json` file included in the API.

We can make H2 persistent by changing in **`application.properties`**: *`spring.datasource.url=jdbc:h2:mem:alerts`* by *`spring.datasource.url=jdbc:h2:file:alerts/db`*. 
It will then be necessary to modify the initialization so that it is launched only in an empty database for example.

### API

## Launch
API uses spring boot, use the following command to start it:
`./mvnw.cmd spring-boot:run`

## Testing
This API has Unit and Integrations tests written.
It is possible to have access to Surefire Report and Jacoco to visualize the execution time and the coverage of the tests following this path:
`SafetyNet/target/site/project-reports.html`

After using the following command in the terminal:
`mvn clean verify site`
