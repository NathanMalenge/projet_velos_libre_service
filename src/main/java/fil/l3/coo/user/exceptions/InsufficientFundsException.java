package fil.l3.coo.user.exceptions;

/**
 * Exception thrown when a money deduction operation fails.
 * This can happen when trying to deduct a negative amount or when there are insufficient funds.
 */
public class InsufficientFundsException extends Exception {
    
    /**
     * Constructs a new InsufficientFundsException with the specified detail message.
     * 
     * @param message the detail message explaining the reason for the exception
     */
    public InsufficientFundsException(String message) {
        super(message);
    }

}