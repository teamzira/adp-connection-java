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
package com.adp.marketplace.connection.configuration;

import com.adp.marketplace.connection.constants.GrantType;


/**
 * <p>
 * ClientCredentialsConfiguration is an implementation class of base abstract 
 * class {@link ConnectionConfiguration} which allows an application to 
 * configure connection with {@link GrantType} <b>client_credentials</b> based 
 * on OAuth 2.0 Specification.
 * </p>
 * 
 * @author tallaprs
 *
 */
public final class ClientCredentialsConfiguration extends ConnectionConfiguration {
	
	/**
	 * default grant type value for this {@link ClientCredentialsConfiguration}
	 */
	public final static String grantTypeValue = GrantType.CLIENT_CREDENTIALS.getValue();
	
	/**
	 * constructor default grant type set to <b>client_credentials</b>
	 */
	public ClientCredentialsConfiguration() {
		super();
		super.setGrantType(GrantType.CLIENT_CREDENTIALS);
		this.setGrantType(GrantType.CLIENT_CREDENTIALS);
	}

	/**
	 * 
	 * @return token expiration time in seconds
	 */
	public long getTokenExpiration() {
		return tokenExpiration;
	}

	/**
	 * 
	 * @param tokenExpiration the token expiration time in seconds
	 */
	public void setTokenExpiration(long tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}

	/**
	 * 
	 * @return clientID  the client id populated in this connection
	 * 					 {@link ClientCredentialsConfiguration} 
	 */
	public String getClientID() {
		return clientID;
	}

	/**
	 * 
	 * @param clientID the client id associated with this connection 
	 *                 {@link ClientCredentialsConfiguration} 
	 */
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	/**
	 * 
	 * @return clientSecret the client secret populated in this connection 
	 *                       {@link ClientCredentialsConfiguration} 
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * 
	 * @param clientSecret the client secret associated with this connection 
	 *                      {@link ClientCredentialsConfiguration} 
	 */
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	/**
	 * 
	 * @return sslCertPath the file path location to the SSL certificate
	 */
	public String getSslCertPath() {
		return sslCertPath;
	}

	/**
	 * 
	 * @param sslCertPath the file path location of ssl certificate
	 */
	public void setSslCertPath(String sslCertPath) {
		this.sslCertPath = sslCertPath;
	}

	/**
	 * 
	 * @return keyPassword the password associated with the key store
	 */
	public String getKeyPassword() {
		return keyPassword;
	}

	/**
	 * 
	 * @param keyPassword the password provided to access the key store
	 */
	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
	}

	/**
	 * 
	 * @return storePassword the password associated with the store
	 */
	public String getStorePassword() {
		return storePassword;
	}

	/**
	 * 
	 * @param storePassword the password provided to access the store
	 */
	public void setStorePassword(String storePassword) {
		this.storePassword = storePassword;
	}

	/**
	 * 
	 * @return tokenServerUrl an absolute URL string to access the token server 
	 */
	public String getTokenServerUrl() {
		return tokenServerUrl;
	}

	/**
	 * 
	 * @param tokenServerUrl the absolute URL string to access the token server
	 */
	public void setTokenServerUrl(String tokenServerUrl) {
		this.tokenServerUrl = tokenServerUrl;
	}

	/**
	 * 
	 * @return apiRequestUrl an absolute URL string to access the resource server
	 */
	public String getApiRequestUrl() {
		return apiRequestUrl;
	}

	/**
	 * 
	 * @param apiRequestUrl the absolute URL string to access the resource server
	 */
	public void setApiRequestUrl(String apiRequestUrl) {
		this.apiRequestUrl = apiRequestUrl;
	}
	
	/**
	 * 
	 * @return GrantType the grant type associated with the connection 
	 *                   configuration
	 * @see    GrantType
	 */
	public static String getGrantTypeValue() {
		return grantTypeValue;
	}

	/**
	 * @return String the String representation of this {@link ClientCredentialsConfiguration} object
	 */
	@Override
	public String toString() {
		return "ClientCredentialsConfiguration [tokenExpiration=" + tokenExpiration + ", clientID=" + clientID
				+ ", clientSecret=" + clientSecret + ", sslCertPath=" + sslCertPath + ", keyPassword=" + keyPassword
				+ ", storePassword=" + storePassword + ", tokenServerUrl=" + tokenServerUrl + ", apiRequestUrl="
				+ apiRequestUrl + ", grantType=" + grantType + ", getTokenExpiration()=" + getTokenExpiration()
				+ ", getClientID()=" + getClientID() + ", getClientSecret()=" + getClientSecret()
				+ ", getSslCertPath()=" + getSslCertPath() + ", getKeyPassword()=" + getKeyPassword()
				+ ", getStorePassword()=" + getStorePassword() + ", getTokenServerUrl()=" + getTokenServerUrl()
				+ ", getApiRequestUrl()=" + getApiRequestUrl() + ", getGrantType()=" + getGrantType() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
