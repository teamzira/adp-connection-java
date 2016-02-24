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

import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.adp.marketplace.connection.configuration.AuthorizationCodeConfiguration;
import com.adp.marketplace.connection.configuration.ClientCredentialsConfiguration;
import com.adp.marketplace.connection.configuration.ConnectionConfiguration;
import com.adp.marketplace.connection.constants.GrantType;
import com.adp.marketplace.connection.exception.ConnectionException;
import com.adp.marketplace.connection.exception.InvalidGrantTypeException;


/**
 * 
 * @author tallaprs
 *
 */
public class ADPAPIConnectionFactory {
	
	private static ADPAPIConnectionFactory INSTANCE = null;
	private static final Logger LOGGER = Logger.getLogger(ADPAPIConnectionFactory.class.getName());
	
	/**
	 * constructor
	 */
	private ADPAPIConnectionFactory() {}
	
	
	/**
	 * this method returns a singleton instance of ADPAPIConnectionFactory class
	 * 
	 * @return ADPAPIConnection an Instance of {@link ADPAPIConnectionFactory}
	 */
	public static ADPAPIConnectionFactory getInstance() {
		
		if ( INSTANCE == null ) {		
			synchronized (ADPAPIConnectionFactory.class) {
	            if ( INSTANCE == null ) {
	                INSTANCE = new ADPAPIConnectionFactory();
	            }
	        }
	    }
		
	    return INSTANCE;
	}

	/**
	 * Returns an instance of Configuration object based on the authorization grant type
	 * 
	 * @param grantType  			   {@link GrantType} 
	 * @return ConnectionConfiguration {@link ADPAPIConnection}
	 * @throws ConnectionException     throws a connection if grant type is empty or null
	 */
	public ConnectionConfiguration init(String grantType) throws ConnectionException {
		
		ConnectionConfiguration connectionConfiguration = null;
		
		if ( StringUtils.isBlank(grantType) ) {
			throw new ConnectionException(new IllegalArgumentException("Authorization Grant Type is null or empty, valid value must be provided"));
		}
		
		if ( grantType.equals(GrantType.CLIENT_CREDENTIALS.getValue()) ) {
			return new ClientCredentialsConfiguration();
		}
			
		if ( grantType.equals(GrantType.AUTHORIZATION_CODE.getValue()) ) {
			return new AuthorizationCodeConfiguration();
		}
			
		return connectionConfiguration;
	}
	
	/**
	 * this method returns an instance of Connection object based on the authorization configuration 
	 * 
	 * @param connectionConfiguration  configuration associated to the connection  
	 * @return ADPAPIConnection        returns an instance of 
	 *                                 ClientCredentialsConnection or AuthorizationCodeConnection
	 * @throws ConnectionException     throws exception if configuration is null, invalid
	 */
	public ADPAPIConnection createConnection(ConnectionConfiguration connectionConfiguration) 
		throws ConnectionException {
		
		ADPAPIConnection aDPAPIConnection = null;
		
		if ( connectionConfiguration == null ) { 
			throw new ConnectionException(new IllegalArgumentException("Connection Configuration cannot be null"));
		}
		
		GrantType grantType = connectionConfiguration.getGrantType() ;
		aDPAPIConnection = createConnection(connectionConfiguration.getGrantType(), connectionConfiguration);
		
		return aDPAPIConnection;
	}
		
	
	/**
	 * this method returns an instance of Connection object based on the authorization grant type 
	 * 
	 * @param grantTypeValue
	 * @return ADPAPIConnection
	 * @throws ConnectionException
	 */
	private ADPAPIConnection createConnection(GrantType grantType, ConnectionConfiguration connectionConfiguration) throws ConnectionException {
		
		try { 
			
			if ( grantType != null ) {
				
				if ( GrantType.AUTHORIZATION_CODE.name().equals(grantType.name()) ) {			
					return new AuthorizationCodeConnection(connectionConfiguration);
				} else if (GrantType.CLIENT_CREDENTIALS.name().equals(grantType.name()) ) {
					return new ClientCredentialsConnection(connectionConfiguration);
				} else {
					throw new IllegalArgumentException();
				}	
				
			} else {
				throw new ConnectionException(new IllegalArgumentException("GrantType cannot not be null or empty "));
			}
			
		} catch (IllegalArgumentException e) {
			throw new ConnectionException(new InvalidGrantTypeException("Allowed GrantTypes {client_credentials, authorization_code} one of which must be provided"));
		}

	}
	
}