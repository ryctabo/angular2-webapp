package com.nativapps.arpia.database.entity;

import javax.persistence.*;

/**
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1
 */
@Entity
@DiscriminatorValue(value = "OE")
public class OperatorEgress extends Egress {

    @Column(name = "TYPE_CONCEPT")
    @Enumerated(EnumType.STRING)
    private TypeConcept typeConcept;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DEBT_ID", nullable = false)
    private Debt debt;

    @ManyToOne
    @JoinColumn(name = "OPERATOR_ID", nullable = false)
    private User operator;

    public TypeConcept getTypeConcept() {
        return typeConcept;
    }

    public void setTypeConcept(TypeConcept typeConcept) {
        this.typeConcept = typeConcept;
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public Debt getDebt() {
        return debt;
    }

    public void setDebt(Debt debt) {
        this.debt = debt;
    }

}
