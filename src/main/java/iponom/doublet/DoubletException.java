package iponom.doublet;

/**
 * @author Ilya Ponomarev.
 */
public class DoubletException extends RuntimeException {

    public DoubletException() {
    }

    public DoubletException(String message) {
        super(message);
    }

    public DoubletException(String message, Throwable cause) {
        super(message, cause);
    }

    public DoubletException(Throwable cause) {
        super(cause);
    }

    public DoubletException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
