package Transactions;

import Catalog.Product;
import java.util.ArrayList;

/**
 * Abstract class to standardize all payment types
 *
 */
public class Payment {

    public static final int CASH = 0;
    public static final int CHECK = 1;
    public static final int CREDIT = 2;

    private double amount;
    private int creditCardNumber;
    private int type; // payment methods

    public Payment(int _type, double _amount) {
        this.type = _type;
        this.amount = _amount;
    }

    public Payment(int _type, double _amount, int _creditCardNumber) {
        this.type = _type;
        this.amount = _amount;
        this.creditCardNumber = _creditCardNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isItCash(int cash) {
        if (this.type == cash) {
            return true;
        }
        return false;
    }

    public boolean isItCredit(int credit) {
        if (this.type == credit) {
            return true;
        }
        return false;
    }

    public boolean isItCheck(int check) {
        if (this.type == check) {
            return true;
        }
        return false;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public static double calculateTotal(ArrayList<Product> products) {
        double total = 0.0;
        for (Product productToCalculate : products) {
            
            total += productToCalculate.getPrice() * productToCalculate.getQuantity();

        }

        return total;
    }

    public int getCreditCardNumber() {
        return this.creditCardNumber;
    }
}
