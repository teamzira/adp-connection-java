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

import com.adp.marketplace.connection.core.ADPAPIConnection;

/**
 * Constants is an interface with Constants that are relevant in the context 
 * of {@link ADPAPIConnection}
 * 
 * @author tallaprs
 *
 */
public interface Constants {
	
	public static final int HTTP_SUCCESS = 200;
	public static final int HTTP_NO_CONTENT = 204;
	public static final int HTTP_REDIRECT = 302;
	public static final int HTTP_CLIENT_ERROR = 400;
	public static final int HTTP_CLIENT_INVALID = 401;
	public static final int HTTP_SERVER_ERROR = 500;

	public static final String AMPERSAND = "&";
	public static final String QUESTION = "?";
	public static final String EQUALS = "=";
	
	public static final String AOID = "aoid";
	public static final String OOID = "ooid";
	
	// grant type - 'client credentials'
	public static final String CLIENT_ID = "client_id";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String GRANT_TYPE = "grant_type";
	
	public static final String RESPONSE_TYPE = "response_type";
	public static final String REDIRECT_URI = "redirect_uri";
	
	// grant type - 'authorization code' specific constants
	public static final String CODE = "code";
	public static final String SCOPE = "scope";
	public static final String STATE = "state";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String TOKEN = "token";

	// SSL/TLS Protocol version
	public static final String TLS_VERSION =  "TLSv1.2";
	
}
