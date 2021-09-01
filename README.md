# SafetyNet-Alerts
An API allowing the management and consultation of information relating to the inhabitants of an area.
This API works with Java Spring and stores data in H2.

## Composing

### DataBase
The H2 database is temporary, a `DataBaseInit` method is used to initialize it at launch using the` data.Json` file included in the API.

We can make H2 persistent by changing in **`application.properties`**: *`spring.datasource.url=jdbc:h2:mem:alerts`* by *`spring.datasource.url=jdbc:h2:file:alerts/db`*. 
It will then be necessary to modify the initialization so that it is launched only in an empty database for example.

### API
The API in addition to the management of inhabitants (post, put, delete) and to consult the information concerning this management, has 7 other URLs allowing access to precise information.

1. `/firestation?stationNumber=<station_number>`
  * The list of **people covered by the fire station** with information: *first name*, *last name*, *address*, *telephone number*, as well as a *count of adults and children*.
  * The *number of the fire station* must be given.

2. `/childAlert?address=<address>`
  * List of **children living at this address**, as well as information: *first name*, *last name*, *age*. If there are **adults in the household**, their *first and last name* is required, but if there are **no children** the list will *be empty*.
  * The *address* must be given.
  
3. `/phoneAlert?firestation=<firestation_number>`
  * List of ***telephone numbers* of residents served by the fire station**.
  * The *number of the fire station* must be given.
 
4. `http://localhost:8080/fire?address=<address>`
  * List of **inhabitants living at the given address** as well as the **fire station serving it**, the information of the inhabitants must be: *first name*, *last name*, *telephone number*, *age*, *medical history*.
  * The *address* must be given.
  
5. `http://localhost:8080/flood/stations?stations=<a list of station_numbers>`
  * List of **inhabitants by address served by a fire station**, the information of the inhabitants must be: *first name*, *last name*, *telephone number*, *age*, *medical history*.
  * The *number of the fire station* must be given.
  
6. `http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>`
  * The information of **the inhabitant**: *first name*, *last name*, *address*, *age*, *email address*, *medical history*.
  * The *first and last name* must be given.
  
7. `http://localhost:8080/communityEmail?city=<city>`
  * The list of ***email addresses* of all the inhabitants of the city**.
  * The *city* must be given.

All *EndPoints* and *Urls* can be seen and remain in **Swagger2**: `http://localhost:8080/swagger-ui.html`.

## Launch
API uses spring boot, use the following command to start it:
`./mvnw.cmd spring-boot:run`

## Testing
This API has Unit and Integrations tests written.
It is possible to have access to Surefire Report and Jacoco to visualize the execution time and the coverage of the tests following this path:
`SafetyNet/target/site/project-reports.html`

After using the following command in the terminal:
`mvn clean verify site`
