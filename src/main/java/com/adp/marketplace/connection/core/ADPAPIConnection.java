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
package com.adp.marketplace.connection.core;

import com.adp.marketplace.connection.constants.GrantType;
import com.adp.marketplace.connection.exception.ConnectionException;
import com.adp.marketplace.connection.vo.Token;


/**
 * <p>
 * ADPAPIConnection is an interface for all Connection Types instantiated based
 * on {@link GrantType} which allows an application to configure connections 
 * </p>
 * 
 * <p>
 * ADPAPIConnection exposes the common methods that are available for 
 * connection types.
 * </p>
 * 
 * <p>
 * @see AuthorizationCodeConnection
 * @see ClientCredentialsConnection
 * 
 * @author tallaprs
 *
 */
public interface ADPAPIConnection {
	
	/**
	 * <p>
	 * Initiates a connect request to ADP Authorization Server or Token Server based 
	 * on the type of connection configuration. Validations are performed on the 
	 * connection configurations and if request is valid access token will be issued 
	 * and {@link Token} is mapped with this connection. In case of error, 
	 * connection exceptions are thrown or error details are returned
	 * </p>
	 * 
	 * @throws ConnectionException A connection exception is thrown in case of 
	 *                             incomplete configuration,invalid configurations 
	 *                             data passed or server error
	 */
	public void connect() throws ConnectionException;
	
	/**
	 * Invokes API to disconnect the connection - currently this method is 
	 * implemented  to reset the connection to null
	 * 
	 * @throws ConnectionException A connection exception is thrown in case of 
	 *                             incomplete configuration,invalid configurations 
	 *                             data passed or server error
	 */
	public void disconnect() throws ConnectionException;
	
	/**
	 * Returns a Token associated with this connection 
	 * 
	 * @return Token a simple value object with token server response 
	 * @see Token 
	 */
	public Token getToken();
	
	/**
	 * Returns true by verifying if token is present in the connection and 
	 * if token is not expired since time of issue. Returns false otherwise. 
	 * 
	 * @return boolean
	 */
	public boolean isConnectionIndicator();
	
}
