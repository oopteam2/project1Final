package Transactions;

import Catalog.Product;
import Post.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Brian Parra
 * This class is for building Transaction objects. The temp version just reads them off a file. Next version should pull data off the GUI
 */
public class TransactionManager {

    private ArrayList<Transaction> transactions;
    private static BufferedReader bufferedReader;

    private final Post currentPost;

    /**
     * Initializer
     * @param _post reference to Post that initialized it
     */
    public TransactionManager(Post _post) {
        this.currentPost = _post;
    }

    /**
     * This pulls the transactions off of transactions.txt
     * @return ArrayList of Transactions
     */
    public ArrayList<Transaction> getTransactions() {
        transactions = new ArrayList<Transaction>();
        ArrayList<Product> products;
        Product currentProduct;
        Customer customer;
        Payment payment;
        String[] upcLine;
        String[] paymentInfo;
        
        try {
            bufferedReader = new BufferedReader(new FileReader("transactions.txt"));
            String line;

            while ((line = bufferedReader.readLine()) != null) {

                
                products = new ArrayList<Product>();
                
                customer = new Customer(line);
                
          
                
                line = bufferedReader.readLine();
                while (!line.contains("CREDIT") && !line.contains("CHECK") && !line.contains("CASH")) {
                    
                    upcLine = line.split("[ ]+");
                    currentProduct = this.currentPost.generateProductByUPC(upcLine[0]);
                    if (upcLine.length == 1) {
                        currentProduct.setQuantity(1);
                    } else {
                        currentProduct.setQuantity(Integer.parseInt(upcLine[1]));
                    }

                    products.add(currentProduct);
                    line = bufferedReader.readLine();
                }
                //System.out.println(line);
                paymentInfo = line.split("[ ]+");
                //System.out.println(paymentInfo[1]);
                switch (paymentInfo[0]) {
                    case "CREDIT":
                        payment = new Payment(Payment.CREDIT, Payment.calculateTotal(products), Integer.parseInt(paymentInfo[1]));
                        break;
                    case "CHECK":
                        payment = new Payment(Payment.CHECK, Double.parseDouble(paymentInfo[1]));
                        break;
                    default:
                        payment = new Payment(Payment.CASH, Double.parseDouble(paymentInfo[1]));
                        break;
                }

                transactions.add(new Transaction(customer, payment, products));
                line = bufferedReader.readLine();
               

      

            }
        } catch (Exception exception) {
            System.out.println(exception);
            //break;
        }

        return transactions;
    }
}
