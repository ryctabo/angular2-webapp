package com.nativapps.arpia.mail.exception;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class DontReadTemplateException extends RuntimeException {

    /**
     * Creates a new instance of <code>DontReadTemplateException</code> without
     * detail message.
     */
    public DontReadTemplateException() {
        super("The selected template can not be read, please check if the template " +
                "contains the required format and the file exists.");
    }

    /**
     * Constructs an instance of <code>DontReadTemplateException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DontReadTemplateException(String msg) {
        super(msg);
    }
}
