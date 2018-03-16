package com.nativapps.arpia.database.entity;

import javax.persistence.*;

/**
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1
 */
@Entity
@DiscriminatorValue(value = "ME")
public class MessengerEgress extends Egress {

    @Column(name = "TYPE_CONCEPT")
    @Enumerated(EnumType.STRING)
    private TypeConcept typeConcept;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DEBT_ID", nullable = false)
    private Debt debt;

    @ManyToOne
    @JoinColumn(name = "MESSENGER_ID", nullable = false)
    private Messenger messenger;

    public TypeConcept getTypeConcept() {
        return typeConcept;
    }

    public void setTypeConcept(TypeConcept typeConcept) {
        this.typeConcept = typeConcept;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public Debt getDebt() {
        return debt;
    }

    public void setDebt(Debt debt) {
        this.debt = debt;
    }

}
