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
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.adp.marketplace.connection.configuration.AuthorizationCodeConfiguration;
import com.adp.marketplace.connection.constants.Constants;
import com.adp.marketplace.connection.core.ADPAPIConnectionFactory;
import com.adp.marketplace.connection.core.AuthorizationCodeConnection;
import com.adp.marketplace.connection.exception.ConnectionException;
import com.adp.marketplace.connection.exception.ConnectionValidatorException;
import com.adp.marketplace.connection.vo.Token;

/**
 * @author tallaprs
 *
 */
public class AuthorizationCodeConnectionTest {
	
	ADPAPIConnectionFactory connectFactoryInstance = null;
	AuthorizationCodeConfiguration authorizationCodeConfiguration =  null;
	
	String apiRequestUrl;
	String baseAuthorizationUrl;
	String redirectUrl;

	String tokenServerUrl;
	String clientID;
	String clientSecret;
	
	String scope;
	String authorizationCode;
	String sslCertPath;
	String storePassword;
	String keyPassword;
	
	/*
	 * 
	 */
	@Before
	public void setUp() {
		
		connectFactoryInstance = ADPAPIConnectionFactory.getInstance();
		authorizationCodeConfiguration =  new AuthorizationCodeConfiguration();
		
		apiRequestUrl = "https://iat-api.adp.com/core/v1/userinfo";
		baseAuthorizationUrl = "https://iat-accounts.adp.com/auth/oauth/v2/authorize";
		redirectUrl = "http://localhost:8889/marketplace/callback";
		tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		clientSecret = "4a26db08-2885-4766-b6bb-ad8d0eac7c22";
		
		scope = "openid";
		authorizationCode = "authorization_code";
		
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
		authorizationCodeConfiguration =  null;
		
		apiRequestUrl = null;
		baseAuthorizationUrl = null;
		redirectUrl = null;

		tokenServerUrl = null;
		clientID = null;
		clientSecret = null;
		
		scope = null;
		authorizationCode = null;
		sslCertPath = null;
		storePassword = null;
		keyPassword = null;	
	}
	
	/**
	 * test class constructor
	 */
	@Test
	public void ConstructorTest() {
		
		AuthorizationCodeConnection authorizationCodeConnection = new AuthorizationCodeConnection(authorizationCodeConfiguration);
		
		assertNotNull(authorizationCodeConnection);
		assertNotNull(authorizationCodeConnection.getConnectionConfiguration());
		assertNull( authorizationCodeConnection.getErrorResponse());
		assertNull( authorizationCodeConnection.getRefreshToken());
		assertNull( authorizationCodeConnection.getToken());
		
	}

	// success test cases are AuthCode sample apps {AuthCodeConnectionSampelApp and AuthCodeUserInfoSampleApp} 
	// for Authorization Code flow
	
