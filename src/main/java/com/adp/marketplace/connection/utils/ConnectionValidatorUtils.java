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

import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.adp.marketplace.connection.configuration.AuthorizationCodeConfiguration;
import com.adp.marketplace.connection.configuration.ConnectionConfiguration;
import com.adp.marketplace.connection.exception.ConnectionValidatorException;


/**
 * <p>
 * ConnectionValidatorUtils is a singleton utility class that provides 
 * convenience methods to perform validations on connection configurations
 * and returns true if valid and false otherwise. 
 * <p>
 * In case the connection or configurations are null or empty or invalid 
 * {@link ConnectionValidatorException} are thrown.
 * 
 * 
 * @author tallaprs
 *
 */
public class ConnectionValidatorUtils {
	
	private static ConnectionValidatorUtils INSTANCE = null;
	
	private static final Logger LOGGER = Logger.getLogger(ConnectionValidatorUtils.class.getName());
	
	/**
	 * Returns a lazy loaded singleton instance of {@link ConnectionValidatorUtils} 
	 * 
	 * @return ConnectionValidatorUtils a singleton instance
	 */
	public static ConnectionValidatorUtils getInstance() {
		
		if ( INSTANCE == null ) {		
			synchronized (SSLUtils.class) {
	            if ( INSTANCE == null ) {
	                INSTANCE = new ConnectionValidatorUtils();
	            }
	        }
	    }
		
	    return INSTANCE;
	}
	
	/**
	 * Returns true if configuration has required SSL attributes, client 
	 * credentials and valid url's, false otherwise or validation exceptions.
	 * 
	 * @param connectionConfiguration       has the configuration details for 
	 * 										this ConnectionConfiguration
	 * @return boolean						returns true if validation passed, 
	 * 										false otherwise
	 * @throws ConnectionValidatorException Exception thrown in case of missing 
	 * 										certificate file, invalid file path 
	 * 										or invalid trust store or key store, 
	 * 										missing client credentials or 
	 * 										authorization code required 
	 * 										parameters or null or empty connection 
	 * 										or configurations
	 */
	public boolean validate(ConnectionConfiguration connectionConfiguration) 
		throws ConnectionValidatorException {
		
		boolean valid = true;
		
		if ( connectionConfiguration == null ) {
			//valid = false;
			//TODO - Log and verify exception
			throw new ConnectionValidatorException("Connection Configuration is Not Set in request!!");
		}
		
		boolean isValidSSL = validateSSLFields(connectionConfiguration);
		boolean isValidClientCredentials = validateClientCredentials(false, connectionConfiguration);
		boolean isValidURLs = validateUrls(connectionConfiguration);
		
		if ( isValidSSL && isValidClientCredentials && isValidURLs ) {
			return valid;
		}
		
		return valid;
	}

	/**
	 * Returns true if {@link AuthorizationCodeConfiguration} has client credentials and 
	 * required authorization code parameters, false otherwise
	 * 
	 * @param connectionConfiguration  		has the configuration details for AuthorizationCode
	 * 								   		connection 
	 * @return boolean valid 		   		returns true if validation passed, false otherwise
	 * @throws ConnectionValidatorException Exception thrown in case of missing certificate 
	 *                             			file, invalid file path or invalid trust store or key 
	 *                             			store, missing client credentials or authorization 
	 *                                      code required parameters or null or empty connection 
	 *                                      or configurations
	 */
	public boolean validateAuthCodeAuthUrl(AuthorizationCodeConfiguration connectionConfiguration) 
			throws ConnectionValidatorException {
	
		boolean valid = true;
		
		if ( connectionConfiguration == null ) {
			throw new ConnectionValidatorException("Connection Configuration is Not Set in request!!");
		}
		
		boolean isValidClientCredentials = validateClientCredentials(true, connectionConfiguration);
		boolean isValidAuthUrl = validateAuthCodeAuthUrlParams(connectionConfiguration);
		
		if ( isValidClientCredentials && isValidAuthUrl ) {
			return valid;
		}
		
		return valid;
	}
	
	/**
	 * Returns true if {@link AuthorizationCodeConfiguration} has required ssl 
	 * attributes, client credentials and required authorization code, url 
	 * parameters, false otherwise
	 * 
	 * @param connectionConfiguration  		has the configuration details for AuthorizationCode
	 * 								   		connection 
	 * @return boolean valid 		   		returns true if validation passed, false otherwise
	 * @throws ConnectionValidatorException Exception thrown in case of missing certificate 
	 *                             			file, invalid file path or invalid trust store or key 
	 *                             			store, missing client credentials or authorization 
	 *                                      server required parameters or null or empty connection 
	 *                                      or configurations 
	 */
	public boolean validateAuthCodeTokenRequest(AuthorizationCodeConfiguration connectionConfiguration) 
			throws ConnectionValidatorException {
		
		boolean valid = true;
		
		if ( connectionConfiguration == null ) {
			//valid = false;
			//TODO - Log and verify exception
			throw new ConnectionValidatorException("Connection Configuration is Not Set in request!!");
		}
		
		boolean isValidSSL = validateSSLFields(connectionConfiguration);
		boolean isValidClientCredentials = validateClientCredentials(true, connectionConfiguration);
		boolean isValidURLs = validateAuthCodeTokenUrlParams(connectionConfiguration);
		
		if ( isValidSSL && isValidClientCredentials && isValidURLs ) {
			return valid;
		}
		
		return valid;
		
	}
	
