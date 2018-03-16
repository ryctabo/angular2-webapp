package com.nativapps.arpia.model.exception;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 */
public class FileOperationException extends RuntimeException {

    /**
     * Creates a new instance of <code>FileOperationException</code> without
     * detail message.
     */
    public FileOperationException() {
    }

    /**
     * Constructs an instance of <code>FileOperationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FileOperationException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs an instance of <code>FileOperationException</code> with the
     * specified cause and with detail message.
     *
     * @param message the detail message
     * @param cause throwable cause
     */
    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
