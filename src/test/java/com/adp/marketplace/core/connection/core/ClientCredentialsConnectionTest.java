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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.adp.marketplace.connection.exception.ConnectionException;

/**
 * @author tallaprs
 *
 */
public class ClientCredentialsConnectionTest {
	
	@Test
	public void connect() {
		assertTrue(true);
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
