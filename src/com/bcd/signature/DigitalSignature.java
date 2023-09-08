package com.bcd.signature;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class DigitalSignature {
    
    private Signature signature;

    public DigitalSignature(){
        super();
        try{
            signature = Signature.getInstance("SHA256WithRSA");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //sign method
    public String sign(String data, PrivateKey key) throws Exception
    {
        signature.initSign(key);
        signature.update(data.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign());
    }


    //signature verification method
    public Boolean verify(String data, String signatures, PublicKey key) throws Exception
    {
        signature.initVerify(key);
        signature.update(data.getBytes());
        return signature.verify(Base64.getDecoder().decode(signatures));
    }
}
