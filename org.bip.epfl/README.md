# BIP EPFL project

This project is simply to deploy some non Maven artefacts to your local Maven 
repository in order to be able to build org.bip.core project. 

Eventually one should deploy those artefacts within your artefact
repository manager (Nexus or Artifactory) and then there is no need to
execute those projects.
 
```
mvn clean install

```
