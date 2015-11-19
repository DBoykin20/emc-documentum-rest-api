
!!! IMPORTANT NOTICE !!!
This Java project contains a reference implementation of Documentum REST Services client written in Java code.
The purpose of this project is to demonstrate one way to develop a hypermedia driven REST client to consume Documentum
REST Services. It does NOT indicate that users could not develop a REST client using other technologies. EMC shares the
source code of this project for the technology sharing. EMC does not make guarantees to maintain this sample project or
add new functions to it in a committed time line. If users plan to migrate the sample code to their products, they are
responsible to maintain this part of the code in their products and should agree with license polices of the referenced
libraries used by this sample project.


##### Brief Project Introduction #####
This Documentum REST Services client is written with Java code.
Underlying it leverages Spring RestTemplate to send the REST messages.
It supports both XML and Json representations.
JAXB is used to bind XML object, and Jackson for Json.

DCTMRestClientSample is the main class to be executed with some sample operations.

##### System Requirements ######
1) Documentum REST Services 7.0 or 7.1 is deployed in the development environment
2) Java 6 or later is installed
3) Maven 3 is required to build the project

##### How To Use ######
1) Build the project with maven
   mvn clean install

2) There will be several files built in target directory:
   rest-api-client-sample-7.1.jar -> the sample client jar file
   lib -> the lib directory contains all other required third party library
   rest-api-client-sample-7.1-all-in-one.jar -> the sample client jar file, with all other third party classes embedded
   rest-api-client-sample-7.1-javadoc.jar -> javadoc of the sample client
   rest-api-client-sample-7.1-sources.jar -> source code of the sample client
   
3) Run the project
   
   a) execute rest-api-client-sample-7.1.jar, with lib directory at the same place
   java -jar rest-api-client-sample-7.1.jar
   
   b) or execute rest-api-client-sample-7.1-all-in-one.jar directly
   java -jar rest-api-client-sample-7.1-all-in-one.jar
   
4) After the program is started, please input the required information prompted by the sample client.
   After that, you will see a list of commands:

   Please input the number of the sample operation which need be executed:
   0 Exit
   1 Navigation
   2 Folder Create/Update/Delete
   3 Sysobject Create/Update/Delete
   4 Document Create/Update/Delete
   5 Content Management
   6 Version Management
   7 DQL Query

   Then input the number of the sample you want to execute.
