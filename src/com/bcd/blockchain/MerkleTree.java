package com.bcd.blockchain;

import java.util.ArrayList;
import java.util.List;

import com.bcd.hashing.Hashing;

public class MerkleTree {
    // to store transactions
    private List<String> tranxList;
    // declare root attribute
    private String root = "0'";

    // implement singleton design pattern
    private static MerkleTree instance;

    // overloading contructor
    private MerkleTree(List<String> tranxList) {
        super();
        this.tranxList = tranxList;
    }

    public static MerkleTree getInstance(List<String> tranxList) {
        // if the instance is not declared
        if (instance == null) {
            return new MerkleTree(tranxList);
        }
        return instance;
    }

    public String getRoot() {
        return root;
    }

    private List<String> generateHashList(List<String> tranxList) {
        List<String> hashList = new ArrayList<>();
        int i = 0;

        while (i < tranxList.size()) {
            String left = tranxList.get(i);
            i++;

            // get the transaction on right
            String right = "";
            if (i != tranxList.size()) {
                right = tranxList.get(i);
            }

            String hash = Hashing.hash(left.concat(right), "SHA-256");
            hashList.add(hash);
            i++;

        }
        return hashList;
    }

    public void buildTree() {
        List<String> tempList = new ArrayList<>();

        // add all transaction to a temporary list
        this.tranxList.stream()
                .forEach(tranx -> tempList.add(tranx));

        List<String> hashes = generateHashList(tempList);
        // loop until it become a single hash
        while (hashes.size() != 1) {
            hashes = generateHashList(hashes);
        }
        this.root = hashes.get(0);

    }

}
