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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;

import com.adp.marketplace.connection.configuration.AuthorizationCodeConfiguration;
import com.adp.marketplace.connection.configuration.ClientCredentialsConfiguration;
import com.adp.marketplace.connection.configuration.ConnectionConfiguration;
import com.adp.marketplace.connection.constants.Constants;
import com.adp.marketplace.connection.core.ADPAPIConnection;
import com.adp.marketplace.connection.core.AuthorizationCodeConnection;
import com.adp.marketplace.connection.core.ClientCredentialsConnection;
import com.adp.marketplace.connection.exception.ConnectionException;
import com.adp.marketplace.connection.vo.Token;
import com.google.gson.Gson;


/**
 * 
 * <p>
 * ConnectionUtils is a singleton utility class that provides convenience 
 * methods to build request and process response of connections
 * <p>
 * In case the connection or configuration is null or empty or invalid 
 * {@link ConnectionException} is thrown.
 * 
 * @author tallaprs
 *
 */
public class ConnectionUtils {
	
	private static ConnectionUtils INSTANCE = null;
	private static final Logger LOGGER = Logger.getLogger(ConnectionUtils.class.getName());
	
	/**
	 * Returns a lazy loaded singleton instance of {@link ConnectionUtils} 
	 * 
	 * @return ConnectionUtils a singleton instance
	 */
	public static ConnectionUtils getInstance() {
		
		if ( INSTANCE == null ) {		
			synchronized (SSLUtils.class) {
	            if ( INSTANCE == null ) {
	                INSTANCE = new ConnectionUtils();
	            }
	        }
	    }
		
	    return INSTANCE;
	}

	/**
	 * Returns a Authorization Url that is constructed from the configuration parameter
	 * 
	 * @param authorizationCodeConfig contains the authorization configuration 
	 * 								  attributes
	 * @return authorizationUrl		  authorization url string with required 
	 *                                parameters
	 * @throws ConnectionException 	  throws connection exception if missing 
	 *                                required configurations attributes
	 */
	@SuppressWarnings("javadoc")
	public static String buildAuthorizationUrl(AuthorizationCodeConfiguration 
		authorizationCodeConfig) throws ConnectionException {
		
		String authorizationUrl = null;
		
		StringBuilder baseAuthUrl = new StringBuilder(authorizationCodeConfig.getBaseAuthorizationUrl());
		StringBuilder clientID = new StringBuilder(authorizationCodeConfig.getClientID());
		StringBuilder redirectUrl = new StringBuilder(authorizationCodeConfig.getRedirectUrl());
		StringBuilder scope = new StringBuilder(authorizationCodeConfig.getScope());
		
		if  ( StringUtils.isBlank(baseAuthUrl) || 
				StringUtils.isBlank(clientID) ||
				StringUtils.isBlank(redirectUrl) || 
				StringUtils.isBlank(scope) ) {
			throw new ConnectionException(" Failed to Build Authorization Url "
					+ "as required field is null or empty");
		}
		
		StringBuilder sb = new StringBuilder(baseAuthUrl.toString());
				sb.append(Constants.QUESTION)
				.append(Constants.CLIENT_ID)
				.append(Constants.EQUALS)
				.append(clientID.toString().trim())
				.append(Constants.AMPERSAND)
				.append(Constants.RESPONSE_TYPE)
				.append(Constants.EQUALS)
				.append(authorizationCodeConfig.getResponseTypeValue())
				.append(Constants.AMPERSAND)
				.append(Constants.REDIRECT_URI)
				.append(Constants.EQUALS)
				.append(redirectUrl.toString().trim())
				.append(Constants.AMPERSAND)
				.append(Constants.SCOPE)
				.append(Constants.EQUALS)
				.append(scope.toString().trim())
				.append(Constants.AMPERSAND)
				.append(Constants.STATE)
				.append(Constants.EQUALS)
				.append(UUID.randomUUID());
		
		authorizationUrl = sb.toString();
		
		return authorizationUrl;
	}

	/**
	 * Returns a List of name value pairs consisting of client credentials and 
	 * grant type associated to this configuration
	 * 
	 * @param connectionConfiguration  type of connection configuration 
	 * @return list                    list contains client credentials, grant type
	 * @throws ConnectionException 	   throws exception if configuration is null
	 */
	public static List<NameValuePair> getClientCredentials(
		ConnectionConfiguration connectionConfiguration) 
		throws ConnectionException {
		
		if ( connectionConfiguration == null ) {
			throw new ConnectionException("Connection Configuration is Not Set in request!!");
		}
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		nameValuePairs.add(new BasicNameValuePair(Constants.CLIENT_ID, connectionConfiguration.getClientID()));
		nameValuePairs.add(new BasicNameValuePair(Constants.CLIENT_SECRET, connectionConfiguration.getClientSecret()));
		nameValuePairs.add(new BasicNameValuePair(Constants.GRANT_TYPE, connectionConfiguration.getGrantType().getValue()));
		
		return nameValuePairs;
	}
	
	/**
	 * Returns a List of name value pairs consisting of client credentials, code,
	 * redirect url and grant type associated to this connection
	 * 
	 * @param connection            connection type 
	 * @return list  				list contains client credentials, grant type, code,
	 * 								redirect Url
	 * @throws ConnectionException throws exception if connection is null or invalid
	 */
	public List<NameValuePair> getNameValuePairs(ADPAPIConnection connection) 
		throws ConnectionException {
		
		List<NameValuePair> nameValuePairs = null;
		
		if ( connection instanceof AuthorizationCodeConnection ) {
			nameValuePairs =  getNameValuePairs((AuthorizationCodeConnection) connection);
		} else if ( connection instanceof ClientCredentialsConnection ) {
			nameValuePairs =  getNameValuePairs((ClientCredentialsConnection) connection);
		}
		
		return nameValuePairs;
	}
	
