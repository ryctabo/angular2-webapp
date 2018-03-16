package com.nativapps.arpia.mail.exception;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class InvalidTemplateException extends RuntimeException {

    /**
     * Creates a new instance of <code>InvalidTemplateException</code> without
     * detail message.
     */
    public InvalidTemplateException() {
        super("The template does not contain the format needed to be used.");
    }

    /**
     * Constructs an instance of <code>InvalidTemplateException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidTemplateException(String msg) {
        super(msg);
    }
}
