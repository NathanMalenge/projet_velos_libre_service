package fil.l3.coo.user.exceptions;

/**
 * Exception thrown when a given amount is negative.
 */
public class NegativeAmountException extends Exception{

    /**
     * Constructs a new NegativeAmountException with the specified detail message.
     * 
     * @param message the detail message explaining the reason for the exception
     */
    public NegativeAmountException(String message) {
        super(message);
    }
}
