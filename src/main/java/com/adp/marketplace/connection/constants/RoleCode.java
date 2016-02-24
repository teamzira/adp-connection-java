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
 * RoleCode is a enum with three Role codes currently available
 * 
 * @author tallaprs
 *
 */
public enum RoleCode {

	Employee("employee"), MANAGER("manager"), PRACTITIONER("practitioner");

	private String value;
	
	/**
	 * constructor
	 * 
	 * @param value maps to this value 
	 */
	private RoleCode(String value) { 
		this.value = value; 
	}

	/**
	 * Returns the value of this role code
	 * 
	 * @return role code value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the value of role code based on value parameter
	 *  
	 * @param value role code value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

};

