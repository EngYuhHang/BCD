package com.bcd.tester;

import java.io.File;
import java.util.UUID;

import com.bcd.blockchain.Block;
import com.bcd.blockchain.Blockchain;
import com.bcd.blockchain.Transaction;
import com.bcd.signature.AsymmetricEncrypt;
import com.bcd.signature.DigitalSignature;
import com.bcd.signature.ReadKey;

public class App {
    public static void main(String[] args) throws Exception {
        // Medicine Supply Chain Simulation
        // Production
        // create transaction between production and shipment
        // medicineID|medicineName|Production|Shipment|quantity|Cost
        String proTranx = String.format("%s|%s|%s|%s|%s|%s",
                UUID.randomUUID().toString(),
                "Captopril",
                "TanosLab",
                "DHL Delivery",
                80,
                160);

        String newProTranx = SignAndEncryp(proTranx);
        Transaction proTranxList = new Transaction();
        if (newProTranx != null)
        {
                proTranxList.add(newProTranx);
        }
        

        // Shipment
        // create new shipment to Supplier transaction
        // ShipmentID|ServiceProvider|Supplier|Quantity|DeliveryDate|Status|DeliveryMethod
        String shipTranx = String.format("%s|%s|%s|%s|%s|%s|%s",
                "DHL13456678",
                "DHL Delivery",
                "MedSupply",
                80,
                "15/10/2022",
                "Delivered",
                "Air Freight");

        Transaction shipTranxList = new Transaction();
        String newShipTranx = SignAndEncryp(shipTranx);
        if (newShipTranx != null)
        {
                shipTranxList.add(newShipTranx);
        }
        

        // Supplier
        // create new supplier to distributor transaction
        // medicineID|Supplier|Distributor|Quantity|Cost|payment_date|Payment_method
        String supTranx = String.format("%s|%s|%s|%s|%s|%s|%s",
                UUID.randomUUID().toString(),
                "MedSupply",
                "Guardian",
                40,
                450.00,
                "18/10/2022",
                "Online Banking");

        Transaction supTranxList = new Transaction();
        String newSupTranx = SignAndEncryp(supTranx);
        if (newSupTranx != null)
        {
                supTranxList.add(newSupTranx);
        }

        // Distributor
        // create new distributor to customer transaction
        // medicineID|Distributor|Customer|Customer_IC|Reason_To_Buy|Quantity|Cost|payment_date|Payment_method
        String disTranx = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s",
                UUID.randomUUID().toString(),
                "Guardian",
                "Kenny Tan",
                "010102011397",
                "Headache",
                3,
                60.00,
                "18/10/2022",
                "Cash");

        Transaction disTranxList = new Transaction();
        String newDisTranx = SignAndEncryp(disTranx);
        if (newDisTranx != null)
        {
                disTranxList.add(newDisTranx);
        }

        // create blockchain and write to file
        new File(Blockchain.MASTER_DIR + "/master").mkdir();
        // create genesis block
        Blockchain.genesis();

        // production block
        Block b1 = new Block(Blockchain.get().getLast().getHeader()
                .getHash());

        b1.setTransaction(proTranxList);
        // add to block chain
        Blockchain.nextBlock(b1);

        // shipment block
        Block b2 = new Block(Blockchain.get().getLast().getHeader()
                .getHash());
        b2.setTransaction(shipTranxList);
        Blockchain.nextBlock(b2);

        // supplier block
        Block b3 = new Block(Blockchain.get().getLast().getHeader()
                .getHash());
        b3.setTransaction(supTranxList);
        Blockchain.nextBlock(b3);

        // supplier block
        Block b4 = new Block(Blockchain.get().getLast().getHeader()
                .getHash());
        b4.setTransaction(disTranxList);
        Blockchain.nextBlock(b4);

        // write to file
        Blockchain.distribute();

    }

    public static String SignAndEncryp(String tranx) throws Exception
    {
        DigitalSignature signature = new DigitalSignature();
        AsymmetricEncrypt asym = new AsymmetricEncrypt();
        String Encrypt_Signed_Tranx = "";

        //sign transaction
        String signed = signature.sign(tranx, ReadKey.getPrivateKey());

        Boolean verified = signature.verify(tranx, signed, ReadKey.getPublicKey());

        if (verified)
        {
                String encryptedTranx = asym.encrypt(tranx, ReadKey.getPublicKey());
                //encrypt transaction if signature was verified
                Encrypt_Signed_Tranx = String.join("|", encryptedTranx,signed);
                return Encrypt_Signed_Tranx;
        }else
        {
                return null;
        }
    }
}
