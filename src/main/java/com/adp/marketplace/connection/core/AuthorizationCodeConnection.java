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

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import com.adp.marketplace.connection.configuration.AuthorizationCodeConfiguration;
import com.adp.marketplace.connection.configuration.ConnectionConfiguration;
import com.adp.marketplace.connection.constants.Constants;
import com.adp.marketplace.connection.constants.GrantType;
import com.adp.marketplace.connection.exception.ConnectionException;
import com.adp.marketplace.connection.utils.ConnectionUtils;
import com.adp.marketplace.connection.utils.ConnectionValidatorUtils;
import com.adp.marketplace.connection.utils.SSLUtils;
import com.adp.marketplace.connection.vo.Token;


/**
 * 
 * <p>
 * AuthorizationCodeConnection is an implementation of {@link ADPAPIConnection} 
 * with {@link GrantType} authorization_code.  
 * </p>
 * 
 * <p>
 * AuthorizationCodeConnection exposes the common methods that are available for 
 * processing a authorization connection request
 * </p>
 * 
 * <p>
 * @see AuthorizationCodeConnection
 * @see ClientCredentialsConnection
 * @author tallaprs
 *
 */
public class AuthorizationCodeConnection implements ADPAPIConnection {
	
	private static final Logger LOGGER = Logger.getLogger(AuthorizationCodeConnection.class.getName());
	
	private boolean isConnectionAlive;
	private double tokenTimeTracker;

	private String errorResponse = null;
	private String authorizationUrl = null;
	private String refreshToken = null;
		
	private ConnectionConfiguration connectionConfiguration;
	private Token token;

	/**
	 * constructor
	 */
	protected AuthorizationCodeConnection() {
		super();
		connectionConfiguration = new AuthorizationCodeConfiguration();
	}
	
	/**
	 * constructor
	 * 
	 * @param connectionConfiguration  sets configuration to this connection
	 */
	public AuthorizationCodeConnection(ConnectionConfiguration connectionConfiguration) {
		super();
		this.connectionConfiguration = connectionConfiguration;
	}
	
	/**
	 * Initiates a connect request to ADP Authorization Server on the type of 
	 * connection configuration. Validations are performed on the connection 
	 * configurations and if request is valid access token will be issued 
	 * and {@link Token} is mapped with this connection. In case of error, 
	 * connection exceptions are thrown or error details are returned
	 * 
	 * @throws ConnectionException exception thrown when validation fails on configuration 
	 * 							   and connection
	 */
	@Override
	public void connect() throws ConnectionException {

		int responseStatusCode;
		StringBuffer sb = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		
		try {
			
			ConnectionValidatorUtils validatorInstance = ConnectionValidatorUtils.getInstance();
			boolean isValid = validatorInstance.validateAuthCodeTokenRequest(
					(AuthorizationCodeConfiguration) this.getConnectionConfiguration());
			
			if ( isValid ) {
				
				//create a https client
				httpClient = SSLUtils.getInstance().getHttpsClient(connectionConfiguration);
			
				//create POST request to acquire access token
				HttpPost post = new HttpPost(connectionConfiguration.getTokenServerUrl().trim());
			
				List<NameValuePair> nameValuePairs = ConnectionUtils.getInstance().getNameValuePairs(this);
			
				//map Client credentials and grant types to post request
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
				//get POST response
				httpResponse = httpClient.execute(post);
			
				if ( httpResponse != null ) {		
					
					responseStatusCode = httpResponse.getStatusLine().getStatusCode();
					
					if ( responseStatusCode == Constants.HTTP_SUCCESS ) {
						
						isConnectionAlive = true;
						
						// set token 
						token = ConnectionUtils.processTokenResponse(httpResponse);
						this.setToken(token);
						
						// set time tracker based on expiration
						this.tokenTimeTracker = (System.currentTimeMillis() / 1000.0) + token.getExpires_in();
						
					} else 	{
						
						if ( responseStatusCode == Constants.HTTP_CLIENT_ERROR  ||
						     responseStatusCode == Constants.HTTP_CLIENT_INVALID ||
						     responseStatusCode == Constants.HTTP_SERVER_ERROR )  {
							
							 this.errorResponse = ConnectionUtils.processResponse(httpResponse);
							 String httpStatusLine = httpResponse.getStatusLine().toString();
							 if ( StringUtils.isNotBlank(httpStatusLine)) {
								 
								 sb = new StringBuffer( httpStatusLine )
									 			.append(" ")
									 			.append(errorResponse);
							 	this.errorResponse = sb.toString();
							 }
						}
					}
				}
			}
			
		} catch (Exception e) {
			throw new ConnectionException(e);
		} finally {
			try {
				if ( httpResponse != null ) {	
					httpResponse.close();
				}
				if ( httpClient != null ) {
					httpClient.close();
				}
			} catch (IOException e) {
				throw new ConnectionException(e);
			}	
		}
	}
	
