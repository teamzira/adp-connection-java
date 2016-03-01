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

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import com.adp.marketplace.connection.configuration.ConnectionConfiguration;
import com.adp.marketplace.connection.constants.Constants;
import com.adp.marketplace.connection.exception.ConnectionException;
import com.adp.marketplace.connection.exception.ConnectionValidatorException;


/**
 * <p>
 * SSLUtils is a singleton utility class that provides convenience methods to obtain 
 * <b>secured</b> http clients
 * 
 * <p>
 * @see CloseableHttpClient
 * 
 * @author tallaprs
 *
 */
public class SSLUtils {
	
	private static SSLUtils INSTANCE = null;
	
	private static final Logger LOGGER = Logger.getLogger(SSLUtils.class.getName());
	
	/**
	 * Returns a lazy loaded singleton instance of {@link SSLUtils} 
	 * 
	 * @return SSLUtils a singleton instance
	 */
	public static SSLUtils getInstance() {
		
		if ( INSTANCE == null ) {			
			synchronized (SSLUtils.class) {			
	            if ( INSTANCE == null ) {
	                INSTANCE = new SSLUtils();
	            }
	        }
	    }
		
	    return INSTANCE;
	}

	/**
	 * Returns an instance of {@link CloseableHttpClient}
	 * 
	 * @param connectionConfiguration has file path to SSL certificate, 
	 * 								  password to trust store or key
	 * @return CloseableHttpClient a closable Http client object returned
	 * @throws ConnectionException Exception in case of missing certificate 
	 *                             file, invalid file path or invalid trust 
	 *                             store or key key store
	 * 
	 * @see CloseableHttpClient
	 * @see ConnectionConfiguration
	 */
	public CloseableHttpClient getHttpsClient(ConnectionConfiguration connectionConfiguration)
			throws ConnectionException {
		
		char[] keyPassword;
		char[] storePassword;
		
		String filePath = "";
		CloseableHttpClient closeableHttpClient = null;
		
		try {
			
			boolean isValid = ConnectionValidatorUtils.getInstance().validateSSLFields(connectionConfiguration);
			
			if ( isValid ) {
			
				filePath = connectionConfiguration.getSslCertPath().trim();
				keyPassword = connectionConfiguration.getKeyPassword().trim().toCharArray();
				storePassword = connectionConfiguration.getStorePassword().trim().toCharArray();
				
				closeableHttpClient = getHttpsClient(filePath, storePassword, keyPassword);
				
			}
			
		} catch (ConnectionValidatorException e) {
			throw new ConnectionException(e);
		} catch (Exception e) {
			throw new ConnectionException(e);
		}
		
		return closeableHttpClient;
	}

	/**
	 * Returns an instance of {@link CloseableHttpClient}
	 * 
	 * @return CloseableHttpClient
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws CertificateException
	 * @throws IOException
	 */
	private final CloseableHttpClient getHttpsClient(String filePath, char[] storePassword, char[] keyPassword) 
			throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, 
			UnrecoverableKeyException, CertificateException, IOException, Exception {
		

		CloseableHttpClient httpclient = null;
		
		//System.out.println("SSLUTILS.filePath: "+ filePath );
		//System.out.println("SSLUTILS.storePassword[] " + storePassword.toString());
		//System.out.println("SSLUTILS.keyPassword" + keyPassword.toString() );
		
		try {
					
			SSLContext sslcontext = SSLContexts.custom()
			        .loadKeyMaterial(new File(filePath), storePassword, keyPassword)
			        .build();
			
			// Allow TLSv1.* protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, 
					new String[] { Constants.TLS_VERSION }, null, 
					SSLConnectionSocketFactory.getDefaultHostnameVerifier());
			
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			
		} catch (Exception e) {
			throw new ConnectionException(e);
		}
		
		return httpclient;
	}
	
	
}