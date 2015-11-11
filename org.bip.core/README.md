# CF Sample Project

This is a sample project using a servicemix oriented platform

To deploy to a local CF while building, use Maven profile `deploy`:
 
```
mvn clean install -Pdeploy -Ddeploy.dir=<cf-path>
```