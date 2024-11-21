package src.helper;

/**
 * Thrown by a {@code Scanner} to indicate that the token
 * retrieved is not within the specified range.
 * @author Kee, Seann
 * @version 1.0
 * @since 2024-11-20
 */
public class OutOfRange extends Exception{
    
    /**
     * Constructor that initializes the error message.
     */
    public OutOfRange(){
        super("Input is out of allowed range");
    }

    /**
     * Overridden constructor that initializes the error message with the specified message.
     * @param message error message to be displayed.
     */
    public OutOfRange(String message){
        super(message);
    }
}