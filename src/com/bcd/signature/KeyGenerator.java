package com.bcd.signature;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import com.bcd.blockchain.Blockchain;

public class KeyGenerator {
    private static final String algorithm = "RSA";

    private KeyPairGenerator keygen;
    private KeyPair keypair;
    public static PublicKey publicKey;
    public static PrivateKey privateKey;

    public static final String PUBLIC_KEY_FILE = Blockchain.MASTER_DIR + "/master/PublicKey.bin";

    public static final String PRIVATE_KEY_FILE = Blockchain.MASTER_DIR + "/master/PrivateKey.bin";

    public static PublicKey getPublicKey() {
        return publicKey;
    }

    public static PrivateKey getPrivateKey() {
        return privateKey;
    }

    public KeyGenerator() {
        try{
            //instantiate key pair generator
            keygen = KeyPairGenerator.getInstance(algorithm);
            keygen.initialize(1024);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void generateKey(){
        KeyGenerator myKey = new KeyGenerator();

        //generate publci and private key
        myKey.keypair = myKey.keygen.generateKeyPair();

        //get key and save in binary file
        publicKey = myKey.keypair.getPublic();
        saveKey(publicKey.getEncoded(), PUBLIC_KEY_FILE); 

        privateKey = myKey.keypair.getPrivate();
        saveKey(privateKey.getEncoded(), PRIVATE_KEY_FILE);
    }

    public static void saveKey( byte[] keyBytes, String fileName) { 
        try{
            Files.write(Paths.get(fileName), keyBytes , StandardOpenOption.CREATE);
            System.out.println("Key saved in file..");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
