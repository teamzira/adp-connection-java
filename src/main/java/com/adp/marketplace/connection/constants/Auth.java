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
 * Auth is a enum with single value
 * 
 * @author tallaprs
 *
 */
public enum Auth {
	
	DONE("done");
	
	private String value;
	
	/**
	 * constructor
	 * 
	 * @param value maps to this value
	 */
	private Auth(String value) { 
		this.value = value; 
	}

	/**
	 * Returns the value of this 
	 * 
	 * @return  value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of Auth based on value parameter
	 *  
	 * @param value  string value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

};
