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
	------------------------------------------------------------------------------
*/
package com.adp.marketplace.core.connection.test;

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
import com.adp.marketplace.connection.configuration.ConnectionConfiguration;
import com.adp.marketplace.connection.constants.GrantType;
import com.adp.marketplace.connection.core.ADPAPIConnection;
import com.adp.marketplace.connection.core.ADPAPIConnectionFactory;
import com.adp.marketplace.connection.exception.ConnectionException;


/**
 * @author tallaprs
 *
 */
public class ADPAPIConnectionFactoryTest {

	ADPAPIConnectionFactory instance = null;
	
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
		instance = ADPAPIConnectionFactory.getInstance();;
	}

	/**
	 * @throws java.lang.Exception Exception thrown if this call fails
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 
	 * verifies that a singleton instance is created
	 */
	@Test
	public void singletonInstanceCreated() {
		
		ADPAPIConnectionFactory anotherInstance = ADPAPIConnectionFactory.getInstance();
		
		assertNotNull(instance);
		assertNotNull(anotherInstance);
		
		assertTrue(instance instanceof ADPAPIConnectionFactory);
		assertTrue(anotherInstance instanceof ADPAPIConnectionFactory);
		
		assertSame(instance, anotherInstance);
		assertEquals(instance, anotherInstance);
	}
	
	/**
	 * 
	 * verifies creation of ClientCredentialImpl object when grantTypeValue is set to 'client_credentials'
	 */
	@Test
	public void createClientCredentialsObject() {
		
		GrantType grantType = GrantType.CLIENT_CREDENTIALS;	
		
		try {
			
			ConnectionConfiguration connectionConfiguration = new ClientCredentialsConfiguration();
			
			ADPAPIConnection adpAPIConnection = ADPAPIConnectionFactory.getInstance().createConnection(connectionConfiguration);
			
			assertNotNull(adpAPIConnection);
			assertTrue(adpAPIConnection.getClass().getName().contains("ClientCredentialsConnection"));
				
		} catch (ConnectionException e) {
			assertTrue(false);
		} 
	}
	
	/**
	 * 
	 * verifies creation of AuthorizationCodeImpl object when grantTypeValue is set to 'auth_code'
	 */
	@Test
	public void createAuthorizationCodeObject() {
	
		try {
			
			ConnectionConfiguration connectionConfiguration = new AuthorizationCodeConfiguration();
			ADPAPIConnection adpAPIConnection = ADPAPIConnectionFactory.getInstance().createConnection(connectionConfiguration);
			
			assertNotNull(adpAPIConnection);
			assertTrue(adpAPIConnection.getClass().getName().contains("AuthorizationCodeConnection"));
			
		} catch (ConnectionException e) {
			assertTrue(false);
		} 
		
	}
	
	
	/**
	 * 
	 * verifies grantTypeValue is invalid and throws an exception 
	 */
	@Test
	public void invalidGrantType() {
		
		try {
			
			ConnectionConfiguration connectionConfiguration = null;
			ADPAPIConnection adpAPIConnection = ADPAPIConnectionFactory.getInstance().createConnection(connectionConfiguration);
		
		} catch (Exception e) {
			assertTrue(e.getClass().getSimpleName().equals("ConnectionException"));
			assertTrue(e instanceof ConnectionException);
		}
	}

}
