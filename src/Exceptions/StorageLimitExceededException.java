package Exceptions;

public class StorageLimitExceededException extends Exception {
    public StorageLimitExceededException(String message) {
        super(message);
    }
}
