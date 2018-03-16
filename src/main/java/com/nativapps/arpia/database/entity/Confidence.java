package com.nativapps.arpia.database.entity;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public enum Confidence {
    /**
     * Indicates that the confidence level is null.
     * <p>
     * Translate: Nulo
     */
    NULL, 
    /**
     * Indicates that the confidence level is low.
     * <p>
     * Translate: Bajo
     */
    LOW,
    /**
     * Indicates that the confidence level is medium.
     * <p>
     * Translate: Intermedio
     */
    MEDIUM,
    /**
     * Indicates that the confidence level is high.
     * <p>
     * Translate: Alto
     */
    HIGH,
    /**
     * Indicates that the confidence level is the highest.
     * <p>
     * Translate: Súper Alto
     */
    SUPER_HIGH
}
