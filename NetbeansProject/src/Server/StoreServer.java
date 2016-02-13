package Server;

import Transactions.Transaction;
import Transactions.Invoice;
import Post.*;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import Catalog.*; // Imports catalog package
import PaymentVerifier.*;


/**
 *
 * @author Brian Parra
 * This is supposed to be an instance of a store server. Pretend that when you instantiate it you can tell it which port to listen to and 
 * what not. There can be multiple StoreServers if you have multiple server racks at your store. Posts will connect to available StoreServers;
 * 
 * 
 */
public class StoreServer {

    private PaymentVerifier paymentVerifier;
    public String storeName;
    public Post currentPost;
    private final String CATALOG_DATABASE = "products.txt";
    private Stock catalog; // catalog instance

    
    /**
     * Initializes a StoreServer. This would be the part of the program that sits on the store's servers and listens for POSTs's requests.
     * @param name the name of the store
     */
    public StoreServer(String name) {

        //Initialize stuff here needs to read in data and what not
        this.storeName = name;

        //First check and see if this store has already been created
        catalog = new Stock(CATALOG_DATABASE); // creates an object of the Stock class
        catalog.loadCatalog(); // loads catalog of products in memory

        paymentVerifier = new PaymentVerifier();

        //For now directly make a post and run it
        currentPost = new Post(this);
        currentPost.runPost();

    }

 
    /**
     * This returns the store's catalog. Can be used to populate a gui
     * @return ArrayList of Products in the store's catalog
     */
    public Stock getCatalog(){
        return this.catalog;
    }


    /**
     * Simply outputs catalog
     */
    public void viewCatalog() {
        catalog.viewCatalog();

    }
    
    /**
     * This takes a transaction, verifies it, then makes an invoice and logs it before sending it back to the client POST
     * @param transaction transaction to verify
     * @return Invoice with all calculated values and status
     */
    public Invoice verifyTransaction(Transaction transaction){
        Invoice invoice = new Invoice(transaction , this);
        
        //Do some basic checking like random chance that check/credit card is bad or fake bills
        if(!paymentVerifier.verify(invoice)){
           
            invoice.setValid(false);
        }
        invoice.saveInSalesLog();
        return invoice;
    }
    
    /**
     * Get the Store's name
     * @return Store's name
     */
    public String getName(){
        return this.storeName;
    }

}
