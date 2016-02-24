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
import com.adp.marketplace.connection.core.ADPAPIConnection;


/**
 * <p>
 * ConnectionConfiguration is the abstract base class for all grant types 
 * {@link GrantType} which allows an application to configure connections 
 * {@link ADPAPIConnection} based on OAuth2.0 recognized Grant Types.
 * </p>
 * 
 * @author tallaprs
 *
 */
public abstract class ConnectionConfiguration {

	protected long tokenExpiration = 0;
	
	protected String clientID = "";
	protected String clientSecret = "";
	
	protected String sslCertPath = "";
	
	protected String keyPassword = "";
	protected String storePassword = "";
	
	protected String tokenServerUrl = "";
	protected String apiRequestUrl = "";
	
	protected GrantType grantType;

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
	 * @return clientID  the client id populated in this {@link 
	 *                   ConnectionConfiguration} connection configuration
	 */
	public String getClientID() {
		return clientID;
	}

	/**
	 * 
	 * @param clientID the client id associated with this connection 
	 *                 configuration
	 */
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	/**
	 * 
	 * @return clientSecret the client secret populated in this connection 
	 *                      configuration
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * 
	 * @param clientSecret the client secret associated with this connection 
	 *                     configuration
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
	 * @param apiRequestUrl the absolute URL string to the resource server
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
	public GrantType getGrantType() {
		return grantType;
	}

	/**
	 * 
	 * @param grantType  the grant type set to this connection configuration
	 * @see    GrantType
	 */
	protected void setGrantType(GrantType grantType) {
		this.grantType = grantType;
	}

	/**
	 * @return String the String representation of this {@link ConnectionConfiguration} object
	 */
	@Override
	public String toString() {
		return "ConnectionConfiguration [tokenExpiration=" + tokenExpiration + ", clientID=" + clientID
				+ ", clientSecret=" + clientSecret + ", sslCertPath=" + sslCertPath + ", keyPassword=" + keyPassword
				+ ", storePassword=" + storePassword + ", tokenServerUrl=" + tokenServerUrl + ", apiRequestUrl="
				+ apiRequestUrl + ", grantType=" + grantType + ", getTokenExpiration()=" + getTokenExpiration()
				+ ", getClientID()=" + getClientID() + ", getClientSecret()=" + getClientSecret()
				+ ", getSslCertPath()=" + getSslCertPath() + ", getKeyPassword()=" + getKeyPassword()
				+ ", getStorePassword()=" + getStorePassword() + ", getTokenServerUrl()=" + getTokenServerUrl()
				+ ", getApiRequestUrl()=" + getApiRequestUrl() + ", getGrantType()=" + getGrantType() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
