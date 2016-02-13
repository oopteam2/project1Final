package Transactions;

import Catalog.Product;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import Server.StoreServer;

/**
 *
 * @author Jose Ortiz Costa and Brian Parra
 * This class contains all the methods and properties of a invoice
 * This class takes an object of the Transaction class as argument and prints a
 * formatted invoice on the screen
 * @see Transaction class
 * @see Payment class
 */
public class Invoice {
    private StoreServer store;
    private Transaction transaction;
    private double subtotal;
    private double total;
    private double tendered = 0.0;
    private double returned = 0.0;
    static double TAXES = 4.50;
    public boolean valid = true;
    private ArrayList<Product> products;


    /**
     * Constructor
     *
     * @param _store Store which this transaction belongs to
     * @param _transaction Transaction Object
     */
    public Invoice (Transaction _transaction, StoreServer _store ){
 
        this.transaction = _transaction;
        this.store = _store;
        this.products = transaction.getProducts();
        
        
        setAmountReturned(transaction.getAmountPaid()-transaction.getTotal());
        

    }

    // Setters
  
    /**
     * Sets amount paid 
     * @param tendered cash paid
     */
    public void setAmountTendered(double tendered) {
        this.tendered = tendered;
    }

    /**
     * Sets amount due back
     * @param _returned amount due back
     */
    private void setAmountReturned(double _returned) {
        
        this.returned = _returned;
        
    }

    /**
     * Sets Total Paid
     * @param total total amout of purchase
     */
    private void setTotal(double total) {
        this.total = total;
    }

    // Getters
    
    /**
     * Getter for total paid
     * @return total paid
     */
    public double getTotalPaid(){
        return this.transaction.getAmountPaid();
    }
    
    /**
     * Get total tendered
     * @return gets total tendered
     */
    public double getAmountTendered() {
        return this.tendered;
    }

    /**
     * Gets amount returned
     * @return amount returend
     */
    public double getAmountReturned() {

        return this.returned;
    }

    /**
     * Getter for date
     * @return timestamp of when purchased
     */
    public Date getInvoiceDate() {
        return new Date();
    }

    /**
     * Getter for total amount
     * @return total amount of purchase
     */
    public double getTotal() {
        // compute total amount including taxes
        return this.transaction.getTotal();
    }

    /**
     * Outputs invoice to command line
     */
    public void printInvoice() {
        System.out.println(this.toString());

    }
    
    /**
     * Checks if this invoice is valid
     * @return is invoice valid?
     */
    public boolean isValid(){
        return this.valid;
    }
    
    /**
     * Sets status of invoice
     * @param _valid invoice status
     */
    public void setValid(boolean _valid){
        this.valid = _valid;
    }

    /**
     * Saves a single invoice in the salesLog
     *
     * 
     */
    public void saveInSalesLog() {
        try {
            FileWriter writer = new FileWriter("salesLog.txt", true);
            writer.write(this.toString());
            writer.close();
        } catch (IOException err) {
            System.out.println("Error: This transaction couldn't be saved in the log \n"
                    + "Detailed Error: " + err.getMessage());

        }
    }

    /**
     * Read all the invoices from the salesLog
     *
     * @param salesLogFile
     */
    /*
    public void readAllInvoices(String salesLogFile) {
        try {
            FileInputStream fis = new FileInputStream(salesLogFile);
            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (IOException err) {
            System.out.println("Error: " + err.getMessage());
        }
    }
    */

    /**
     *
     * @return a string representing the invoice formatted
     */
    @Override
    public String toString() {
        int i;

        String invoicePart1, invoicePart2 = "", invoicePart3, formattedPart1,
                formattedPart2, formattedPart3, separator, tenderedStr;
        formattedPart1 = "%-20s %-15s%n";
        formattedPart2 = "%-20s %-30s $%8.2f%n";

        invoicePart1 = String.format(formattedPart1, this.store.getName() , getInvoiceDate())
                + String.format(formattedPart1, transaction.getCustomerName(), "");

        for (i = 0; i < products.size(); i++) {
            invoicePart2 += String.format(formattedPart2, products.get(i).getDescription(),
                    products.get(i).getQuantity() + " @ "
                    + products.get(i).getPrice(),
                    products.get(i).getQuantity()
                    * products.get(i).getPrice());
        }

        separator = "-----------------------------------------------------------\n";
        if (transaction.isCreditTransaction()
                || transaction.isCheckTransaction()) {
            tenderedStr = "Paid by " + transaction.getPaymentTypeString();
            //setAmountTendered(getTotal());

        } else {
            tenderedStr = "Amount Tendered ";
        }

        invoicePart3 = String.format(formattedPart2, "Total", "", getTotal())
                + String.format(formattedPart2, tenderedStr, "", this.transaction.getAmountPaid())
                + String.format(formattedPart2, "Amount returned", "", getAmountReturned());

        
        if(this.valid){
            invoicePart3 = String.format(formattedPart2, "Total", "", getTotal())
                + String.format(formattedPart2, tenderedStr, "", this.transaction.getAmountPaid())
                + String.format(formattedPart2, "Amount returned", "", getAmountReturned());
        }else{
            invoicePart3 = String.format(formattedPart2, "Total", "", getTotal())
                + String.format(formattedPart2, tenderedStr, "", this.transaction.getAmountPaid())
                + "*****INVALID TRANSACTION*****\n";
        }
        
        return invoicePart1 + invoicePart2 + separator + invoicePart3 + "\n\n";

    }

}
