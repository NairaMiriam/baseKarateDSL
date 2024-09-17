<h1> Testing - Karate DSL</h1> 

<h3>Descripción del Proyecto de automatización</h3>

Este proyecto permite utilizar los metodos nativos del Framework de Karate.

Para la ejecución via **Maven** se debe realizar lo siguiente:

```mvn 
mvn clean test -Dkarate.env=cert "-Dkarate.options=--tags @OAUTH" 
``` 
En el caso necesites usar data sensible como credenciales de autenticación de API's, esta debe ser enviada por linea de comandos:
```mvn 
mvn clean test -Dkarate.env=dev "-Dkarate.options=--tags @OAUTH" -Dclient-id=xxxxxx -Dsecret=yyyyyy 
``` 

## Integración con JIRA XRAY

Agregar el siguiente código para actualizar el estado de los Test en
el TestExecution de Jxray

Configuracion automation.properties (true|false)

```  
jxray.update.evidence=false 
``` 

En la clase LabsTest debe llamarse el siguiente metodo:

``` Java 
import jiraxray.api.AccesJiraXray; 
  
@AfterAll 
 static void afterAll() { 
 logger.info("AFTER >>>>>>>>>>>"); 
 AccesJiraXray.saveResultsJiraXray(); 
 } 
```