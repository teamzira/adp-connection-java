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
import com.adp.marketplace.connection.constants.ResponseType;
import com.adp.marketplace.connection.constants.Scope;


/**
 * <p>
 * AuthorizationCodeConfiguration is an implementation class of base abstract 
 * class {@link ConnectionConfiguration} which allows an application to 
 * configure connection with {@link GrantType} <b>authorization_code</b> based 
 * on OAuth 2.0 Specification.
 * </p>
 * 
 * @author tallaprs
 *
 */
public final class AuthorizationCodeConfiguration extends ConnectionConfiguration {
	
	/**
	 * default grant type for this {@link AuthorizationCodeConfiguration}
	 */
	private final static GrantType grantType = GrantType.AUTHORIZATION_CODE;
	public final static String grantTypeValue = grantType.getValue();
	
	/**
	 * default response type for this {@link AuthorizationCodeConfiguration}
	 */
	private final static ResponseType responseType = ResponseType.CODE;
	public final static String responseTypeValue = responseType.getValue();
	
	/**
	 * API scopes for this {@link AuthorizationCodeConfiguration} 
	 * @see Scope
	 */
	public static final String scopeApi = Scope.API.getValue();
	
	/**
	 * API scopes for this {@link AuthorizationCodeConfiguration} 
	 * @see Scope
	 */
	public static final String scopeOpenId = Scope.OPEN_ID.getValue();
	
	/**
	 * API scopes for this {@link AuthorizationCodeConfiguration} 
	 * @see Scope
	 */
	public static final String scopeProfile = Scope.PROFILE.getValue();
	
	/**
	 * randomly generated {@link UUID} string used as an identifier to each
	 * connection
	 */
	private double state;
	
	private String baseAuthorizationUrl = "";
	private String disconnectUrl = "";
	private String redirectUrl = "";
	private String authorizationCode = "";
	private String scope = "";
	
	/**
	 * constructor  default grant type set to <b>authorization_code</b> and 
	 *              default scope set to <b>openid</b>
	 */
	public AuthorizationCodeConfiguration() {
		super();
		super.setGrantType(GrantType.AUTHORIZATION_CODE);
		this.scope = scopeOpenId;
	}

	/**
	 * 
	 * @return double the generated state value
	 */
	public double getState() {
		return state;
	}

