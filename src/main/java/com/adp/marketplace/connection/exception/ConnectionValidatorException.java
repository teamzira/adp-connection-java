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
package com.adp.marketplace.connection.exception;


/**
 * <p>
 * Thrown to indicate when a connection is in invalid state
 * or has invalid configuration
 * 
 * @author tallaprs
 *
 */
public class ConnectionValidatorException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * constructs a {@link ConnectionValidatorException} with no detail message
	 */
	public ConnectionValidatorException() {
        super();
    }

	/**
	 * constructs a {@link ConnectionValidatorException} with a detail message
	 * @param message describes the error message
	 */
    public ConnectionValidatorException(String message) {
        super(message);
    }
    
    /**
     * constructs a {@link ConnectionValidatorException} with detail message and
     * {@link Throwable} cause of the exception
     * 
     * @param message  describes the error message
     * @param cause    throwable cause
     */
    public ConnectionValidatorException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * constructs a {@link ConnectionValidatorException} with {@link Throwable} 
     * cause of the exception
     * 
     * @param cause  throwable cause
     */
    public ConnectionValidatorException(Throwable cause) {
        super(cause);
    }
}