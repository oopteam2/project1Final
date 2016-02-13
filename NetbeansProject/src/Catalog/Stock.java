package Catalog;

/**
 * @author: Jose Ortiz Costa
 * Date: 02/09/2016
 * Modified:  02/09/2016
 * Description: This class contains methods to work with the catalog of products of a store
 * Usage: 
 *      1. if you are working in other package, import the Catalog package.
 *           import Catalog.*;
 *      2. Create an object of this class
 *           Stock catalog = new Stock();
 *      3. Example: Get a product from the catalog  given its upc as argument
 *           Product product = catalog.getProduct("0001");
 *      4. Example: Check is a product exist in the catalog
 *           boolean isInCatalog = catalog.isProductInStock("0001");
 *      5. Example: Get the list of all the products
 *           ArrayList <Product> products = catalog.getProductsFromStock();
 *      6. Example: View all products in the catalog
 *           catalog.viewCatalog();
 * 
 * 
 * @see: Product.java
 */
import java.util.ArrayList;
import java.io.*;
import java.util.Collections;

public class Stock implements IStock {

    private ArrayList<Product> products; // list of products
    private File catalog;
    private String store = null; // store
    private StringBuilder productBuilder; // podruct builder to format for txt file
    /**
     * Constructor
     * @param catalogDatabasePath  path to the database catalog 
     */
    public Stock(String catalogDatabasePath) {
        this.products = new ArrayList<>();
        this.catalog = new File(catalogDatabasePath);
    }
    /**
     * Constructor
     * @param storeName name of the store
     * @param catalogDatabasePath  catalog file
     */
    public Stock(String storeName, String catalogDatabasePath) {
        this.store = storeName;
        this.catalog = new File(catalogDatabasePath);
        this.products = new ArrayList<>();
        
    }
    /**
     * Constructor
     * @param products list of products
     */
    public Stock(ArrayList<Product> products) {

        this.products = products;

    }
    /**
     * Initialize a StringBuilder of size 45 to allocate products attributes
     */
    private void initProductBuilder ()
    {
        this.productBuilder = new StringBuilder ();
        int i = 0;
        for (i = 0; i<45; i++)
            productBuilder.append(" ");
    }
    /**
     * Loads catalog of products in memory
     */
    public void loadCatalog() {
        try {
            FileInputStream fis = new FileInputStream(catalog);
            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            while ((line = br.readLine()) != null) {
                Product product = formatProductOutput(line);
                products.add(product);
            }
            br.close();
        } catch (IOException err) {
            System.out.println("Error: " + err.getMessage());
        }

    }
    /**
     * 
     * @return a catalog with all its products
     */
    public ArrayList<Product> getProductsFromStock() {
        return products;
    }
    
    /**
     * Format the string line representing a product from the catalog
     *        and convert it to a Product object
     * @param line string line representing a product in the catalog
     * @return a Product object representing the product in the catalog
     */
    private Product formatProductOutput(String line) {
        Product product = new Product();
        String upc, description;
        double price;
        upc = line.substring(Product.UPC_STARS, Product.UPC_ENDS);
        description = line.substring(Product.DESC_STARS, Product.DESC_ENDS);
        price = Double.parseDouble(line.substring(Product.PRICE_STARS, Product.PRICE_ENDS));
        product.setUPC(upc);
        product.setDescription(description);
        product.setPrice(price);
        return product;

    }
    /**
     * Adds a product to the catalog
     * @param product product object to be added
     * @return true if the product was succesfully added
     *         otherwise, returns false.
     */
    public boolean addProduct(Product product) {
        // Add a product to the catalog.
        try
        {
            if (!this.isProductInStock(product.getUPC()))
            {
               FileWriter fw = new FileWriter(this.catalog, true);
               BufferedWriter writer = new BufferedWriter(fw);
               writer.write("\n" + product.productWritterToString());
               writer.flush();
               writer.close();
               
               return true;
            }
        }
        catch (IOException err)
        {
            System.out.println("Error: " + err.getMessage());
        }
        
        return false;
    }
    
   
    /**
     * Checks if a given product by upc is in the catalog
     * @param upc product upc
     * @return true if the product is in the catalog. Otherwise, returns false
     */
    public boolean isProductInStock(String upc) {
        if (!products.isEmpty()) {
            for (Product product : products) {
                if (product.getUPC().equals(upc)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Gets a product given its upc
     * @param upc product upc
     * @return the Product object related to the upc given
     */
    public Product getProduct(String upc) {
        Product product = new Product();
        if (isProductInStock(upc)) {
            for (Product prod : products) {
                if (prod.getUPC().equals(upc)) {
                    product = prod;
                }
            }
        }
        return product;
    }
    
    /**
     * Get a product by a given id or index ( position on the list )
     * @param index position of product on the catalog
     * @return a Product Object
     * Note: Index starts with 0.
     */
    public Product getProduct(int index)
    {
        return products.get(index);
    }
    
    
    /**
     * Prints the whole catalog of products sorted by product description in
     * ascending order.
     */
    public void viewCatalog() {
        
        Collections.sort(products);
        System.out.println("\n\n\n ***STORE CATALOG***");
        for (Product prod : products)
            System.out.println(prod.productReaderToString());
               
    }

}
