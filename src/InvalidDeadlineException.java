// CO-6: custom exception for invalid deadline
public class InvalidDeadlineException extends Exception {
    public InvalidDeadlineException(String message) {
        super(message);
    }
}
