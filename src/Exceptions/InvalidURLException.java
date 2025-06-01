package Exceptions;

/**
 * Custom exception for invalid URLs.
 */
public class InvalidURLException extends Exception {
    public InvalidURLException(String message) {
        super(message);
    }
}
