package PaymentVerifier;

/**
 *
 * @author Brian Parra
 */
import java.util.Random;
import Transactions.Invoice;


public class PaymentVerifier {
    private Random randomGen;
    private int randomChance;
    private Invoice invoice;
    
    /**
     * Constructor
     */
    public PaymentVerifier(){
        randomGen = new Random();
    }
    
    /**
     * Takes an invoice And does some simple checking to see if its valid
     * @param invoice invoice to process
     * @return true if valid invoice
     */
    public boolean verify(Invoice invoice){
        
        
        randomChance = randomGen.nextInt(9);
        
        if(randomChance == 0 || (invoice.getAmountReturned() < 0)){
            
            return false;
        }else{
            return true;
        }
        

    }
    
}