	/**
	 * verifies ConnectionException on calling connect() with null configuration
	 */
	@Test
	public void connectExceptionNullConfiguration() {
		
		authorizationCodeConfiguration.setApiRequestUrl(apiRequestUrl);
		authorizationCodeConfiguration.setBaseAuthorizationUrl(baseAuthorizationUrl);
		authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		//authorizationCodeConfiguration.setState(state);
		authorizationCodeConfiguration.setScope(scope);
		authorizationCodeConfiguration.setAuthorizationCode(authorizationCode);
		
		
		authorizationCodeConfiguration.setSslCertPath(sslCertPath);
		
		//StorePassword  null 
		authorizationCodeConfiguration.setStorePassword(null);
		authorizationCodeConfiguration.setKeyPassword(keyPassword);
	
		try {
			AuthorizationCodeConnection authorizationCodeConnection =
				(AuthorizationCodeConnection) ADPAPIConnectionFactory.getInstance().
					createConnection(authorizationCodeConfiguration);
			
			// test - reset configuration on connection to null
			authorizationCodeConnection.setConnectionConfiguration(null);
			
			authorizationCodeConnection.connect();
			
		} catch (ConnectionException e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionException);
			assertTrue(e.getCause().getMessage().equals("Connection Configuration is Not Set in request!!"));
		} 
	}
	
	
	/**
	 * verify SSL context path error
	 */
	@Test
	public void connectExceptionSSLContextPathInvalid() {
		
		authorizationCodeConfiguration.setApiRequestUrl(apiRequestUrl);
		authorizationCodeConfiguration.setBaseAuthorizationUrl(baseAuthorizationUrl);
		authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setScope(scope);
		authorizationCodeConfiguration.setAuthorizationCode(authorizationCode);
		
		try {
			
			//valid path
			//String FILE_PATH = (new java.io.File( "." ).getCanonicalPath()).concat(sslCertPath);
		
			authorizationCodeConfiguration.setSslCertPath(sslCertPath);
			authorizationCodeConfiguration.setStorePassword(storePassword);
			authorizationCodeConfiguration.setKeyPassword(keyPassword);
			
			AuthorizationCodeConnection authorizationCodeConnection = 
					(AuthorizationCodeConnection) ADPAPIConnectionFactory.getInstance().
						createConnection(authorizationCodeConfiguration);
				
			authorizationCodeConnection.connect();
				
			} catch (Exception e) {
				assertNotNull(e);
				assertTrue(e instanceof ConnectionException);
				assertTrue(e.getCause().getMessage().contains("FileNotFoundException"));
			} 
	}
	
	/**
	 * verify 400 Bad Request Response on calling connect()
	 */
	@Test
	public void connectVerifyServerErrorResponse() {
		
		String errorResponse = null;
		
		authorizationCodeConfiguration.setApiRequestUrl(apiRequestUrl);
		authorizationCodeConfiguration.setBaseAuthorizationUrl(baseAuthorizationUrl);
		authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setScope(scope);
		authorizationCodeConfiguration.setAuthorizationCode(authorizationCode);
		
		try {
			
			//valid path
			String FILE_PATH = (new java.io.File( "." ).getCanonicalPath()).concat(sslCertPath);
		
			authorizationCodeConfiguration.setSslCertPath(FILE_PATH);
			authorizationCodeConfiguration.setStorePassword(storePassword);
			authorizationCodeConfiguration.setKeyPassword(keyPassword);
			
			AuthorizationCodeConnection authorizationCodeConnection 
				= (AuthorizationCodeConnection) ADPAPIConnectionFactory.getInstance().
						createConnection(authorizationCodeConfiguration);
				
				authorizationCodeConnection.connect();
				Token token = authorizationCodeConnection.getToken();
				if ( token == null ) {
					
					errorResponse = authorizationCodeConnection.getErrorResponse();
					
					assertNotNull(errorResponse);
					assertTrue(errorResponse.contains("HTTP/1.1 400 Bad Request"));
					assertTrue(errorResponse.contains("Token retrievel error"));
				}
				
			} catch (ConnectionException e) {
				assertTrue(false);
			} catch (IOException e1) {
				// 
			}
	}
	
	/**
	 * verify disconnect resets connection attributes
	 */
	@Test
	public void disconnect() {
		
		String errorResponse = null;
		
		authorizationCodeConfiguration.setApiRequestUrl(apiRequestUrl);
		authorizationCodeConfiguration.setBaseAuthorizationUrl(baseAuthorizationUrl);
		authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setScope(scope);
		authorizationCodeConfiguration.setAuthorizationCode(authorizationCode);
		
		try {
			
			//valid path
			String FILE_PATH = (new java.io.File( "." ).getCanonicalPath()).concat(sslCertPath);
		
			authorizationCodeConfiguration.setSslCertPath(FILE_PATH);
			authorizationCodeConfiguration.setStorePassword(storePassword);
			authorizationCodeConfiguration.setKeyPassword(keyPassword);
			
			AuthorizationCodeConnection authorizationCodeConnection = 
					(AuthorizationCodeConnection) ADPAPIConnectionFactory.getInstance().
						createConnection(authorizationCodeConfiguration);
				
				//connect
 				Token token = authorizationCodeConnection.getToken();
				if ( token == null ) {
					errorResponse = authorizationCodeConnection.getErrorResponse();
				}
				
				//disconnect
				authorizationCodeConnection.disconnect();
				
				token = authorizationCodeConnection.getToken();
				
				assertNotNull( token);
				
				assertTrue( token.getExpires_in() == 0);
				assertTrue( token.getAccess_token() == null);
				assertTrue( token.getId_token() == null);
				assertTrue( token.getRefresh_token() == null);
				assertTrue( token.getScope() == null);
				assertTrue( token.getToken_type() == null);
				
				assertNull( authorizationCodeConnection.getConnectionConfiguration());
				assertNull( authorizationCodeConnection.getErrorResponse());
				
			} catch (ConnectionException e) {
				assertTrue(false);
			} catch (IOException e1) {
				assertTrue(false);
			}	
	}
	
	/**
	 * verifies that the authorization URl is constructed on connection with valid configurations and returned
	 * with expected fields
	 */
	@Test
	public void getAuthorizationUrl()  {
		
		authorizationCodeConfiguration.setApiRequestUrl(apiRequestUrl);
		authorizationCodeConfiguration.setBaseAuthorizationUrl(baseAuthorizationUrl);
		authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setScope(scope);
		authorizationCodeConfiguration.setAuthorizationCode(authorizationCode);
		
		try {
			
			//valid path
			String FILE_PATH = (new java.io.File( "." ).getCanonicalPath()).concat(sslCertPath);
		
			authorizationCodeConfiguration.setSslCertPath(FILE_PATH);
			authorizationCodeConfiguration.setStorePassword(storePassword);
			authorizationCodeConfiguration.setKeyPassword(keyPassword);
			
			AuthorizationCodeConnection authorizationCodeConnection = 
					(AuthorizationCodeConnection) ADPAPIConnectionFactory.getInstance().
						createConnection(authorizationCodeConfiguration);
				
			authorizationCodeConnection.connect();
			
			String authorizationUrl = authorizationCodeConnection.getAuthorizationUrl();
			
			assertNotNull(authorizationUrl);
			
			assertTrue(authorizationUrl.contains(Constants.CLIENT_ID));
			assertTrue(authorizationUrl.contains(Constants.RESPONSE_TYPE));
			assertTrue(authorizationUrl.contains(Constants.REDIRECT_URI));
			assertTrue(authorizationUrl.contains(Constants.SCOPE));
			assertTrue(authorizationUrl.contains(Constants.STATE));
				
			} catch (ConnectionException e) {
				assertTrue(false);
			} catch (IOException e) {
				assertTrue(false);
			} 
	}
	
	/**
	 * verifies that the call getAuthorizationUri() throws exceptions if configurations is null
	 */
	@Test
	public void getAuthorizationUrlConfigurationNull()  {
		
		authorizationCodeConfiguration.setApiRequestUrl(apiRequestUrl);
		authorizationCodeConfiguration.setBaseAuthorizationUrl(baseAuthorizationUrl);
		authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setScope(scope);
		authorizationCodeConfiguration.setAuthorizationCode(authorizationCode);
		
		try {
			
			//valid path
			String FILE_PATH = (new java.io.File( "." ).getCanonicalPath()).concat(sslCertPath);
		
			authorizationCodeConfiguration.setSslCertPath(FILE_PATH);
			authorizationCodeConfiguration.setStorePassword(storePassword);
			authorizationCodeConfiguration.setKeyPassword(keyPassword);
			
			AuthorizationCodeConnection authorizationCodeConnection = 
					(AuthorizationCodeConnection) ADPAPIConnectionFactory.getInstance().
						createConnection(authorizationCodeConfiguration);
				
			// configuration reset on connection to null
			authorizationCodeConnection.setConnectionConfiguration(null);
			
			authorizationCodeConnection.connect();
			
			String authorizationUrl = authorizationCodeConnection.getAuthorizationUrl();
		
			assertTrue(false);
			
			} catch (IOException e) {
				assertTrue(false);
			} catch (Exception e) {
				assertNotNull(e);
				assertTrue(e instanceof ConnectionException);
				assertTrue(e.getCause() instanceof ConnectionValidatorException);
				assertTrue(e.getCause().getMessage().equals("Connection Configuration is Not Set in request!!"));
			}
	}
	
	/**
	 * verifies if the Configuration is populated with all fields required to build authorization URl else
	 * throws validation exceptions if configurations doesn't have required fields
	 */
	@Test
	public void getAuthorizationUrlConfigurationAttributesInvalid()  {

		authorizationCodeConfiguration.setBaseAuthorizationUrl(baseAuthorizationUrl);
		
		// client id is not set in request
		//authorizationCodeConfiguration.setClientID(clientID);
		
		// redirect url not set in request
		//authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		
		authorizationCodeConfiguration.setScope(scope);
		
		try {
			
			//valid path
			String FILE_PATH = (new java.io.File( "." ).getCanonicalPath()).concat(sslCertPath);
		
			authorizationCodeConfiguration.setSslCertPath(FILE_PATH);
			authorizationCodeConfiguration.setStorePassword(storePassword);
			authorizationCodeConfiguration.setKeyPassword(keyPassword);
			
			AuthorizationCodeConnection authorizationCodeConnection =
					(AuthorizationCodeConnection) ADPAPIConnectionFactory.getInstance().
						createConnection(authorizationCodeConfiguration);
				
			// configuration reset on connection to null
			authorizationCodeConnection.setConnectionConfiguration(authorizationCodeConfiguration);
			
			authorizationCodeConnection.connect();
			
			String authorizationUrl = authorizationCodeConnection.getAuthorizationUrl();
		
			assertTrue(false);
			
			} catch (IOException e) {
				assertTrue(false);
			} catch (Exception e) {
				assertNotNull(e);
				assertTrue(e instanceof ConnectionException);
				assertTrue(e.getCause() instanceof ConnectionValidatorException);
				assertTrue(e.getCause().getMessage().equals("Client ID is not populated!!"));
			}
	}
	
	
	/**
	 * verifies if connection is alive - token is empty the connection is not alive
	 */
	@Test
	public void isConnectionIndicator() {
	
		String errorResponse = null;
		
		authorizationCodeConfiguration.setApiRequestUrl(apiRequestUrl);
		authorizationCodeConfiguration.setBaseAuthorizationUrl(baseAuthorizationUrl);
		authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setScope(scope);
		authorizationCodeConfiguration.setAuthorizationCode(authorizationCode);
		
		try {
			
			//valid path
			String FILE_PATH = (new java.io.File( "." ).getCanonicalPath()).concat(sslCertPath);
		
			authorizationCodeConfiguration.setSslCertPath(FILE_PATH);
			authorizationCodeConfiguration.setStorePassword(storePassword);
			authorizationCodeConfiguration.setKeyPassword(keyPassword);
			
			AuthorizationCodeConnection authorizationCodeConnection = 
					(AuthorizationCodeConnection) ADPAPIConnectionFactory.getInstance().
						createConnection(authorizationCodeConfiguration);
				
				authorizationCodeConnection.connect();
				Token token = authorizationCodeConnection.getToken();
				if ( token == null ) {
					
					errorResponse = authorizationCodeConnection.getErrorResponse();
					
					assertNotNull(errorResponse);
					assertTrue(errorResponse.contains("HTTP/1.1 400 Bad Request"));
					assertTrue(errorResponse.contains("Token retrievel error"));
				}
				
				boolean isAlive = authorizationCodeConnection.isConnectionIndicator();
				assertTrue( isAlive == false);	
				
			} catch (ConnectionException e) {
				assertTrue(false);
			} catch (IOException e1) {
				// 
			}
	}

}
