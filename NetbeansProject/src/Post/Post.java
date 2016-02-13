package Post;

import Transactions.TransactionManager;
import Transactions.Transaction;
import Transactions.Invoice;
import Catalog.Product;
import Catalog.Stock;
import java.util.ArrayList;
import Server.StoreServer;



/**
 *
 * @author Brian Parra
 * Description of Post This class represents the view controller for the POST. The idea is that there can be many POST's that communicate with
 * StoreServer objects.
 */
public final class Post{
    private StoreServer parentStore;
    private TransactionManager transactionManager;
    private Stock catalog; // its own copy of the catalog for display purposes
    private Invoice invoice;
    

    /**
     * Initializes a post object. In reality this should be its own program and it tries to connect to StoreServer
     * @param _parentStore Reference to the storeServer it is connected to
     */
    public Post(StoreServer _parentStore) {
        //Reference to the store it belongs
        this.parentStore = _parentStore;
        this.transactionManager = new TransactionManager(this);
        this.catalog = this.parentStore.getCatalog();
        System.out.println("Initializing Post");
 
    }
    
    /**
     * This is the run method for the "view controller" This would be the equivalent of the event listener. Since there 
     * is no event listener, it will just load a TransactionManager and read off a list of transactions.
     */
    public void runPost(){
        System.out.println("***Post Running***\n\n\n");
        

        //For now just get an array of transactions and process them (Replace with gui later)
        ArrayList<Transaction> transactions = this.transactionManager.getTransactions();
       
        //Now we loop through each transaction and communicate with the "Store Server" To check if the transactions go through
        for (Transaction transaction : transactions) {
            
            //Check for transaction
            invoice = this.parentStore.verifyTransaction(transaction);
            invoice.printInvoice();
        }

    }
    
    /**
     * This will return a Product fron the catalog by upc number
     * @param _upc the upc to look product up by
     * @return the product by upc
     */
    public Product generateProductByUPC(String _upc){
        return new Product(this.catalog.getProduct(_upc));
    }
    
 
    
}
