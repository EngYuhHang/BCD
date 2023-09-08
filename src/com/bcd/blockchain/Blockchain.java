package com.bcd.blockchain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;

import com.google.gson.GsonBuilder;

public class Blockchain {

    // declare directory path
    public static final String MASTER_DIR = "src/com/bcd";

    // master-binary-file
    public static final String MASTER_BINARY = MASTER_DIR + "/master/Blockchain.bin";

    // ledger-file
    public static final String LEDGER_FILE = MASTER_DIR + "/Ledger.txt";

    // db
    private static LinkedList<Block> database = new LinkedList<>();

    // to get the blockchain from file
    public static LinkedList<Block> get() {
        try (
                FileInputStream fin = new FileInputStream(MASTER_BINARY);
                ObjectInputStream oin = new ObjectInputStream(fin);) {

            return (LinkedList<Block>) oin.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // create the genesis block
    public static void genesis() {
        Block genesis = new Block("0");
        database.add(genesis);
        // write and print the block
        Blockchain.persist();
        Blockchain.distribute();
    }

    public static void nextBlock(Block block) {

        // generate merkle root
        MerkleTree mt = MerkleTree.getInstance(block.getTransaction().getTranxLst());
        mt.buildTree();
        String root = mt.getRoot();

        // set root as the merkleroot for the block
        block.getTransaction().setMerkleRoot(root);

        // retrieve the blockchain from file
        database = Blockchain.get();
        block.getHeader().setId(database.getLast().getHeader().getId() + 1);
        // add the new block into the linked list
        database.add(block);
        // write the file
        Blockchain.persist();

    }

    public static void persist() {
        try (
                FileOutputStream fout = new FileOutputStream(MASTER_BINARY);
                ObjectOutputStream op = new ObjectOutputStream(fout);) {

            op.writeObject(database);
            System.out.println("updated");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // write ledger file
    public static void distribute() {
        // convert chain to json
        String chain = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create()
                .toJson(database);
        System.out.println(chain);
        try {
            Files.write(Paths.get(LEDGER_FILE), chain.getBytes(), StandardOpenOption.CREATE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
