package Catalog;

import java.text.DecimalFormat;

/**
 *
 * @author Jose Ortiz Costa
 * This class represents a product from a catalog with its
 * properties ( UPC, description and price )
 *
 */
public class Product implements Comparable<Product> {

    // private instance variables
    private String upc;
    private String desc;
    private double price;
    private int quantity;
    private DecimalFormat formatter;
    private StringBuilder productBuilder;
    // static variables
    static final int UPC_STARS = 0;
    static final int UPC_ENDS = 4;
    static final int DESC_STARS = 9;
    static final int DESC_ENDS = 28;
    static final int PRICE_STARS = 34;
    static final int PRICE_ENDS = 41;

    /**
     * Constructor
     */
    public Product() {

    }

    /**
     * Constructor
     *
     * @param upc Upc number
     * @param description Text description of Product
     * @param price Price of product
     */
    public Product(String upc, String description, double price) {
        this.upc = upc;
        this.desc = description;
        this.price = price;
        formatter = new DecimalFormat("#0000.00");
    }

    /**
     * Constructor
     *
     * @param upc Upc of item
     * @param quantity quantity purchased
     */
    public Product(String upc, int quantity) {
        this.upc = upc;
        this.quantity = quantity;
        formatter = new DecimalFormat("#0000.00");
    }

    public Product(Product anotherProduct) {

        this.upc = anotherProduct.upc;
        this.desc = anotherProduct.desc;
        this.price = anotherProduct.price;
    }

    //setters
    /**
     * Setter for upc
     *
     * @param upc upc to set
     */
    public void setUPC(String upc) {
        this.upc = upc;
    }

    /**
     * Sets description
     *
     * @param description string to describe
     */
    public void setDescription(String description) {
        this.desc = description;
    }

    /**
     * Set Price of Product
     *
     * @param price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Used for purchasing quantity
     *
     * @param quantity quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter for upc
     *
     * @return upc for product
     */
    public String getUPC() {
        return this.upc;
    }

    /**
     *
     * @return product description
     */
    public String getDescription() {
        return this.desc;
    }

    /**
     *
     * @return price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     *
     * @return quantity
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Initialize a StringBuilder of size 45 to allocate products attributes
     */
    private void initProductBuilder() {
        this.productBuilder = new StringBuilder();
        int i = 0;
        for (i = 0; i < 45; i++) {
            productBuilder.append(" ");
        }
    }

    /**
     * Gets the productbuilder ready to format a product
     *
     * @return a StringBuilder object representing the product builder
     */
    public StringBuilder getProductBuilder() {
        this.initProductBuilder();
        return this.productBuilder;
    }

    /**
     * Prepare a product attribute to be formatted for adding to txt file
     *
     * @param productAttribute
     * @param startIndex
     * @param endIndex
     */
    private void stringBuilderAttribute(String productAttribute, int startIndex, int endIndex) {

        this.productBuilder.replace(startIndex, endIndex, productAttribute);
    }

    /**
     * Format a product, so it can be added to the txt file.
     *
     * @param outputMode Product Object
     */
    public void stringBuilderProduct(boolean outputMode) {

        if (!outputMode) {
            stringBuilderAttribute(this.getUPC(), Product.UPC_STARS, Product.UPC_ENDS);
            stringBuilderAttribute(this.getDescription(), Product.DESC_STARS, Product.DESC_ENDS);
            stringBuilderAttribute(formatter.format(this.getPrice()), Product.PRICE_STARS, Product.PRICE_ENDS);
        } else {
            int descriptionStarts = 0,
                    descriptionEnds = Product.DESC_ENDS - Product.DESC_STARS,
                    priceStarts = descriptionStarts + descriptionEnds + 1,
                    priceEnds = priceStarts + (Product.PRICE_ENDS - Product.PRICE_STARS),
                    upcStarts = priceEnds + 1,
                    upcEnds = productBuilder.capacity() - 1;

            stringBuilderAttribute(this.getDescription(), descriptionStarts, descriptionEnds);
            stringBuilderAttribute(String.valueOf(this.getPrice()), priceStarts, priceEnds);
            stringBuilderAttribute(this.getUPC(), upcStarts, upcEnds);
        }

    }

    /**
     * Compares to Products by description
     *
     * @param compareProduct Product object to be compared
     * @return the object which lexically is smaller.
     */
    @Override
    public int compareTo(Product compareProduct) {
        return this.getDescription().compareTo(compareProduct.getDescription());
    }

    /**
     *
     * @return a String representing a product string ready to be outputted from
     * the transaction file
     */
    public String productReaderToString() {
        this.initProductBuilder();
        this.stringBuilderProduct(true);
        return productBuilder.toString();
    }

    /**
     *
     * @return a product string ready to be written in the transaction file
     */
    public String productWritterToString() {
        this.initProductBuilder();
        this.stringBuilderProduct(false);
        return productBuilder.toString();
    }

}
