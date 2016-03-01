/*
	---------------------------------------------------------------------------
	Copyright © 2015-2016 ADP, LLC.   
	
	Licensed under the Apache License, Version 2.0 (the “License”); 
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software 
	distributed under the License is distributed on an “AS IS” BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
	implied.  See the License for the specific language governing 
	permissions and limitations under the License.
	---------------------------------------------------------------------------
*/
package com.adp.marketplace.connection.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.adp.marketplace.connection.configuration.AuthorizationCodeConfiguration;
import com.adp.marketplace.connection.configuration.ClientCredentialsConfiguration;
import com.adp.marketplace.connection.core.ADPAPIConnectionFactory;
import com.adp.marketplace.connection.core.AuthorizationCodeConnection;
import com.adp.marketplace.connection.core.ClientCredentialsConnection;
import com.adp.marketplace.connection.exception.ConnectionException;
import com.adp.marketplace.connection.vo.Token;

/**
 * @author tallaprs
 *
 */
public class ConnectionUtilsTest {
	
	ConnectionUtils instance = null;
	ADPAPIConnectionFactory connectFactoryInstance = null;
	AuthorizationCodeConfiguration authCodeConfiguration =  null;
	ClientCredentialsConfiguration clientCredentialsConfiguration =  null;
	
	/**
	 * @throws java.lang.Exception Exception thrown if this call fails
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception Exception thrown if this call fails
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception Exception thrown if this call fails
	 */
	@Before
	public void setUp() throws Exception {
		instance = ConnectionUtils.getInstance();
		connectFactoryInstance = ADPAPIConnectionFactory.getInstance();
		authCodeConfiguration =  new AuthorizationCodeConfiguration();
		clientCredentialsConfiguration = new ClientCredentialsConfiguration(); 
	}
	
	/**
	 * @throws java.lang.Exception Exception thrown if this call fails
	 */
	@After
	public void tearDown() throws Exception {
		instance = null;
		connectFactoryInstance = null;
		authCodeConfiguration =  null;
		clientCredentialsConfiguration =  null;
	}
	
	/**
	 * 
	 * verifies that a singleton instance is created for ConnectionUtils
	 */
	@Test
	public void singletonInstanceCreated() {
		
		ConnectionUtils anotherInstance = ConnectionUtils.getInstance();
		
		assertNotNull(instance);
		assertNotNull(anotherInstance);
		
		assertTrue(instance instanceof ConnectionUtils);
		assertTrue(anotherInstance instanceof ConnectionUtils);
		
		assertSame(instance, anotherInstance);
		assertEquals(instance, anotherInstance);
	}

