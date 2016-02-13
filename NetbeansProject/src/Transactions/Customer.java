package Transactions;

import java.util.ArrayList;
import java.util.Iterator;
import Catalog.Product;

/**
 *
 * @author Tai and Brian
 */
public class Customer {

    private ArrayList<Product> itemList = new ArrayList<>(); // items recorded from transaction.txt file
    private Payment payment; // customer can make payment
    private String name; // customer has name

    /**
     * Constructor
     *
     * @param name name of customer
     */
    public Customer(String name) {
        this.name = name;
    }

    /**
     * Getter for name
     * @return name of customer
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     *
     * @param name name to change
     */
    public void setName(String name) {
        this.name = name;
    }

}