	/**
	 * 
	 * @param state variable
	 */
	public void setState(double state) {
		this.state = state;
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
	 *                   {@link AuthorizationCodeConfiguration}
	 */
	public String getClientID() {
		return clientID;
	}

	/**
	 * 
	 * @param clientID the client id associated with this connection 
	 *                 {@link AuthorizationCodeConfiguration}
	 */
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	/**
	 * 
	 * @return clientSecret the client secret populated in this connection 
	 *                      {@link AuthorizationCodeConfiguration}
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * 
	 * @param clientSecret the client secret associated with this connection 
	 *                     {@link AuthorizationCodeConfiguration}
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
	 * @return GrantType the grant type associated with this connection 
	 *                   configuration
	 * @see    GrantType
	 */
	public GrantType getGrantType() {
		return grantType;
	}

	/**
	 * 
	 * @return ResponseType the response type associated with this connection 
	 *                   configuration
	 * @see    ResponseType
	 */
	public ResponseType getResponseType() {
		return responseType;
	}

	/**
	 * 
	 * @return baseAuthorizationUrl an absolute URL string to access the 
	 *                              authorization server
	 */
	public String getBaseAuthorizationUrl() {
		return baseAuthorizationUrl;
	}

	/**
	 * 
	 * @param baseAuthorizationUrl the absolute URL string to access 
	 *                             authorization server
	 */
	public void setBaseAuthorizationUrl(String baseAuthorizationUrl) {
		this.baseAuthorizationUrl = baseAuthorizationUrl;
	}
	
	/**
	 * 
	 * @return disconnectUrl an absolute URL string to access the 
	 *                       server for disconnect
	 */
	public String getDisconnectUrl() {
		return disconnectUrl;
	}

	/**
	 * 
	 * @param disconnectUrl the absolute URL string to access server for 
	 *                      disconnect
	 */
	public void setDisconnectUrl(String disconnectUrl) {
		this.disconnectUrl = disconnectUrl;
	}

	/**
	 * 
	 * @return redirectUrl an absolute URL string to redirect to non ADP 
	 *                       server 
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * 
	 * @param redirectUrl the absolute URL string to redirect to non ADP server
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/**
	 * 
	 * @return authorizationCode the authorization code generated by 
	 *                           authorization server
	 */
	public String getAuthorizationCode() {
		return authorizationCode;
	}

	/**
	 * 
	 * @param authorizationCode the generated value of authorization code
	 */
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	/**
	 * 
	 * @return scope  the default scope of <b>openid</b> or any additional scopes 
	 *                appended to default scope as {@code &api&profile}
	 * @see Scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * 
	 * @param scope the default scope of <b>openid</b> or additional scopes
	 *              appended to default scope as {@code &api&profile}
	 * @see Scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	/**
	 * 
	 * @return responseTypeValue 'code' is returned for this configuration
	 */
	public static String getResponseTypeValue() {
		return responseTypeValue;
	}

	/**
	 * 
	 * @return grantTypeValue returns 'authorization_code' for this configuration
	 */
	public static String getGrantTypeValue() {
		return grantTypeValue;
	}

	/**
	 * 
	 * @return scopeApi scope 'api' is returned
	 * @see Scope
	 */
	public static String getScopeApi() {
		return scopeApi;
	}

	/**
	 * 
	 * @return scopeOpenId scope 'openid' is returned
	 * @see Scope
	 */
	public static String getScopeOpenId() {
		return scopeOpenId;
	}

	/**
	 * 
	 * @return scopeProfile  scope 'profile' is returned
	 * @see Scope
	 */
	public static String getScopeProfile() {
		return scopeProfile;
	}

	/**
	 * @return String the String representation of this {@link AuthorizationCodeConfiguration} object
	 */
	@Override
	public String toString() {
		return "AuthorizationCodeConfiguration [state=" + state + ", grantType=" + grantType + ", baseAuthorizationUrl="
				+ baseAuthorizationUrl + ", disconnectUrl=" + disconnectUrl + ", redirectUrl=" + redirectUrl
				+ ", authorizationCode=" + authorizationCode + ", scope=" + scope + ", responseType=" + responseType
				+ ", tokenExpiration=" + tokenExpiration + ", clientID=" + clientID + ", clientSecret=" + clientSecret
				+ ", sslCertPath=" + sslCertPath + ", keyPassword=" + keyPassword + ", storePassword=" + storePassword
				+ ", tokenServerUrl=" + tokenServerUrl + ", apiRequestUrl=" + apiRequestUrl + ", getState()="
				+ getState() + ", getTokenExpiration()=" + getTokenExpiration() + ", getClientID()=" + getClientID()
				+ ", getClientSecret()=" + getClientSecret() + ", getSslCertPath()=" + getSslCertPath()
				+ ", getKeyPassword()=" + getKeyPassword() + ", getStorePassword()=" + getStorePassword()
				+ ", getTokenServerUrl()=" + getTokenServerUrl() + ", getApiRequestUrl()=" + getApiRequestUrl()
				+ ", getGrantType()=" + getGrantType() + ", getResponseType()=" + getResponseType()
				+ ", getBaseAuthorizationUrl()=" + getBaseAuthorizationUrl() + ", getDisconnectUrl()="
				+ getDisconnectUrl() + ", getRedirectUrl()=" + getRedirectUrl() + ", getAuthorizationCode()="
				+ getAuthorizationCode() + ", getScope()=" + getScope() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
}
