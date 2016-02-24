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
package com.adp.marketplace.connection.vo;

import java.io.Serializable;

import com.adp.marketplace.connection.constants.Scope;


/**
 * <p>
 * Token class is a simple value object that contains response from Authorization
 * Server
 * 
 * @author tallaprs
 *
 */
public class Token implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long expires_in = 0;
	
	private String access_token;
	private String id_token;
	private String refresh_token;
	private String scope;
	private String token_type;
	
	/**
	 * constructor
	 */
	public Token() {
		super();
	}
	
	/**
	 * Returns the access token issued by token server
	 * 
	 * @return access_token value issued by the server
	 */
	public String getAccess_token() {
		return access_token;
	}
	
	/**
	 * Sets the access token
	 * 
	 * @param access_token maps the value of access token
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	/**
	 * Returns token expiration time in seconds
	 * 
	 * @return expires_in token expiration time in seconds
	 */
	public long getExpires_in() {
		return expires_in;
	}
	
	/**
	 * Sets the expiration time of token since issued
	 * 
	 * @param expires_in maps the value of expiration time in seconds
	 */
	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}
	
	/**
	 * @return the id_token
	 */
	public String getId_token() {
		return id_token;
	}

	/**
	 * @param id_token the id_token to set
	 */
	public void setId_token(String id_token) {
		this.id_token = id_token;
	}

	/**
	 * @return the refresh_token
	 */
	public String getRefresh_token() {
		return refresh_token;
	}

	/**
	 * @param refresh_token the refresh_token to set
	 */
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	/**
	 * Returns the scope of the access token
	 * 
	 * 
	 * @return scope value returned based on type of connection 
	 * @see Scope
	 */
	public String getScope() {
		return scope;
	}
	
	/**
	 * Sets the scope on this Token 
	 * 
	 * @param scope maps the value of scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	/**
	 * Returns token type value of this Token
	 * 
	 * @return token_type value 'Bearer 'is returned
	 */
	public String getToken_type() {
		return token_type;
	}
	
	/**
	 * Sets the value of token_type to <b>Bearer</b>
	 * @param token_type  maps the value of token_type
	 */
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	/**
	 * 
	 * @return String the String representation of this {@link Token} object
	 */
	@Override
	public String toString() {
		return "Token [expires_in=" + expires_in + ", access_token=" + access_token + ", id_token=" + id_token
				+ ", refresh_token=" + refresh_token + ", scope=" + scope + ", token_type=" + token_type + "]";
	}

}
