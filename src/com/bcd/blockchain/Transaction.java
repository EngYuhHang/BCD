package com.bcd.blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Transaction implements Serializable {
    // data collection using arraylist
    // hide transaction data from ledger file
    @Expose
    public List<String> tranxList;
    // expose annotation to print it in the ledger file
    @Expose
    public String merkleRoot;

    // getter and setter for merkleRoot
    public String getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public List<String> getTranxLst() {
        return tranxList;
    }

    public Transaction() {
        tranxList = new ArrayList<String>();
    }

    // add transaction into the list
    public void add(String tranx) {
        tranxList.add(tranx);
    }

}