package com.realllydan.hashy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.StringBuilder;

class Hash {
    public static String getEncryptedMessage(String algostring, String message) {
        if (algostring.equals("MD5")) {
            return encrpytMessageForAlgostring("MD5", message);
        }
        if (algostring.equals("SHA-1")) {
            return encrpytMessageForAlgostring("SHA-1", message);
        }
        if (algostring.equals("SHA-256")) {
            return encrpytMessageForAlgostring("SHA-256", message);
        }
        return null;
    }

    private static String encrpytMessageForAlgostring(String algostring, String message) {
        try {
            MessageDigest md = MessageDigest.getInstance(algostring);
            md.update(message.getBytes());
            byte[] hashBytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hashBytes.length; i++) {
                sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ae) {
            return ae.toString();
        }
    }

/*
    // Great work, G! - Dan

    public static void main(String[] args)
    {
        System.out.println(getEncryptedMessage("MD5", "Hash This Message"));
        System.out.println(getEncryptedMessage("SHA-1", "Hash This Message"));
        System.out.println(getEncryptedMessage("SHA-256", "Hash This Message"));
    }
     
    OUTPUT:
    
    4c5f18afc3bccdcfcfc7cc523bab140c
    46cdce83319de7bb849759086c2e42bb75db4870
    ac73b90d35d91242941d38c8810e72c6d8906e3498eea62b23fe56d62bbdd579

*/

}