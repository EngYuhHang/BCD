package com.bcd.signature;

import java.security.PublicKey;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class ReadKey {

    public static PublicKey getPublicKey() throws Exception{
        byte[] keyBytes = Files.readAllBytes(Paths.get(KeyGenerator.PUBLIC_KEY_FILE));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    public static PrivateKey getPrivateKey() throws Exception{
        byte[] keyBytes = Files.readAllBytes(Paths.get(KeyGenerator.PRIVATE_KEY_FILE));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }
}
