package Transactions;

import Catalog.Product;
import Catalog.Stock;
import java.time.LocalDateTime;
import java.util.ArrayList;




/**
 *This is the transaction class it handles the details for performing a transaction at a store
 * 
 * @author Brian Parra
 */
public class Transaction {
    public Customer customer;
    public Payment payment;
    public LocalDateTime timeStamp;
    public double total =0.0;
    public double amountPaid = 0.0;
    public ArrayList<Product> products;
    public boolean valid = true;
    
    /**
     * Transaction Class holds details about a single Transaction
     * @param _customer Customer who is purchasing
     * @param _payment Payment type 
     * @param _products Products purchased
     */
    public Transaction (Customer _customer, Payment _payment,  ArrayList<Product> _products )
    {
       
        this.customer = _customer;
        this.payment = _payment;
        this.products = _products;
        this.timeStamp = LocalDateTime.now();
        this.total = Payment.calculateTotal(this.products);
        this.amountPaid = this.payment.getAmount();
        
    }
    
    /**
     * Total amount due
     * @return Total amount Products are worth
     */
    public double getTotal(){
        return this.total;
    }
    /**
     * Get the time the transaction was made
     * @return timestamp
     */
    public LocalDateTime getTimeStamp(){
        return this.timeStamp;
    }
    
    /**
     * Sets the payment object
     * @param payment Change the payment type
     */
    public void setPayment(Payment payment)
    {
        this.payment = payment;
    }
    
    /**
     * Check if paid by cash
     * @return true if is cash transaction
     */
    public boolean isCashTransaction(){
        return this.payment.getType() == Payment.CASH;
    }
    
    /**
     * Check if paid by credit
     * @return true if paid by credit
     */
    public boolean isCreditTransaction(){
        return this.payment.getType() == Payment.CREDIT;
    }
    
    /**
     * Check if paid by check
     * @return true if paid by check
     */
    public boolean isCheckTransaction(){
        return this.payment.getType() == Payment.CHECK;
    }
    
    /**
     * Is this a valid transaction
     * @return true if this transaction is valid
     */
    public boolean isValid(){
        return this.valid;
    }
    
    /**
     * Validate or invalidate transaction
     * @param _valid status of transaction
     */
    public void setValid(boolean _valid){
        this.valid = _valid;
    }
    
    /**
     * Set Customer for the transaction
     * @param customer Customer who purchased
     */
    public void setCostumer (Customer customer)
    {
        this.customer = customer;
    }
    
    /**
     * Get payment object
     * @return Payment used
     */
    public Payment getPayment()
    {
        
        return this.payment;
    }
    
    /**
     * Gets Customer full info
     * @return Customer in purchase
     */
    public Customer getCustomer ()
    {
        return this.customer;
    }
    
    /**
     * Gets customer name
     * @return Customer's name
     */
    public String getCustomerName(){
        return this.customer.getName();
    }
    
    /**
     * Gets the amount that the customer paid (can be different from total due)
     * @return total paid
     */
    public double getAmountPaid(){
        return this.amountPaid;
    }
    
    /**
     * Gets formated version of payment type
     * @return String of payment type
     */
    public String getPaymentTypeString(){
        String paymentType = "";
        if(this.isCashTransaction()){
           paymentType = "CASH"; 
        }else if(this.isCheckTransaction()){
            paymentType = "CHECK";
        }else if(this.isCreditTransaction()){
            paymentType = "CREDIT";
        }
        return paymentType;
    }
   
    /**
     * Gets array list of the products associated with this purchase
     * @return ArrayList of products
     */
    public ArrayList <Product> getProducts ()
    {

        return this.products;
    }
    
    
}
