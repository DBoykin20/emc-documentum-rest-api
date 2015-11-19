
!!! IMPORTANT NOTICE !!!
This Visual Studio solution contains a reference implementation of Documentum REST Services client written in C# code. 
The purpose of this project is to demonstrate one way to develop a hypermedia driven REST client to consume Documentum 
REST Services. It does NOT indicate that users could not develop a REST client using other technologies. EMC shares the 
source code of this project for the technology sharing. EMC does not make guarantees to maintain this sample project or 
add new functions to it in a committed time line. If users plan to migrate the sample code to their products, they are 
responsible to maintain this part of the code in their products and should agree with license polices of the referenced 
libraries used by this sample project.


##### Brief Project Introduction #####
This Documentum REST Services client is written with C# code. Underlying it leverages System.Net.Http.HttpClient to send the HTTP messages, leverages .NET DataContract serialization to bind the data model classes to JSON representations. It provides two serializer implementations, one is the .NET default System.Runtime.Serialization.Json.DataContractJsonSerializer, and the other is Newtonsoft.Json.JsonSerializer. The solution is divided into two sub projects:
    - DctmRestHttpClient
      It implements a Documentum REST client following the hypermedia driven style;
      It builts out the client library "Emc.Documentum.Rest.Sample.Client.dll".
    - ClientDemo
      It references the client library to consume REST resources;
      It contains a number of demo samples regarding repository, folder, document and son on;
      It builds out the console application "Emc.Documentum.Rest.Sample.DemoConsole.exe".


##### System Requirements ######
1) Documentum REST Services 7.0 or 7.1 is deployed in the development environment;
2) .NET framework 4.5 or later is installed in the development envrionment;
3) Visual Studio 2013 is installed in the development envrionment;
4) Download json.net library 4.5 or later version from http://json.codeplex.com/ and put Newtonsoft.Json.dll under folder /Libs/Json.Net/Bin/Net45/.


##### How To Use ######
1) Open the solution in Visual Studio 2013 IDE;
2) Make the sub project "ClientDemo" as the "Startup" project;
3) Run the project.
4) After the program is started, below commands will be shown on the console:

	$$$$ Demo for the Documentum REST .NET Client Reference Implementation$$$$

	Set the home document URL [http://localhost:8080/dctm-rest/services] :
	Set the username [dmadmin] :
	Set the user password [password] :
	Set the repository name [acme] :
	Whether to print the result [true] :

	Please choose the demo instance to run:
	  1 ----- Repository Demo
	  2 ----- User Home Cabinet Demo
	  3 ----- Folder Demo
	  4 ----- Document Demo
	  5 ----- Document Content Demo
	  6 ----- Document Version Demo
	  7 ----- Document Copy Move Link Demo
	  8 ----- DQL Query Demo
	  a ----- All
	  c ----- Clear the console
	  x ----- Exit the demo

	Command >
Please follow the commands to run the test demo.

