/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nativapps.arpia.rest.bean;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 */
public enum EgressType {
    /**
     * Indicates that the egress is from management.
     * <p>
     * Translate: Egreso de gerencia
     */
    MANAGEMENT_EGRESS,
    /**
     * Indicates that the egress is from a messenger.
     * <p>
     * Translate: Egreso de mensajero
     */
    MESSENGER_EGRESS,
    /**
     * Indicates that the egress is from a customer.
     * <p>
     * Translate: Egreso de cliente
     */
    CUSTOMER_EGRESS,
    /**
     * Indicates that the egress is from an operator.
     * <p>
     * Translate: Egreso de operador
     */
    OPERATOR_EGRESS
}
