/* @Author: Christian Joseph Dalisay
 * @Created: 05/08/14
 * @Handles the HMAC-SHA1 Encryption Algorithm 
 */

package com.example.api.auth;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class HMAC_SHA1 {
	
	public static String hmacSha1(String value, String key) {
    	System.out.println("data " + value);
    	System.out.println("key " + key);
        try {
            // Get an hmac_sha1 key from the raw key bytes
            byte[] keyBytes = key.getBytes();           
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(value.getBytes());

            // Convert raw bytes to Hex
            byte[] hexBytes = new Hex().encode(rawHmac);
            System.out.println("HMAC_SHA1.hmacSha1 " + new String(hexBytes, "UTF-8"));
            
            //  Covert array of Hex bytes to a String
            return new String(hexBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	   
}