	/**
	 * Returns a simple value object {@link Token} after processing JSON response
	 * obtained as part of the Http Post execution to token server.  
	 * 
	 * @param response	    contains JSON response from token server
	 * @return Token        value object to map access token, expiration time, 
	 *                      scope and token type
	 * throws 				// thrown if processing of IO stream fail
	 * @throws Exception 
	 */
	public static Token processTokenResponse(CloseableHttpResponse response) throws Exception {
		
		Gson gson = null;
		Token token = null;
		String tokenString = null;
			
		try {
			
			if ( response != null ) {
				
				tokenString = processResponse(response);
				
				gson = new Gson();
				if ( StringUtils.isNotBlank(tokenString)) {
					token = gson.fromJson(tokenString, Token.class);
				} 
			}		
		} catch ( UnsupportedOperationException e ) {
			throw new Exception(e);
		} catch ( IOException e ) {
			throw new Exception(e);
		} finally {
			try {
				if ( response != null) {
					response.close();
				}
			} catch (IOException e) {
				throw new Exception(e);
			}
		}

		return token;
	}
	
	/**
	 * 
	 * @param httpResponse
	 * @return String
	 * @throws IOException 
	 */
	public static String processResponse(CloseableHttpResponse httpResponse) 
		throws Exception {
			
		String line = "";
		String response = null;
		StringBuffer stringBuffer = null;
		InputStreamReader inputStreamReader = null;
		
		if ( httpResponse != null ) {
			
			try {
			
				inputStreamReader = new InputStreamReader(httpResponse.getEntity().getContent());
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
				stringBuffer = new StringBuffer();
				while ( (line = bufferedReader.readLine()) != null ) {
					stringBuffer.append(line);
				}
				
			} catch ( UnsupportedOperationException e ) {
				throw new Exception(e);
			} catch ( IOException e ) {
				throw new Exception(e);
			} finally {
				try {
					httpResponse.close();
				} catch (IOException e) {
					throw new Exception(e);
				}
			}
		}
		
		if ( stringBuffer != null && stringBuffer.length() > 0 ) {
			response = stringBuffer.toString();
		}
			
		return response;
	}
	
	/**
	 * Returns true if token hasn't expired since issued and false if token 
	 * has expired. Validity of token is determined based on delta of current 
	 * time and sum of token expiration time to token issued time
	 * 
	 * @param tokenTimeTracker has sum of token issued time and expiration time
	 * @return boolean         returns true if token is valid, false otherwise
	 */
	protected boolean computeIsValidToken(double tokenTimeTracker) {
		
		boolean isValidToken = false;
		double currentTimeTracker  = System.currentTimeMillis()/1000.0;
		
		if ( (currentTimeTracker - tokenTimeTracker) < 0 ) {
			isValidToken = true;
		} else if ( (currentTimeTracker - tokenTimeTracker) >= 0 ) {
			isValidToken = false;
		} 
		
		return isValidToken;
	}
	
	/**
	 * Returns a List of name value pairs consisting of client credentials, code, 
	 * grant type, redirect url associated to this configuration
	 * 
	 * @param connection			Authorization Code connection
	 * @return List<NameValuePair>  A list with client credentials, code, 
	 * 								grant type, redirect Url
	 * @throws ConnectionException 	throws exception if connection is null
	 */
	private List<NameValuePair> getNameValuePairs(
		AuthorizationCodeConnection connection) throws ConnectionException {

		String scope = null;
		String code = null;
		
		List<NameValuePair> nameValuePairs = null;
		
		if ( connection == null ) {
			throw new NullPointerException("Connection is Null!");
		}
	
		AuthorizationCodeConfiguration configuration = 
				(AuthorizationCodeConfiguration) connection.getConnectionConfiguration();
		
		scope = configuration.getScope();
		code = configuration.getAuthorizationCode();
		
		nameValuePairs = new ArrayList<NameValuePair>();
		
		// verify - required if scope required for api calls
		nameValuePairs.add(new BasicNameValuePair("scope", scope));
		
		//required for token server request
		nameValuePairs.add(new BasicNameValuePair(Constants.CODE, code));
		nameValuePairs.add(new BasicNameValuePair(Constants.REDIRECT_URI, configuration.getRedirectUrl()));
		nameValuePairs.addAll(ConnectionUtils.getInstance().getClientCredentials(configuration));

		return nameValuePairs;
	}
	
	/**
	 * Returns List<NameValuePair> contains client credentials, grant type
	 * 
	 * @param connection 			Client Credentials Connection
	 * @return List<NameValuePair>  A list with client credentials and grant type
	 * 								client credentials
	 * @throws ConnectionException  throws exception if connection is null
	 */
	private List<NameValuePair> getNameValuePairs(ClientCredentialsConnection 
		connection) throws ConnectionException {

		List<NameValuePair> nameValuePairs = null;
		
		if ( connection == null ) {
			throw new ConnectionException("Connection is Null!");
		}
	
		ClientCredentialsConfiguration configuration = 
				(ClientCredentialsConfiguration) connection.getConnectionConfiguration();

		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.addAll(ConnectionUtils.getInstance().getClientCredentials(configuration));

		return nameValuePairs;
	}
	
}