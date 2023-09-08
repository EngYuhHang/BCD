package com.bcd.blockchain;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;

import com.bcd.hashing.Hashing;
import com.google.gson.annotations.Expose;

public class Block implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Expose
    public Header header;

    public Header getHeader() {
        return header;
    }

    @Expose
    public Transaction transaction;

    public Block(String previousHash) {
        header = new Header();
        header.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        header.setPrevHash(previousHash);
        
        String info = String.join("+", Integer.toString(header.getId()), Long.toString(header.getTimestamp()),
                header.getPrevHash());
        String blockHash = Hashing.hash(info, "SHA-256");
        header.setHash(blockHash);
    }

    /**
     * getBytes of the Block object
     */
    private byte[] getBytes() {
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(baos);) {
            out.writeObject(this);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * aggregation relationship
     */
    public void setTransaction(Transaction tranx) {
        this.transaction = tranx;
    }

    @Override
    public String toString() {
        return "Block [feader=" + header + ", transaction=" + transaction.merkleRoot + "]";
    }

    /**
     * composition relationship
     */
    public class Header implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        @Expose
        public int id;
        @Expose
        public String hash, prevHash;
        @Expose
        public long timestamp;

        // getset methods
        public String getHash() {
            return hash;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getPrevHash() {
            return prevHash;
        }

        public void setPrevHash(String prevHash) {
            this.prevHash = prevHash;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "Header [id=" + id + ", hash=" + hash + ", prevHash=" + prevHash + ", timestamp="
                    + timestamp + "]";
        }

    }

}