	/**
	 * Returns true if client credentials are not empty, false otherwise
	 * 
	 * @param ignoreClientSecret
	 * @param connectionConfiguration
	 * @return
	 * @throws ConnectionValidatorException
	 */
	private boolean validateClientCredentials(boolean ignoreClientSecret, 
		ConnectionConfiguration connectionConfiguration) 
		throws ConnectionValidatorException {
		
		boolean valid = true;
		
		if ( connectionConfiguration == null ) {
			//valid = false;
			//TODO - Log and verify exception
			throw new ConnectionValidatorException("Connection Configuration is "
					+ "Not Set in request!!");
		}
		
		if ( !ignoreClientSecret ) {
			if ( StringUtils.isBlank(connectionConfiguration.getClientID()) ||
					StringUtils.isBlank(connectionConfiguration.getClientSecret()) ) {
				//valid = false;
				throw new ConnectionValidatorException("Either both or one of "
						+ "the Client Credentials is not populated!!");
			}
		} else {
			if ( StringUtils.isBlank(connectionConfiguration.getClientID()) ) {
				//valid = false;
				throw new ConnectionValidatorException("Client ID is not populated!!");
			}
		}
		
		return valid;
	}
	
	/**
	 * Returns true if valid, false otherwise and exception in case of error
	 * 
	 * @param connectionConfiguration
	 * @return
	 * @throws ConnectionValidatorException 
	 */
	private boolean validateAuthCodeAuthUrlParams(
			AuthorizationCodeConfiguration connectionConfiguration) 
			throws ConnectionValidatorException {
		
		boolean valid = true;
		
		//TODO - verify handle error or do what?
		if ( connectionConfiguration == null ) {
			//valid = false;
			//TODO - Log and verify exception
			throw new ConnectionValidatorException("Connection Configuration "
					+ "is Not Set in request!!");
		}
		
		//TODO - verify required fileds and handle error or do what?
		if ( StringUtils.isBlank(connectionConfiguration.getBaseAuthorizationUrl()) ||
			 StringUtils.isBlank(connectionConfiguration.getRedirectUrl()) || 
			 StringUtils.isBlank(connectionConfiguration.getClientID()) ||
			 StringUtils.isBlank(connectionConfiguration.getScope()) ) {
			//valid = false;
			throw new ConnectionValidatorException("Invalid Authorization "
					+ "Request - missing required {authorization_url, client_id, "
					+ "redirect_ui, response_type, scope } information !!" );
		}
		
		return valid;
	}

	/**
	 * Returns true if valid, false otherwise and exception in case of errors
	 * 
	 * @param connectionConfiguration
	 * @return boolean valid
	 */
	private boolean validateAuthCodeTokenUrlParams(AuthorizationCodeConfiguration 
		connectionConfiguration) throws ConnectionValidatorException {
	
		boolean valid = true;
		
		if ( connectionConfiguration == null ) {
			//valid = false;
			//TODO - Log and verify exception
			throw new ConnectionValidatorException("Connection Configuration is Not Set in request!!");
		}
		
		if ( StringUtils.isBlank(connectionConfiguration.getAuthorizationCode()) ||
			 StringUtils.isBlank(connectionConfiguration.getScope()) || 
			 StringUtils.isBlank(connectionConfiguration.getRedirectUrl()) ||
			 StringUtils.isBlank(connectionConfiguration.getTokenServerUrl()) ) {
			//valid = false;
			throw new ConnectionValidatorException("Invalid Authorization Request - missing required "
					+ "{authorization_url, client_id, redirect_ui, response_type, scope } information !!" );
		}
		
		return valid;
	}

	/**
	 * Returns true if valid, false otherwise and exception in case of error
	 * 
	 * @param connectionConfiguration
	 * @return
	 * @throws Exception
	 */
	protected boolean validateSSLFields(ConnectionConfiguration connectionConfiguration) 
		throws ConnectionValidatorException {
		
		boolean valid = true;
		
		if ( connectionConfiguration == null ) {
			//valid = false;
			throw new ConnectionValidatorException("Connection Configuration is Not Set in request!!");
		}
		
		if ( StringUtils.isBlank(connectionConfiguration.getSslCertPath()) ||
				StringUtils.isBlank(connectionConfiguration.getStorePassword()) ||
				StringUtils.isBlank(connectionConfiguration.getKeyPassword()) ) {
			//valid = false;
			//TODO - Log and verify exception
			throw new ConnectionValidatorException("One or more key SSL attributes are missing in request!!");
		}
	
		return valid;
	}
	
	/**
	 * Returns true if valid, false otherwise and exception in case of error
	 * 
	 * @param connectionConfiguration
	 * @return
	 * @throws ConnectionValidatorException
	 */
	private boolean validateUrls(ConnectionConfiguration connectionConfiguration) 
		throws ConnectionValidatorException {
		
		boolean valid = true;
		
		if ( connectionConfiguration == null ) {
			//valid = false;
			throw new ConnectionValidatorException("Connection Configuration is Not Set in request!!");
		}
		
		if ( StringUtils.isBlank(connectionConfiguration.getTokenServerUrl()) ) {
			//valid = false;
			//TODO - Log and verify exception
			throw new ConnectionValidatorException("Token URL is null or empty!" );
		}
	
		return valid;
	}

}
