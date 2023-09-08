package com.bcd.tester;

import java.util.LinkedList;
import java.util.List;

import com.bcd.blockchain.Block;
import com.bcd.blockchain.Blockchain;
import com.bcd.signature.AsymmetricEncrypt;
import com.bcd.signature.ReadKey;

public class Decrypt {
    public static void main(String[] args) throws Exception{
        LinkedList<Block> list = new LinkedList<Block>();
        list = Blockchain.get();

        AsymmetricEncrypt asymm = new AsymmetricEncrypt();

        List<String> TranxList = null;

        System.out.println("Transaction Decryption is beginning..");

        System.out.println("Below is decrypted Transaction Record:\n");

        //for loop start at index 1 to exclude genesis block
        for (int i = 1 ; i < list.size(); i ++)
        {
            TranxList = list.get(i).getTransaction().getTranxLst();
            //split the transactin list with digital signature
            String[] tranx = TranxList.get(0).toString().split("=");

            //decrypt cipher text
            String oriText = asymm.decrypt(tranx[0], ReadKey.getPrivateKey());
            System.out.println(oriText);
        }
    }
}
