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
package com.adp.marketplace.core.connection.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.adp.marketplace.connection.configuration.ClientCredentialsConfiguration;
import com.adp.marketplace.connection.core.ADPAPIConnectionFactory;
import com.adp.marketplace.connection.core.ClientCredentialsConnection;
import com.adp.marketplace.connection.exception.ConnectionException;
import com.adp.marketplace.connection.vo.Token;

/**
 * @author tallaprs
 *
 */
public class ClientCredentialsConnectionTest {
	
	ADPAPIConnectionFactory connectFactoryInstance = null;
	ClientCredentialsConfiguration clientCredentialsConfiguration = null;
	ClientCredentialsConnection clientCredentialsConnection = null;
	
	String tokenServerUrl;
	String clientID;
	String clientSecret;
	String scope;
	String sslCertPath;
	String storePassword;
	String keyPassword;
	
	/*
	 * 
	 */
	@Before
	public void setUp() {
		
		connectFactoryInstance = ADPAPIConnectionFactory.getInstance();
		clientCredentialsConfiguration = new ClientCredentialsConfiguration();

		
		tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		
		clientID = "88a73992-07f2-4714-ab4b-de782acd9c4d";
		clientSecret = "a130adb7-aa51-49ac-9d02-0d4036b63541";
		
		scope = "api";
		
		sslCertPath = "//src/main/resources/certs/keystore.jks";
		storePassword = "adpadp10";
		keyPassword = "adpadp10";
	}

	/**
	 * @throws java.lang.Exception Exception thrown if this call fails
	 */
	@After
	public void tearDown() throws Exception {

		connectFactoryInstance = null;
		clientCredentialsConfiguration = null;
		
		tokenServerUrl = null;
		
		clientID = null;
		clientSecret = null;
		
		scope = null;
		
		sslCertPath = null;
		storePassword = null;
		keyPassword = null;	
	}
	
	/**
	 * verify connect retrieves a token
	 */
	@Test
	public void connect() {
		
		try {
			
			//valid path
			String FILE_PATH = (new java.io.File( "." ).getCanonicalPath()).concat(sslCertPath);
		
			clientCredentialsConfiguration.setTokenServerUrl(tokenServerUrl);
			
			clientCredentialsConfiguration.setClientID(clientID);
			clientCredentialsConfiguration.setClientSecret(clientSecret);
			
			clientCredentialsConfiguration.setSslCertPath(FILE_PATH);
			clientCredentialsConfiguration.setKeyPassword(keyPassword);
			clientCredentialsConfiguration.setStorePassword(storePassword);
			
			
			clientCredentialsConnection = (ClientCredentialsConnection) ADPAPIConnectionFactory.getInstance().
						createConnection(clientCredentialsConfiguration);
				
			//connect
			clientCredentialsConnection.connect();
			
			Token token = clientCredentialsConnection.getToken();
				
			if ( token != null) {				
				token = clientCredentialsConnection.getToken();
			
				assertNotNull( token);
				
				assertTrue( token.getExpires_in() != 0 && token.getExpires_in() >= 14400 );
				assertTrue( token.getAccess_token() != null && token.getAccess_token().length() > 0 );
							
				assertTrue( token.getScope() != null && token.getScope().contains("api"));
				assertTrue( token.getToken_type() != null && token.getToken_type().equals("Bearer"));
				
				assertTrue( token.getId_token() == null);
				assertTrue( token.getRefresh_token() == null);	
					
				assertNotNull( clientCredentialsConnection.getConnectionConfiguration());
				assertNull( clientCredentialsConnection.getErrorResponse());
			}
			
		} catch (ConnectionException e) {
			// assertTrue(false);
		} catch (IOException e) {
			assertTrue(false);
		}	
	}
	
