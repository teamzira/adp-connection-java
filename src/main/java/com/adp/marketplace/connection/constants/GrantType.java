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
package com.adp.marketplace.connection.constants;


/**
 * <p>
 * GrantType is a enum with three Grant Types 
 * 
 * @author tallaprs
 * 
 */
public enum GrantType {

	CLIENT_CREDENTIALS("client_credentials"), AUTHORIZATION_CODE("authorization_code"), SAML_AUTH_CODE("saml");

	private String value;
	
	/**
	 * constructor
	 * @param value maps to this value 
	 */
	private GrantType(String value) { 
		this.value = value; 
	}

	/**
	 * Returns the value of this grant type
	 * 
	 * @return grant type value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of grant type based on value parameter
	 *  
	 * @param value the string value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

};