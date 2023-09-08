package com.bcd.signature;

import java.security.PublicKey;
import java.security.PrivateKey;
import java.util.Base64;

import javax.crypto.Cipher;

public class AsymmetricEncrypt {
    
    private Cipher cipher;

    public AsymmetricEncrypt(){
        this("RSA");
    }

    public AsymmetricEncrypt(String algorithm) {
        try{
            cipher = Cipher.getInstance(algorithm);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //encrypt with public key
    public String encrypt( String data, PublicKey key) throws Exception
    {
        var cipherText = "";

        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] cipherBytes = cipher.doFinal(data.getBytes());

        cipherText = Base64.getEncoder().encodeToString(cipherBytes);

        return cipherText;
    }

    //decrypt with private key
    public String decrypt( String data, PrivateKey key) throws Exception
    {
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] cipherText = Base64.getDecoder().decode(data);

        byte[] dataBytes = cipher.doFinal(cipherText);

        return new String(dataBytes);
    }

}
