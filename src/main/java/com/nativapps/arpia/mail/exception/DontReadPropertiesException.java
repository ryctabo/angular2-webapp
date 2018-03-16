package com.nativapps.arpia.mail.exception;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class DontReadPropertiesException extends RuntimeException {

    /**
     * Creates a new instance of <code>DontReadPropertiesException</code>
     * without detail message.
     */
    public DontReadPropertiesException() {
        super("Can not read properties to set up a session.");
    }

    /**
     * Constructs an instance of <code>DontReadPropertiesException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DontReadPropertiesException(String msg) {
        super(msg);
    }

}
