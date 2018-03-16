package com.nativapps.arpia.database.entity;

/**
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public enum TypeEgress {
    /**
     * Indicates that the egress comes from a customer activity.
     * <p>
     * Translate: Cliente
     */
    CUSTOMER, 
    /**
     * Indicates that the egress comes from a messenger activity.
     * <p>
     * Translate: Mensajero
     */
    MESSENGER, 
    /**
     * Indicates that the egress comes from a operator activity.
     * <p>
     * Translate: Operador
     */
    OPERATOR, 
    /**
     * Indicates that the egress comes from a manager authorization.
     * <p>
     * Translate: Teléfono celular
     */
    MANAGEMENT_AUTHORIZATION
}
