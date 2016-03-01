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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.adp.marketplace.connection.configuration.AuthorizationCodeConfiguration;
import com.adp.marketplace.connection.configuration.ClientCredentialsConfiguration;
import com.adp.marketplace.connection.exception.ConnectionValidatorException;

/**
 * @author tallaprs
 *
 */
public class ConnectionValidatorUtilsTest {

	
	ConnectionValidatorUtils instance = null;
	AuthorizationCodeConfiguration authorizationCodeConfiguration = null;
	ClientCredentialsConfiguration clientCredentialsConfiguration = null;
	
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
		instance = ConnectionValidatorUtils.getInstance();
		authorizationCodeConfiguration = new AuthorizationCodeConfiguration();
		clientCredentialsConfiguration = new ClientCredentialsConfiguration();
	}
	
	/**
	 * @throws java.lang.Exception Exception thrown if this call fails
	 */
	@After
	public void tearDown() throws Exception {
		instance = null;
		authorizationCodeConfiguration = null;
		clientCredentialsConfiguration = null;
	}

	/**
	 * 
	 * verifies that a singleton instance is created for ConnectionValidatorUtils
	 */
	@Test
	public void singletonInstanceCreated() {
		
		ConnectionValidatorUtils anotherInstance = ConnectionValidatorUtils.getInstance();
		
		assertNotNull(instance);
		assertNotNull(anotherInstance);
		
		assertTrue(instance instanceof ConnectionValidatorUtils);
		assertTrue(anotherInstance instanceof ConnectionValidatorUtils);
		
		assertSame(instance, anotherInstance);
		assertEquals(instance, anotherInstance);
	}
	
	@Test
	public void validateConnectionAuthCodeConfigNull() {
		
		AuthorizationCodeConfiguration authorizationCodeConfiguration = null;
		
		try {
			instance.validate(authorizationCodeConfiguration);
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("Connection Configuration is Not Set in request!!"));
		}	
	}
	
	@Test
	public void validateConnectionClientCredentialsConfigNull() {
		
		ClientCredentialsConfiguration clientCredentialsConfiguration = null;
		
		try {
			instance.validate(clientCredentialsConfiguration);
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("Connection Configuration is Not Set in request!!"));
		}	
	}
	
	@Test
	public void validateAuthCodeConfigConnection() {
		
		boolean valid = false;
		
		String clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		String clientSecret = "4a26db08-2885-4766-b6bb-ad8d0eac7c22";
		
		String sslCertPath = "/AuthCodeUserInfoSampleApp/src/main/resources/certs/keystore.jks";
		String keyPassword = "adpadp10";
		String storePassword = "adpadp10";
		
		String tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setSslCertPath(sslCertPath);
		authorizationCodeConfiguration.setKeyPassword(keyPassword);
		authorizationCodeConfiguration.setStorePassword(storePassword);
		
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);

		try {
			valid = instance.validate(authorizationCodeConfiguration);
			assertTrue(valid);
			
		} catch (ConnectionValidatorException e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void validateAuthCodeConfigConnectionClientIDNull() {
		
		boolean valid = false;
		
		String clientID = null;
		String clientSecret = "4a26db08-2885-4766-b6bb-ad8d0eac7c22";
		
		String sslCertPath = "/AuthCodeUserInfoSampleApp/src/main/resources/certs/keystore.jks";
		String keyPassword = "adpadp10";
		String storePassword = "adpadp10";
		
		String tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setSslCertPath(sslCertPath);
		authorizationCodeConfiguration.setKeyPassword(keyPassword);
		authorizationCodeConfiguration.setStorePassword(storePassword);
		
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);

		try {
			valid = instance.validate(authorizationCodeConfiguration);
			assertTrue(false);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("Either both or one of the Client Credentials is not populated!!"));
		}
	}
	
	@Test
	public void validateAuthCodeConfigConnectionClientSecretNull() {
		
		boolean valid = false;
		
		String clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		String clientSecret = null;
		
		String sslCertPath = "/AuthCodeUserInfoSampleApp/src/main/resources/certs/keystore.jks";
		String keyPassword = "adpadp10";
		String storePassword = "adpadp10";
		
		String tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setSslCertPath(sslCertPath);
		authorizationCodeConfiguration.setKeyPassword(keyPassword);
		authorizationCodeConfiguration.setStorePassword(storePassword);
		
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);

		try {
			valid = instance.validate(authorizationCodeConfiguration);
			assertTrue(false);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("Either both or one of the Client Credentials is not populated!!"));
		}
	}
	
	@Test
	public void validateAuthCodeConfigConnectionSSLFieldNull() {
		
		boolean valid = false;
		
		String clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		String clientSecret = "4a26db08-2885-4766-b6bb-ad8d0eac7c22";
		
		String sslCertPath = "/AuthCodeUserInfoSampleApp/src/main/resources/certs/keystore.jks";
		String keyPassword = null;
		String storePassword = null;
		
		String tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		
		authorizationCodeConfiguration = new AuthorizationCodeConfiguration();
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setSslCertPath(sslCertPath);
		authorizationCodeConfiguration.setKeyPassword(keyPassword);
		authorizationCodeConfiguration.setStorePassword(storePassword);
		
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);

		try {
			valid = instance.validate(authorizationCodeConfiguration);
			assertTrue(false);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("One or more key SSL attributes are missing in request!!"));
		}
	}
	
	@Test
	public void validateAuthCodeConfigConnectionTokenUrlNull() {
		
		boolean valid = false;
		
		String clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		String clientSecret = "4a26db08-2885-4766-b6bb-ad8d0eac7c22";
		
		String sslCertPath = "/AuthCodeUserInfoSampleApp/src/main/resources/certs/keystore.jks";
		String keyPassword = "adpadp10";
		String storePassword = "adpadp10";
		
		String tokenServerUrl = null;
		
		authorizationCodeConfiguration = new AuthorizationCodeConfiguration();
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setSslCertPath(sslCertPath);
		authorizationCodeConfiguration.setKeyPassword(keyPassword);
		authorizationCodeConfiguration.setStorePassword(storePassword);
		
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);

		try {
			valid = instance.validate(authorizationCodeConfiguration);
			assertTrue(false);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("Token URL is null or empty!"));
		}
	}
	
	@Test 
	public void validateAuthCodeAuthUrl() {
		
		boolean valid = false;
		
		String clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		String clientSecret = "4a26db08-2885-4766-b6bb-ad8d0eac7c22";
		
		String sslCertPath = "/AuthCodeUserInfoSampleApp/src/main/resources/certs/keystore.jks";
		String keyPassword = "adpadp10";
		String storePassword = "adpadp10";
		
		String apiRequestUrl = "https://iat-api.adp.com/core/v1/userinfo";
		String baseAuthUrl = "https://iat-accounts.adp.com/auth/oauth/v2/authorize";
		String redirectUrl = "http://localhost:8889/marketplace/callback";
		String tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		
		authorizationCodeConfiguration.setAuthorizationCode("authorization_code");
		authorizationCodeConfiguration.setState(0.0);
		authorizationCodeConfiguration.setTokenExpiration(0);
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setSslCertPath(sslCertPath);
		authorizationCodeConfiguration.setKeyPassword(keyPassword);
		authorizationCodeConfiguration.setStorePassword(storePassword);
		
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);
		authorizationCodeConfiguration.setApiRequestUrl(apiRequestUrl);
		authorizationCodeConfiguration.setBaseAuthorizationUrl(baseAuthUrl);
		authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		authorizationCodeConfiguration.setDisconnectUrl(null);
		

		try {
			valid = instance.validateAuthCodeAuthUrl(authorizationCodeConfiguration);
			assertTrue(valid);
			
		} catch (ConnectionValidatorException e) {
			assertTrue(false);
		}
	}
	
	@Test 
	public void validateAuthCodeAuthUrlsNull() {
		
		boolean valid = false;
		
		String clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		String clientSecret = "4a26db08-2885-4766-b6bb-ad8d0eac7c22";
		
		String sslCertPath = "/AuthCodeUserInfoSampleApp/src/main/resources/certs/keystore.jks";
		String keyPassword = "adpadp10";
		String storePassword = "adpadp10";
		
		String apiRequestUrl = "https://iat-api.adp.com/core/v1/userinfo";
		String baseAuthUrl = null;
		String redirectUrl = null;
		String tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		
		authorizationCodeConfiguration.setAuthorizationCode("authorization_code");
		authorizationCodeConfiguration.setState(0.0);
		authorizationCodeConfiguration.setTokenExpiration(0);
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setSslCertPath(sslCertPath);
		authorizationCodeConfiguration.setKeyPassword(keyPassword);
		authorizationCodeConfiguration.setStorePassword(storePassword);
		
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);
		authorizationCodeConfiguration.setApiRequestUrl(apiRequestUrl);
		authorizationCodeConfiguration.setBaseAuthorizationUrl(baseAuthUrl);
		authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		authorizationCodeConfiguration.setDisconnectUrl(null);
		

		try {
			valid = instance.validateAuthCodeAuthUrl(authorizationCodeConfiguration);
			assertTrue(false);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("Invalid Authorization Request - missing required "
					+ "{authorization_url, client_id, "
				+ "redirect_ui, response_type, scope } information !!"));
		}
	}

	@Test
	public void validateAuthCodeTokenRequest() {
		
		boolean valid = false;
		
		String clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		String clientSecret = "4a26db08-2885-4766-b6bb-ad8d0eac7c22";
		
		String sslCertPath = "/AuthCodeUserInfoSampleApp/src/main/resources/certs/keystore.jks";
		String keyPassword = "adpadp10";
		String storePassword = "adpadp10";
		
		String scope = "openid";
		String authorizationCode = "authorization_code";
		String redirectUrl = "http://localhost:8889/marketplace/callback";
		String tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setSslCertPath(sslCertPath);
		authorizationCodeConfiguration.setKeyPassword(keyPassword);
		authorizationCodeConfiguration.setStorePassword(storePassword);
		
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);
		authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		authorizationCodeConfiguration.setScope(scope);
		authorizationCodeConfiguration.setAuthorizationCode(authorizationCode);
		try {
			valid = instance.validateAuthCodeTokenRequest(authorizationCodeConfiguration);
			assertTrue(valid);
			
		} catch (ConnectionValidatorException e) {
			assertTrue(false);
		}	
	}
	
	@Test
	public void validateAuthCodeTokenRequestConnectionNull() {
		
		boolean valid = false;
		authorizationCodeConfiguration = null;
		
		try {
			valid = instance.validateAuthCodeTokenRequest(authorizationCodeConfiguration);
			assertTrue(valid);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("Connection Configuration is Not Set in request!!"));
		}
		
	}
	
	@Test
	public void validateAuthCodeTokenRequestSSLFieldsNull() {
		
		boolean valid = false;
		
		String clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		String clientSecret = "4a26db08-2885-4766-b6bb-ad8d0eac7c22";
		
		String sslCertPath = null;
		String keyPassword = "adpadp10";
		String storePassword = null;
		
		String scope = "openid";
		String authorizationCode = "authorization_code";
		String redirectUrl = "http://localhost:8889/marketplace/callback";
		String tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setSslCertPath(sslCertPath);
		authorizationCodeConfiguration.setKeyPassword(keyPassword);
		authorizationCodeConfiguration.setStorePassword(storePassword);
		
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);
		authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		
		authorizationCodeConfiguration.setScope(scope);
		authorizationCodeConfiguration.setAuthorizationCode(authorizationCode);
		try {
			valid = instance.validateAuthCodeTokenRequest(authorizationCodeConfiguration);
			assertTrue(valid);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("One or more key SSL attributes are missing in request!!"));
		}	
	}
	
	@Test
	public void validateAuthCodeTokenRequestClientCredentialsNull() {
		
		boolean valid = false;
		
		String clientID = null;
		String clientSecret = null;
		
		String sslCertPath = "/AuthCodeUserInfoSampleApp/src/main/resources/certs/keystore.jks";
		String keyPassword = "adpadp10";
		String storePassword = "adpadp10";
		
		String scope = "openid";
		String authorizationCode = "authorization_code";
		String redirectUrl = "http://localhost:8889/marketplace/callback";
		String tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setSslCertPath(sslCertPath);
		authorizationCodeConfiguration.setKeyPassword(keyPassword);
		authorizationCodeConfiguration.setStorePassword(storePassword);
		
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);
		authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		
		authorizationCodeConfiguration.setScope(scope);
		authorizationCodeConfiguration.setAuthorizationCode(authorizationCode);
		try {
			valid = instance.validateAuthCodeTokenRequest(authorizationCodeConfiguration);
			assertTrue(valid);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("Client ID is not populated!!"));
		}	
	}
	
	@Test
	public void validateAuthCodeTokenRequestUrlsNull() {
		
		boolean valid = false;
		
		String clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		String clientSecret = "4a26db08-2885-4766-b6bb-ad8d0eac7c22";
		
		String sslCertPath = "/AuthCodeUserInfoSampleApp/src/main/resources/certs/keystore.jks";
		String keyPassword = "adpadp10";
		String storePassword = "adpadp10";
		
		String scope = "openid";
		String authorizationCode = "authorization_code";
		String redirectUrl = null;
		String tokenServerUrl = null;
		
		authorizationCodeConfiguration.setClientID(clientID);
		authorizationCodeConfiguration.setClientSecret(clientSecret);
		
		authorizationCodeConfiguration.setSslCertPath(sslCertPath);
		authorizationCodeConfiguration.setKeyPassword(keyPassword);
		authorizationCodeConfiguration.setStorePassword(storePassword);
		
		authorizationCodeConfiguration.setTokenServerUrl(tokenServerUrl);
		authorizationCodeConfiguration.setRedirectUrl(redirectUrl);
		
		authorizationCodeConfiguration.setScope(scope);
		authorizationCodeConfiguration.setAuthorizationCode(authorizationCode);
		try {
			valid = instance.validateAuthCodeTokenRequest(authorizationCodeConfiguration);
			assertTrue(valid);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("Invalid Authorization Request - missing required "
					+ "{authorization_url, client_id, redirect_ui, response_type, scope } information !!"));
		}	
	}
	
	@Test
	public void validateClientCredentialsConfigConnection() {
		
		boolean valid = false;
		
		String clientID = "88a73992-07f2-4714-ab4b-de782acd9c4d";
		String clientSecret = "a130adb7-aa51-49ac-9d02-0d4036b63541";
		
		String sslCertPath = "/AuthCodeUserInfoSampleApp/src/main/resources/certs/keystore.jks";
		String keyPassword = "adpadp10";
		String storePassword = "adpadp10";
		
		String tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		
		clientCredentialsConfiguration.setClientID(clientID);
		clientCredentialsConfiguration.setClientSecret(clientSecret);
		
		clientCredentialsConfiguration.setSslCertPath(sslCertPath);
		clientCredentialsConfiguration.setKeyPassword(keyPassword);
		clientCredentialsConfiguration.setStorePassword(storePassword);
		
		clientCredentialsConfiguration.setTokenServerUrl(tokenServerUrl);
		
		try {
			valid = instance.validate(clientCredentialsConfiguration);
			assertTrue(valid);
		} catch (ConnectionValidatorException e) {
			assertTrue(false);
		}
	}
	
	public void validateClientCredentialsConfigConnectionClientCredentialsNull() {
		
		boolean valid = false;
		
		String clientID = null;
		String clientSecret = "a130adb7-aa51-49ac-9d02-0d4036b63541";
		
		String sslCertPath = "/AuthCodeUserInfoSampleApp/src/main/resources/certs/keystore.jks";
		String keyPassword = "adpadp10";
		String storePassword = "adpadp10";
		
		String tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		
		clientCredentialsConfiguration.setClientID(clientID);
		clientCredentialsConfiguration.setClientSecret(clientSecret);
		
		clientCredentialsConfiguration.setSslCertPath(sslCertPath);
		clientCredentialsConfiguration.setKeyPassword(keyPassword);
		clientCredentialsConfiguration.setStorePassword(storePassword);
		
		clientCredentialsConfiguration.setTokenServerUrl(tokenServerUrl);
		
		try {
			valid = instance.validate(clientCredentialsConfiguration);
			assertTrue(false);
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("Either both or one of the Client Credentials is not populated!!"));
		}
	}

	@Test
	public void validateClientCredentialsConfigConnectionSSLFieldsNull() {
		
		boolean valid = false;
		
		String clientID = "88a73992-07f2-4714-ab4b-de782acd9c4d";
		String clientSecret = "a130adb7-aa51-49ac-9d02-0d4036b63541";
		
		String sslCertPath = null;
		String keyPassword = null;
		String storePassword = null;
		
		String tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		
		clientCredentialsConfiguration.setClientID(clientID);
		clientCredentialsConfiguration.setClientSecret(clientSecret);
		
		clientCredentialsConfiguration.setSslCertPath(sslCertPath);
		clientCredentialsConfiguration.setKeyPassword(keyPassword);
		clientCredentialsConfiguration.setStorePassword(storePassword);
		
		clientCredentialsConfiguration.setTokenServerUrl(tokenServerUrl);
		
		try {
			valid = instance.validate(clientCredentialsConfiguration);
			assertTrue(false);
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("One or more key SSL attributes are missing in request!!"));
		}
	}
	
	@Test
	public void validateClientCredentialsConfigConnectionTokenUrlNull() {
		
		boolean valid = false;
		
		String clientID = "88a73992-07f2-4714-ab4b-de782acd9c4d";
		String clientSecret = "a130adb7-aa51-49ac-9d02-0d4036b63541";
		
		String sslCertPath = "/AuthCodeUserInfoSampleApp/src/main/resources/certs/keystore.jks";
		String keyPassword = "adpadp10";
		String storePassword = "adpadp10";
		
		String tokenServerUrl = null;
		
		clientCredentialsConfiguration.setClientID(clientID);
		clientCredentialsConfiguration.setClientSecret(clientSecret);
		
		clientCredentialsConfiguration.setSslCertPath(sslCertPath);
		clientCredentialsConfiguration.setKeyPassword(keyPassword);
		clientCredentialsConfiguration.setStorePassword(storePassword);
		
		clientCredentialsConfiguration.setTokenServerUrl(tokenServerUrl);
		
		try {
			valid = instance.validate(clientCredentialsConfiguration);
			assertTrue(false);
		} catch (ConnectionValidatorException e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionValidatorException);
			assertTrue(e.getMessage().equals("Token URL is null or empty!"));
		}
	}
	
}