	/**
	 * Invokes API to disconnect the connection - currently this method is implemented 
	 * to reset the connection to null.
	 *  
	 *  @throws ConnectionException  throws exceptions in the event of object 
	 */
	@Override
	public void disconnect() throws ConnectionException {
		
		this.isConnectionAlive = false;
		this.token = new Token();
		this.tokenTimeTracker = 0.0;
		this.connectionConfiguration = null;
	}

	/**
	 * Returns Authorization Url after building it from configuration 
	 * 
	 * @return String authorization Url authorization Url for redirect
	 * @throws ConnectionException  	throws exception if connection 
	 * 									configuration is null
	 */
	public String getAuthorizationUrl() throws ConnectionException {
		
		boolean valid = false;
		
		if ( this.connectionConfiguration == null ) {
			throw new ConnectionException("Connection Configuration is Null!");
		}
		
		try {
			
			ConnectionValidatorUtils validatorInstance =
						ConnectionValidatorUtils.getInstance();
			AuthorizationCodeConfiguration authorizationCodeConfig =	
					(AuthorizationCodeConfiguration) this.getConnectionConfiguration();
			
			valid = validatorInstance.
				validateAuthCodeAuthUrl(authorizationCodeConfig);

			if ( valid ) {	
				this.authorizationUrl = ConnectionUtils.buildAuthorizationUrl(authorizationCodeConfig);
			}
			
		} catch (Exception e) {
			throw new ConnectionException(e);
		}

		return authorizationUrl;
	} 

	/**
	 *  Returns true or false based on the validation or access token.
	 *  
	 *  @return boolean  returns true when token is valid and not expired
	 */
	@Override
	public boolean isConnectionIndicator()  {		
		
		if ( this.token == null ) {
			isConnectionAlive = false;
		} else {
			if ( StringUtils.isBlank(token.getAccess_token()) || !isValidToken() ) {
				isConnectionAlive = false;
			}
		}
		
		return isConnectionAlive;
	}
	
	/**
	 * Returns a new token from server if the current token is still valid and 
	 * hasn't expired
	 * 
	 * @return String
	 */
	public String getRefreshToken() {
		//unimplemented for this iteration
		return refreshToken;
	}

	/**
	 * Returns the Connection configuration 
	 *  
	 * @return Connection Configuration  returns this connection configuration
	 */
	public ConnectionConfiguration getConnectionConfiguration() {
		return connectionConfiguration;
	}

	/**
	 * Maps the connection configuration {@link AuthorizationCodeConfiguration} 
	 * to this connection
	 * 
	 * @param connectionConfiguration maps AuthorizationCodeConfiguration to 
	 * 								  this connection configuration
	 */
	public void setConnectionConfiguration(ConnectionConfiguration connectionConfiguration) {
		this.connectionConfiguration = connectionConfiguration;
	}
	
	/**
	 *  Returns the token on this connection
	 *  
	 *  @return Token 
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets the tokens on this connection
	 * 
	 * @param token sets token to this connection
	 */
	public void setToken(Token token) {
		this.token = token;
	}
	
	/**
	 * Returns errors
	 * 
	 * @return String error message
	 */
	public String getErrorResponse() {
		return errorResponse;
	}

	/**
	 * 
	 * @param errorResponse sets the http error status
	 */
	public void setErrorResponse(String errorResponse) {
		this.errorResponse = errorResponse;
	}

	/**
	 * Returns true if token is still valid since issued based on delta of 
	 * current time and token time tracker that has the token expired time
	 * 
	 * @return boolean 
	 */
	private boolean isValidToken() {
		
		boolean valid = false;
		double currentTime = (System.currentTimeMillis() / 1000.0);
		
		if ( StringUtils.isNotBlank(token.getAccess_token()) || 
				( currentTime < this.tokenTimeTracker ) ) {
			valid = true;
		} 
		
		return valid; 
	}

}