	/**
	 * verifies ConnectionException is thrown in case authorization code configuration 
	 * has null or missing values foe baseAuthurl or clientID or redirectUrl or scope.
	 * 
	 */
	@Test
	public void buildAuthorizationUrlException() {
		
		AuthorizationCodeConfiguration authorizationCodeConfig = new AuthorizationCodeConfiguration();
		
		String baseAuthUrl = " ";
		String clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		String redirectUrl = "http://localhost:8889/marketplace/callback";
		String scope = "openid";
		
		authorizationCodeConfig.setBaseAuthorizationUrl(baseAuthUrl);
		authorizationCodeConfig.setClientID(clientID);
		authorizationCodeConfig.setRedirectUrl(redirectUrl);
		authorizationCodeConfig.setScope(scope);
		
		try {
			
			String authorizationUrl = instance.buildAuthorizationUrl(authorizationCodeConfig);
			
			assertTrue(false);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionException);
			assertTrue(e.getMessage().equals(" Failed to Build Authorization Url as required field is null or empty"));
		}
	}
	
	/**
	 * verifies authorization url is built 
	 */
	@Test
	public void buildAuthorizationUrl() {
		
		AuthorizationCodeConfiguration authorizationCodeConfig = new AuthorizationCodeConfiguration();
		
		String baseAuthUrl = "https://iat-accounts.adp.com/auth/oauth/v2/authorize";
		String clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		String redirectUrl = "http://localhost:8889/marketplace/callback";
		String scope = "openid";
		
		authorizationCodeConfig.setBaseAuthorizationUrl(baseAuthUrl);
		authorizationCodeConfig.setClientID(clientID);
		authorizationCodeConfig.setRedirectUrl(redirectUrl);
		authorizationCodeConfig.setScope(scope);
		
		try {
			String authorizationUrl = instance.buildAuthorizationUrl(authorizationCodeConfig);
			
			assertNotNull(authorizationUrl);
			
			assertTrue(authorizationUrl.contains("client_id"));
			assertTrue(authorizationUrl.contains("response_type"));
			assertTrue(authorizationUrl.contains("redirect_uri"));
			
			assertTrue(authorizationUrl.contains("scope"));
			assertTrue(authorizationUrl.contains("state"));
			
		} catch (ConnectionException e) {
			assertTrue(false);
		}
	}
	
	/**
	 * verifies if null AuthorizationCodeConfiguration passed ConnectionException
	 * with appropriate message is thrown 
	 */
	@Test
	public void getClientCredentialsForAuthCodeConfigException() {
		
		AuthorizationCodeConfiguration authorizationCodeConfig = null;
		
		try {
			
			List<NameValuePair> nameValuePair = instance.getClientCredentials(authorizationCodeConfig);
			
		} catch( Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionException);
			assertTrue(e.getMessage().contains("Connection Configuration is Not Set in request!!"));
		}
	}
	
	/**
	 * verifies getClientCredentials for authorization code configuration is successfull and returns 
	 * mapped name value pairs
	 */
	@Test
	public void getClientCredentialsForAuthorizationCodeConfig() {
		
		AuthorizationCodeConfiguration authorizationCodeConfig = new AuthorizationCodeConfiguration();
		
		String baseAuthUrl = "https://iat-accounts.adp.com/auth/oauth/v2/authorize";
		String clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		String redirectUrl = "http://localhost:8889/marketplace/callback";
		String scope = "openid";
		
		authorizationCodeConfig.setBaseAuthorizationUrl(baseAuthUrl);
		authorizationCodeConfig.setClientID(clientID);
		authorizationCodeConfig.setRedirectUrl(redirectUrl);
		authorizationCodeConfig.setScope(scope);
		
		try {
			List<NameValuePair> nameValuePairs = instance.getClientCredentials(authorizationCodeConfig);
			
			assertNotNull(nameValuePairs);
			assertTrue(nameValuePairs.size() >= 3);
			
			for ( NameValuePair nameValuePair : nameValuePairs ) {
				 String name = nameValuePair.getName();
				 String value = nameValuePair.getValue();
				 
				 assertNotNull(name);
				 assertNotNull(value);
				 
				 assertTrue(name.contains("grant_type") ||
						 name.contains("client_id") ||
						 name.contains("client_secret") );
				 if (  name.contains("grant_type") ) {
					 assertTrue(value.equals("authorization_code") );
				 }
			}
			
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * verifies if null ClientCredentialsConfiguration passed ConnectionException
	 * with appropriate message is thrown 
	 */
	@Test
	public void getClientCredentialsForClientCredentialsConfigException() {
		
		ClientCredentialsConfiguration clientCredentialsConfiguration = null;
		
		try {
			
			List<NameValuePair> nameValuePair = instance.getClientCredentials(clientCredentialsConfiguration);
			
		} catch( Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionException);
			assertTrue(e.getMessage().contains("Connection Configuration is Not Set in request!!"));
		}
		
	}
	
	/**
	 * verifies if ClientCredentialsConfiguration passed and name value pairs are generated
	 */
	@Test
	public void getClientCredentialsForClientCredentialsConfig() {
		
		ClientCredentialsConfiguration clientCredentialsConfiguration = new ClientCredentialsConfiguration();
		
		try {
			
			clientCredentialsConfiguration.setClientID("5cab3a80-b3fd-415f-955f-4f868596ff43");
			clientCredentialsConfiguration.setClientSecret("4a26db08-2885-4766-b6bb-ad8d0eac7c22");
			
			List<NameValuePair> nameValuePairs = instance.getClientCredentials(clientCredentialsConfiguration);
			
			assertNotNull(nameValuePairs);
			assertTrue( nameValuePairs.size() >= 3 );
			
			for ( NameValuePair nameValuePair : nameValuePairs ) {
				 String name = nameValuePair.getName();
				 String value = nameValuePair.getValue();
				 
				 assertNotNull(name);
				 assertNotNull(value);
				 
				 assertTrue(name.contains("grant_type") ||
						 name.contains("client_id") ||
						 name.contains("client_secret") );
				 if (  name.contains("grant_type") ) {
					 assertTrue(value.equals("client_credentials") );
				 }
			}
			
		} catch( Exception e) {
			assertTrue(false);
		}
	}
		
	/**
	 * verifies exception thrown on getNameValuePairs when AuthorizationCodeConnection 
	 * is null
	 */
	@Test 
	public void getNameValuePairsAuthorizationCodeConnectionNull() {
		
		try {
			
			AuthorizationCodeConnection authorizationCodeConnection = null;
			
			List<NameValuePair> nameValuePairs = instance.getNameValuePairs(authorizationCodeConnection);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionException);
			assertTrue(e.getMessage().equals("Connection is Null!"));
		}
	}
	
	/**
	 * verifies getNameValuePairs returns valid values for Authorization Code Connection
	 * 
	 */
	@Test 
	public void getNameValuePairsAuthorizationCodeConnection() {
		
		Token token = null;
		try {
			// build configuration with details
			authCodeConfiguration.setAuthorizationCode("authorization_code");
			authCodeConfiguration.setScope("openid");
			authCodeConfiguration.setClientID("5cab3a80-b3fd-415f-955f-4f868596ff43");
			authCodeConfiguration.setClientSecret("4a26db08-2885-4766-b6bb-ad8d0eac7c22");
			
			authCodeConfiguration.setRedirectUrl("http://localhost:8889/marketplace/callback");
			
			// build token
			token = new Token();
			token.setAccess_token("98f34d77-9b1f-490d-84c7-c9333ef929df");
			token.setExpires_in(14400);
			token.setId_token("dhdghdeggwfgmsns");
			token.setRefresh_token("");
			token.setScope("openid");
			token.setToken_type("Bearer");
			
			AuthorizationCodeConnection authorizationCodeConnection = 
					(AuthorizationCodeConnection) connectFactoryInstance.createConnection(authCodeConfiguration);
			
			authorizationCodeConnection.setToken(token);
			
			List<NameValuePair> nameValuePairs = instance.getNameValuePairs(authorizationCodeConnection);
		
			assertNotNull(nameValuePairs);
			assertTrue(nameValuePairs.size() == 6);
			
			for ( NameValuePair nameValuePair : nameValuePairs ) {
				 String name = nameValuePair.getName();
				 String value = nameValuePair.getValue();
				 
				 assertNotNull(name);
				 assertNotNull(value);
				 
				 assertTrue( name.contains("code") ||
						 name.contains("scope") || 
						 name.contains("redirect_uri") ||
						 name.contains("grant_type") ||
						 name.contains("client_id") ||
						 name.contains("client_secret") );
				 if (  name.contains("code") ) {
					 assertTrue(value.equals("authorization_code")  );
				 }
				 if (  name.contains("scope") ) {
					 assertTrue(value.equals("openid")  );
				 }
				 if (  name.contains("grant_type") ) {
					 assertTrue(value.equals("authorization_code") );
				 }
			}
			
		} catch (ConnectionException e) {
			assertTrue(false);
		}
	}
	
	/**
	 * verifies exception thrown on getNameValuePairs when Client Credentials Connection 
	 * is null
	 */
	@Test 
	public void getNameValuePairsClientCredentialsConnectionNull() {
		
		Token token = null;
		try {
			
			ClientCredentialsConnection clientCredentialsConnection = null;			
			List<NameValuePair> nameValuePairs = instance.getNameValuePairs(clientCredentialsConnection);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionException);
			assertTrue(e.getMessage().equals("Connection is Null!"));
		}
	}
	
	/**
	 * verifies getNameValuePairs returns valid values for Client Credentials Connection
	 * 
	 */
	@Test 
	public void getNameValuePairsClientCredentialsConnection() {
		
		Token token = null;
		try {
			// build configuration 
			authCodeConfiguration.setClientID("5cab3a80-b3fd-415f-955f-4f868596ff43");
			authCodeConfiguration.setClientSecret("4a26db08-2885-4766-b6bb-ad8d0eac7c22");
			
			// build token
			token = new Token();
			token.setAccess_token("98f34d77-9b1f-490d-84c7-c9333ef929df");
			token.setScope("api");
			
			connectFactoryInstance = ADPAPIConnectionFactory.getInstance();
			
			ClientCredentialsConnection clientCredentialsConnection = 
					(ClientCredentialsConnection) connectFactoryInstance.createConnection(clientCredentialsConfiguration);
			
			clientCredentialsConnection.setToken(token);
			
			List<NameValuePair> nameValuePairs = instance.getNameValuePairs(clientCredentialsConnection);
		
			assertNotNull(nameValuePairs);
			assertTrue(nameValuePairs.size() == 3);
			
			for ( NameValuePair nameValuePair : nameValuePairs ) {
				 String name = nameValuePair.getName();
				 String value = nameValuePair.getValue();
				 
				 assertNotNull(name);
				 assertNotNull(value);
				
				 assertTrue( name.contains("grant_type") ||
						 name.contains("client_id") ||
						 name.contains("client_secret") );
				 
				 if (  name.contains("grant_type") ) {
					 assertTrue(value.equals("client_credentials") );
				 }
			}
			
		} catch (ConnectionException e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testProcessTokenResponse() {
		
		String currentDirectory = "";
		Token token = null;
		
		//initializes the client specific configurations
		//build client configuration
		clientCredentialsConfiguration.setClientID("88a73992-07f2-4714-ab4b-de782acd9c4d");
		clientCredentialsConfiguration.setClientSecret("a130adb7-aa51-49ac-9d02-0d4036b63541");
		
		try {
			
			currentDirectory = (new java.io.File( "." ).getCanonicalPath());
			String concatSSLCertPath = currentDirectory.concat("//src/main/resources/certs/keystore.jks");
			
			clientCredentialsConfiguration.setSslCertPath(concatSSLCertPath);
			clientCredentialsConfiguration.setKeyPassword("adpadp10");	
			clientCredentialsConfiguration.setStorePassword("adpadp10");
			clientCredentialsConfiguration.setTokenServerUrl("https://iat-api.adp.com/auth/oauth/v2/token");
			clientCredentialsConfiguration.setApiRequestUrl("https://iat-api.adp.com");
			
			//call to establish a connection with ADP
			ClientCredentialsConnection clientCredentialsConnection = (ClientCredentialsConnection) 
					ADPAPIConnectionFactory.getInstance().createConnection(clientCredentialsConfiguration);
							
			// set connection configuration object after populating configuration with properties 
	    	clientCredentialsConnection.setConnectionConfiguration(clientCredentialsConfiguration);
			    	
			// invoke connect to acquire Access Token
			clientCredentialsConnection.connect();
			    	
			//tests indirectly processTokenResponse and process response
			if ( clientCredentialsConnection != null ) {		
				if ( clientCredentialsConnection.getToken() != null ) {
					 token = clientCredentialsConnection.getToken();
					
					 assertNotNull(token);
					 assertNotNull(token.getAccess_token());
					 assertNotNull(token.getExpires_in());
					 assertNotNull(token.getScope());
					 assertNotNull(token.getToken_type());
					 
					 assertNull(token.getRefresh_token());
					 assertNull(token.getId_token());
				}
			}
				
		} catch (IOException e) {
			assertTrue(false);
		} catch (ConnectionException e) {
			assertTrue(false);
		}
	}
}
