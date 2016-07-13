# BIP Core Project

BEFORE you can build this project you need to build org.bip.epfl project in 
../org.bip.epfl directory.

This is the BIP core project that provides BIP executors, BIP API, BIP Glue, ...

To deploy to a local CF while building, use Maven profile `deploy`:
 
```
mvn clean install -Pdeploy -Ddeploy.dir=<cf-path>

```
