package com.adp.marketplace.connection.utils;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.adp.marketplace.connection.configuration.ClientCredentialsConfiguration;
import com.adp.marketplace.connection.exception.ConnectionException;
import com.adp.marketplace.connection.exception.ConnectionValidatorException;
import com.adp.marketplace.connection.vo.Token;

/**
 * @author tallaprs
 *
 */
public class SSLUtilsTest {
	
	SSLUtils instance = null;
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
		instance = SSLUtils.getInstance();
		clientCredentialsConfiguration = new ClientCredentialsConfiguration();
	}
	
	/**
	 * @throws java.lang.Exception Exception thrown if this call fails
	 */
	@After
	public void tearDown() throws Exception {
		instance = null;
		clientCredentialsConfiguration = null;
	}
	
	/**
	 * 
	 * verifies that a singleton instance is created for SSLUtils
	 */
	@Test
	public void singletonInstanceCreated() {
		
		 SSLUtils anotherInstance = SSLUtils.getInstance();
		
		assertNotNull(instance);
		assertNotNull(anotherInstance);
		
		assertTrue(instance instanceof SSLUtils);
		assertTrue(anotherInstance instanceof SSLUtils);
		
		assertSame(instance, anotherInstance);
		assertEquals(instance, anotherInstance);
	}

	/**
	 *  verifies valid https client is returned when request is valid
	 */
	@Test
	public void getHttpsClient() {
		
		String currentDirectory = "";
		
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
			
			CloseableHttpClient closeableHttpClient = instance.getHttpsClient(clientCredentialsConfiguration);
			
			assertNotNull(closeableHttpClient);
			
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	/**
	 *  verifies exceptions and error messages when SSL fields are null
	 */
	@Test
	public void getHttpsClientSSLFieldsInvalid() {
		
		String currentDirectory = "";
		Token token = null;
		
		//initializes the client specific configurations
		//build client configuration
		clientCredentialsConfiguration.setClientID("88a73992-07f2-4714-ab4b-de782acd9c4d");
		clientCredentialsConfiguration.setClientSecret("a130adb7-aa51-49ac-9d02-0d4036b63541");
		
		try {
			
			currentDirectory = (new java.io.File( "." ).getCanonicalPath());
			String concatSSLCertPath = currentDirectory.concat("//src/main/resources/certs/keystore.jks");
			
			clientCredentialsConfiguration.setSslCertPath(null);
			clientCredentialsConfiguration.setKeyPassword(null);	
			clientCredentialsConfiguration.setStorePassword("adpadp10");
			
			clientCredentialsConfiguration.setTokenServerUrl("https://iat-api.adp.com/auth/oauth/v2/token");
			
			CloseableHttpClient closeableHttpClient = instance.getHttpsClient(clientCredentialsConfiguration);
			
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue(e instanceof ConnectionException);
			assertTrue(e.getCause() instanceof ConnectionValidatorException);
			assertTrue(e.getCause().getMessage().equals("One or more key SSL attributes are missing in request!!"));
		}
	}
}