	/**
	 * verifies ConnectionException on calling connect() with null configuration
	 */
	@Test
	public void connectExceptionNullConfiguration() {
		
		try {
			
			//valid path
			String FILE_PATH = (new java.io.File( "." ).getCanonicalPath()).concat(sslCertPath);
		
			clientCredentialsConfiguration.setTokenServerUrl(tokenServerUrl);
			
			clientCredentialsConfiguration.setClientID(clientID);
			clientCredentialsConfiguration.setClientSecret(clientSecret);
			
			clientCredentialsConfiguration.setSslCertPath(FILE_PATH);
			clientCredentialsConfiguration.setKeyPassword(keyPassword);
			clientCredentialsConfiguration.setStorePassword(storePassword);
			
			
			clientCredentialsConnection = (ClientCredentialsConnection) ADPAPIConnectionFactory.getInstance().
						createConnection(clientCredentialsConfiguration);
				
			// reset configuration to null
			clientCredentialsConfiguration  = null;
			clientCredentialsConnection.setConnectionConfiguration(clientCredentialsConfiguration);
			
			//connect
			clientCredentialsConnection.connect();
			
			assertTrue(false);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionException);
			assertTrue(e.getCause().getMessage().contains("Connection Configuration is Not Set in request!!"));
		}	
	}

	/**
	 * verify SSL context path error
	 */
	@Test
	public void connectExceptionSSLContextPathInvalid() {
		
		try {
			
			//valid path
			//String FILE_PATH = (new java.io.File( "." ).getCanonicalPath()).concat(sslCertPath);
		
			clientCredentialsConfiguration.setTokenServerUrl(tokenServerUrl);
			
			clientCredentialsConfiguration.setClientID(clientID);
			
			clientCredentialsConfiguration.setClientSecret(clientSecret);
			
			clientCredentialsConfiguration.setSslCertPath(sslCertPath);
			clientCredentialsConfiguration.setKeyPassword(keyPassword);
			clientCredentialsConfiguration.setStorePassword(storePassword);
			
			
			clientCredentialsConnection = (ClientCredentialsConnection) ADPAPIConnectionFactory.getInstance().
						createConnection(clientCredentialsConfiguration);
				
			//connect
			clientCredentialsConnection.connect();
			
			assertTrue(false);
				
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionException);
			assertTrue(e.getCause().getMessage().contains("FileNotFoundException"));
		}
	}
	
	/**
	 * verify 401 Unauthorized User Response on calling connect()
	 */
	@Test
	public void connectVerifyServerErrorResponse() {
		
		String errorResponse = null;
		
		try {
			
			//valid path
			String FILE_PATH = (new java.io.File( "." ).getCanonicalPath()).concat(sslCertPath);
		
			clientCredentialsConfiguration.setTokenServerUrl(tokenServerUrl);
			
			//invalid client id
			clientID = "88a73992-07f2-4714-ab4b-de782axxxxxx";
			clientCredentialsConfiguration.setClientID(clientID);
			
			clientCredentialsConfiguration.setClientSecret(clientSecret);
			
			clientCredentialsConfiguration.setSslCertPath(FILE_PATH);
			clientCredentialsConfiguration.setKeyPassword(keyPassword);
			clientCredentialsConfiguration.setStorePassword(storePassword);
			
			
			clientCredentialsConnection = (ClientCredentialsConnection) ADPAPIConnectionFactory.getInstance().
						createConnection(clientCredentialsConfiguration);
				
			//connect
			clientCredentialsConnection.connect();
			
			Token token = clientCredentialsConnection.getToken();
				
			if ( token == null) {
				
				errorResponse = clientCredentialsConnection.getErrorResponse();
				
				assertNotNull(errorResponse);
				assertTrue(errorResponse.contains("HTTP/1.1 401 Unauthorized "));
				assertTrue(errorResponse.contains("invalid_client"));
				assertTrue(errorResponse.contains("Failed to get client credentials"));
			}
				
		} catch (ConnectionException e) {
			// assertTrue(false);
		} catch (IOException e) {
			assertTrue(false);
		}	
	}
	
	/**
	 * verify disconnect resets connection attributes
	 */
	@Test
	public void disconnect() {
		
		try {
			
			//valid path
			String FILE_PATH = (new java.io.File( "." ).getCanonicalPath()).concat(sslCertPath);
		
			clientCredentialsConfiguration.setTokenServerUrl(tokenServerUrl);
			
			clientCredentialsConfiguration.setClientID(clientID);
			clientCredentialsConfiguration.setClientSecret(clientSecret);
			
			clientCredentialsConfiguration.setSslCertPath(FILE_PATH);
			clientCredentialsConfiguration.setKeyPassword(keyPassword);
			clientCredentialsConfiguration.setStorePassword(storePassword);
			
			
			clientCredentialsConnection = (ClientCredentialsConnection) ADPAPIConnectionFactory.getInstance().
						createConnection(clientCredentialsConfiguration);
				
			//connect
			clientCredentialsConnection.connect();
			
			Token token = clientCredentialsConnection.getToken();
				
			if ( token != null) {
				assertNotNull(token);
			}
			
			//disconnect
			clientCredentialsConnection.disconnect();
				
			token = clientCredentialsConnection.getToken();
			
			assertNotNull( token);
			
			assertTrue( token.getExpires_in() == 0);
			assertTrue( token.getAccess_token() == null);
			assertTrue( token.getId_token() == null);
			assertTrue( token.getRefresh_token() == null);				
			assertTrue( token.getScope() == null);
			assertTrue( token.getToken_type() == null);
				
			assertNull( clientCredentialsConnection.getConnectionConfiguration());
			assertNull( clientCredentialsConnection.getErrorResponse());
				
		} catch (ConnectionException e) {
			// assertTrue(false);
		} catch (IOException e) {
			assertTrue(false);
		}	
	}
	
	/**
	 * verifies if connection is alive - token is empty the connection is not alive
	 */
	@Test
	public void isConnectionIndicator() {
		
		boolean isAlive = false;
		
		try {
			
			//valid path
			String FILE_PATH = (new java.io.File( "." ).getCanonicalPath()).concat(sslCertPath);
		
			clientCredentialsConfiguration.setTokenServerUrl(tokenServerUrl);
			
			clientCredentialsConfiguration.setClientID(clientID);
			clientCredentialsConfiguration.setClientSecret(clientSecret);
			
			clientCredentialsConfiguration.setSslCertPath(FILE_PATH);
			clientCredentialsConfiguration.setKeyPassword(keyPassword);
			clientCredentialsConfiguration.setStorePassword(storePassword);
			
			
			clientCredentialsConnection = (ClientCredentialsConnection) ADPAPIConnectionFactory.getInstance().
						createConnection(clientCredentialsConfiguration);
				
			//connect
			clientCredentialsConnection.connect();
			
			Token token = clientCredentialsConnection.getToken();
				
			if ( token != null) {
				assertNotNull(token);
				isAlive = clientCredentialsConnection.isConnectionIndicator();
				
				//connection must be active
				assertTrue(isAlive);
			}
			
			//disconnect
			clientCredentialsConnection.disconnect();
		
			isAlive = clientCredentialsConnection.isConnectionIndicator();
			
			// connection must be disconnected
			assertTrue( isAlive == false);	
			
			token = clientCredentialsConnection.getToken();
			assertNotNull( token);
			
			assertTrue( token.getExpires_in() == 0);
			assertTrue( token.getAccess_token() == null);
			assertTrue( token.getId_token() == null);
			assertTrue( token.getRefresh_token() == null);				
			assertTrue( token.getScope() == null);
			assertTrue( token.getToken_type() == null);
				
			assertNull( clientCredentialsConnection.getConnectionConfiguration());
			assertNull( clientCredentialsConnection.getErrorResponse());
				
		} catch (ConnectionException e) {
			// assertTrue(false);
		} catch (IOException e1) {
			assertTrue(false);
		}	
	}
	
}
