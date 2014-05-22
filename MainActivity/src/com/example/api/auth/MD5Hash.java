/*Created By: Christian Joseph Dalisay
 * Created On: 05/08/14
 * MD5Hash - This class has a hash method for a string using message digest 5
 */
	
package com.example.api.auth;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Hash {
	
    public static String md5(String s) throws Exception{
        MessageDigest m=MessageDigest.getInstance("MD5");
        m.update(s.getBytes(),0,s.length());
        return new BigInteger(1,m.digest()).toString(16);
    }
}
    
    
