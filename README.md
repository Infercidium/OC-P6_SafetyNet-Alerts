# SafetyNet-Alerts
An API allowing the management and consultation of information relating to the inhabitants of an area.
This API works with Java Spring and stores data in H2.

## Composing

### DataBase
The H2 database is temporary, a `DataBaseInit` method allows it to be initialized at launch using the `data.Json` file included in the API.
No storage method has yet been set up but it is possible to be able to replace the `data.Json` file with a new file having the data from the updated database.

### API

## Launch


## Testing
This API has Unit and Integrations tests written.
It is possible to have access to Surefire Report and Jacoco to visualize the execution time and the coverage of the tests following this path:
`SafetyNet/target/site/project-reports.html`

After using the following command in the terminal:
`mvn clean verify site`
