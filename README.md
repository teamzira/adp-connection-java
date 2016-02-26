
## ADP Client Connection Library for Java
The ADP Client Connection Library is intended to simplify and aid the process of authenticating, authorizing and connecting to the ADP Marketplace API Gateway. The Library includes couple of sample applications that can be run out-of-the-box to connect to the ADP Marketplace API **test** gateway.

There are two ways of installing and using this library:

  - Clone the repo from Github: This allows you to access the raw source code of the library as well as provides the ability to run the sample application and view the Library documentation
  - Maven dependency: When you are ready to use the library in your own application use this method to add as Maven dependency

### Version
1.0.0

### Installation

**Clone from Github**

You can either use the links on Github or the command line git instructions below to clone the repo.


$ git clone https://github.com/adplabs/adp-connection-java.git adp-connection-java

followed by either using command line or eclipse options to build the projects

$ cd adp-connection-java
$ mvn clean install -e

Import the library to your Eclipse IDE workspace -> find /client 
copy the client sample(s) to /workspace
unzip the sample application(s) 
Import sample application(s) to eclipse
    
Do a one time setup for client certificates 
 
NOTE: certs bundled in libraries will only work for ADP Sandbox environment
      Refer ONE-TIME_CERTS_SETUP.md for jks key generation and cacerts update 

## Usage
### Library Organization 
ADPConnection library  follows standard maven project structure

### Integrate Libraries
To integrate ADPConnection library to your Client Applications add ADPConnection.jar 
to your classpath and use the library API's.

Client Applications that use these libraries would have to acquire certificates from     
ADP by submitting CSR signing request.

NOTE: certs bundled in libraries will only work for ADP Sandbox environment
Refer ONE-TIME_CERTS_SETUP.md for jks key generation and how to import to cacerts


**How to Use ADPConnection Library?**

Initialize Configuration based on Grant type **client_credentials** or **authorization_code**
Create Connection with initialized configuration
Invoke connect() on Connection to get Access Token 
Invoke getAccessToken() on Connection to obtain Access Token 
Invoke getErrorResponse() on Connection to obtain error details in case of no Access Token
	

** Create Client Credentials Connection **

    // create an instance of ClientCredentialsConfiguration
    ClientCredentialsConfiguration connectionConfiguration = new ClientCredentialsConfiguration();
    
    // map properties
    Properties properties = ClientUtils.getInstance().getConfigProperties();
    ClientUtils.getInstance().mapPropertiesToClientCredentialsConfiguration(properties, connectionConfiguration);
   
    // create connection
    clientCredentialsConnection = (ClientCredentialsConnection) 
						ADPAPIConnectionFactory.getInstance().createConnection(connectionConfiguration);
						
	// set connection configuration object on connection
	clientCredentialsConnection.setConnectionConfiguration(connectionConfiguration);
	
	// invoke connect to acquire Access Token
	clientCredentialsConnection.connect();
	
	// at this time this connection must have token
	Token token = clientCredentialsConnection.getToken()
	
	// alternate flow - no token in connection
	String errorResponse  = clientCredentialsConnection.getErrorResponse()

** Create Authorization Code Connection**

	// create authorization code configuration object
	AuthorizationCodeConfiguration authorizationCodeConfiguration = new AuthorizationCodeConfiguration();
				
	// get authorization code configuration properties 
	Properties properties = com.adp.marketplace.demo.client.auth.authcode.utils.ClientUtils.getInstance().getConfigProperties();

	// populate authorization code configuration 
	com.adp.marketplace.demo.client.auth.authcode.utils.ClientUtils.getInstance()
		.mapPropertiesToAuthCodeConfiguration(properties,  authorizationCodeConfiguration);
				
	// get ADP connection using configuration above
	authorizationCodeConnection = (AuthorizationCodeConnection) ADPAPIConnectionFactory.getInstance().
		createConnection(authorizationCodeConfiguration); 			
		
	// get Url from authorization connection
	authorizationUrl = "redirect:" + authorizationCodeConnection.getAuthorizationUrl();

	// callback snippets
	String requestCode = (String) request.getParameter("code");
	String callBackRequestError = (String) request.getParameter("error");
	
	AuthorizationCodeConfiguration config = ((AuthorizationCodeConfiguration) 
	authorizationCodeConnection.getConnectionConfiguration());

	// call to ADPConnection Library
	authorizationCodeConnection.connect();
					
	// at this time this connection must have token info
	Token token = authorizationCodeConnection.getToken();

	// alternate flow - no token in connection 
	errorMessage = authorizationCodeConnection.getErrorResponse();


## Sample Client
Two sample clients are provided to demonstrate usage of the libraries.  The sample client connects to a sandbox environment hosted by ADP, and comes preconfigured with the necessary credentials and certificates to connect to the sandbox server.

AuthCodeConnectionSampleApp - Web Application - web app that uses ADPConnection library to obtain a connection based on grant type Authorization Code 
                                                 
ClientCredConnectionSampleApp - Stand Alone -  Provides sample app to use Connection based on grant type Client Credentials     

Unzip the sample application and copy to {Home}/workspace/ so that sample apps can be run as individual modules 

To setup, build and run the sample clients, please refer to README.md corresponding to AuthCodeConnectionSampleApp, ClientCredConnectionSampleApp

## API Documentation
Documentation on the individual API calls

Library Documentation
file://{HOME}/workspace/ADPConnection/doc/index.html
  
To generate the documentation, please complete the following steps:

### Generate Docs Eclipse Editor
Select ADPConnectionLibrary

Select Project from menu bar and click **Generate Javadoc**

A pop up window opens with few options 
**Configure JavaDoc Command** to point to your machines JAVA_HOME/Contents/Home/bin/ javadoc
Ex:	/Library/Java/JavaVirtualMachines/jdk1.8.0_51.jdk/Contents/Home/bin/javadoc
**Create Javadoc with members with visibility 
Select public
**Use standard Doculet**
Destination {HOME}/workspace/ADPConnection/doc	

Click Next
**Provide Document Title** ADPConnection API Docs
**Basic Options** Choose Default options

Click Next
**Configure Javadoc arguments** 

Check Overview -> Browse to point to
	{HOME}/ADPConnection/doc/index.html
Verify JRE source compatibility
Check Open generated index file in browser

Click Finish

Java Doc successfully generated in {HOME}/workspace/ADPConnection/doc	

**View Java Docs in a browser**
file://{HOME}/workspace/ADPConnection/doc/index.html

Additional documentation can also be found on the [ADP Developer Portal](https://developers.adp.com).

## Tests
Automated unit tests are available in the src/main/test folder.  

> mvn test

Eclipse 
	Right Click on pom.xml 
		Select Run As -> Maven Test

## Dependencies
ADPConnection library depends on the following libraries.

1.  commons-logging-1.2.jar
2.  commons-lang3-3.4.jar
3.  httpclient-4.5.1.jar
4.  httpcore-4.4.3.jar
5.  commons-codec-1.9.jar
6.  httpcore-osgi-4.4.4.jar
7.  httpcore-nio-4.4.4.jar
8.  gson-2.3.1.jar
9.  json-simple-1.1.1.jar
10. junit-4.12.jar
11. hamcrest-core-1.3.jar
12. slf4j-api-1.7.14.jar


## Contributing
To contribute to the library, please generate a pull request.  Before generating the pull request, please insure the following:

1. Appropriate unit tests have been updated or created.
2. Code coverage on unit tests must be no less than 95%.
2. Your code updates have been fully tested and linted with no errors.
3. Update README and API documentation as appropriate.

## License
[Apache 2](http://www.apache.org/licenses/LICENSE-2.0)