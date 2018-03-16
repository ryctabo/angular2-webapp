package com.nativapps.arpia.model;

/**
 * The <strong>BuilderPattern</strong> interface contains all the methods
 * necessary to implement the builder pattern.
 *
 * @param <T> type of class
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface BuilderPattern<T> {

    /**
     * Create a instance of {@code T} object.
     *
     * @return T object.
     */
    T build();

}
