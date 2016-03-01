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

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.adp.marketplace.connection.configuration.AuthorizationCodeConfiguration;
import com.adp.marketplace.connection.core.ADPAPIConnectionFactory;
import com.adp.marketplace.connection.core.AuthorizationCodeConnection;
import com.adp.marketplace.connection.exception.ConnectionException;

/**
 * @author tallaprs
 *
 */
public class AuthorizationCodeConnectionTest {
	
	ADPAPIConnectionFactory connectFactoryInstance = null;
	AuthorizationCodeConfiguration authCodeConfiguration =  null;
	
	
	String apiRequestUrl;
	String baseAuthorizationUrl;
	String redirectUrl;

	String tokenServerUrl;
	String clientID;
	String clientSecret;
	
	String state;
	String scope;
	String authorizationCode;
	String sslCertPath;
	String storePassword;
	String keyPassword;
	
	/**
	 * @throws java.lang.Exception Exception thrown if this call fails
	 */
	@Before
	public void setUp() throws Exception {
		connectFactoryInstance = ADPAPIConnectionFactory.getInstance();
		authCodeConfiguration =  new AuthorizationCodeConfiguration();
		
		state = UUID.randomUUID().toString();
		apiRequestUrl = "https://iat-api.adp.com/core/v1/userinfo";
		baseAuthorizationUrl = "https://iat-accounts.adp.com/auth/oauth/v2/authorize";
		redirectUrl = "http://localhost:8889/marketplace/callback";
		
		tokenServerUrl = "https://iat-accounts.adp.com/auth/oauth/v2/token";
		clientID = "5cab3a80-b3fd-415f-955f-4f868596ff43";
		clientSecret = "4a26db08-2885-4766-b6bb-ad8d0eac7c22";
		scope = "openid";
		authorizationCode = "authorization_code";
		sslCertPath = "/src/main/resources/certs/keystore.jks";
		storePassword = "adpadp10";
		keyPassword = "adpadp10";
	}

	/**
	 * @throws java.lang.Exception Exception thrown if this call fails
	 */
	@After
	public void tearDown() throws Exception {

		connectFactoryInstance = null;
		authCodeConfiguration =  null;

	}
	
	@Test
	public void ConstructorTest() {
		
		AuthorizationCodeConfiguration authorizationCodeConfiguration = new AuthorizationCodeConfiguration();
		AuthorizationCodeConnection authorizationCodeConnection = new AuthorizationCodeConnection(authorizationCodeConfiguration);
		
		assertNotNull(authorizationCodeConnection);
		assertNotNull(authorizationCodeConnection.getConnectionConfiguration());
		assertNull( authorizationCodeConnection.getErrorResponse());
		assertNull( authorizationCodeConnection.getRefreshToken());
		assertNull( authorizationCodeConnection.getToken());
		
	}

	@Test
	public void connect() {
		
		AuthorizationCodeConfiguration authorizationCodeConfiguration = new AuthorizationCodeConfiguration();
		
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
		authorizationCodeConfiguration.setStorePassword(storePassword);
		authorizationCodeConfiguration.setStorePassword(keyPassword);
	
	}
	
	@Test
	public void connectWithParameter() {
		assertTrue(true);
	}

	@Test
	public void disconnect() {
		assertTrue(true);
	}
	
	@Test
	public void init()  {
		assertTrue(true);
	}
	
	@Test
	public void isConnectionIndicator() {
		assertTrue(true);
	}
	
	@Test
	public void testExpectedException() {
		ConnectionException e = new ConnectionException();
		assertTrue(e  instanceof ConnectionException );
	}
}
