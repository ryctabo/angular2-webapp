package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 *
 * @version 1.0
 */
@Entity
@Table(name = "PROHIBITION")
@NamedQueries({
    @NamedQuery(name = "prohibition.findByMessengerId",
            query = "SELECT r FROM Prohibition r WHERE r.messenger.id = :messengerId")
}
)
public class Prohibition implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSENGER_ID", nullable = false)
    private Messenger messenger;

    @Column(name = "DOCUMENT")
    private boolean document;

    @Column(name = "SHOPPING")
    private boolean shopping;

    @Column(name = "RENT")
    private boolean rent;

    @Column(name = "MONEY")
    private boolean money;

    @Column(name = "LIQUEUR")
    private boolean liqueur;

    @Column(name = "FOOD")
    private boolean food;

    @Column(name = "SOAT")
    private boolean soat;

    @Column(name = "BANK")
    private boolean bank;

    public Prohibition() {
    }

    public Prohibition(boolean document, boolean shopping, boolean rent,
            boolean money, boolean liqueur, boolean food, boolean soat,
            boolean bank) {
        this.document = document;
        this.shopping = shopping;
        this.rent = rent;
        this.money = money;
        this.liqueur = liqueur;
        this.food = food;
        this.soat = soat;
        this.bank = bank;
    }

    public Prohibition(long id, boolean document, boolean shopping,
            boolean rent, boolean money, boolean liqueur, boolean food,
            boolean soat, boolean bank) {
        this.id = id;
        this.document = document;
        this.shopping = shopping;
        this.rent = rent;
        this.money = money;
        this.liqueur = liqueur;
        this.food = food;
        this.soat = soat;
        this.bank = bank;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDocument() {
        return document;
    }

    public void setDocument(boolean document) {
        this.document = document;
    }

    public boolean isShopping() {
        return shopping;
    }

    public void setShopping(boolean shopping) {
        this.shopping = shopping;
    }

    public boolean isRent() {
        return rent;
    }

    public void setRent(boolean rent) {
        this.rent = rent;
    }

    public boolean isMoney() {
        return money;
    }

    public void setMoney(boolean money) {
        this.money = money;
    }

    public boolean isLiqueur() {
        return liqueur;
    }

    public void setLiqueur(boolean liqueur) {
        this.liqueur = liqueur;
    }

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean isSoat() {
        return soat;
    }

    public void setSoat(boolean soat) {
        this.soat = soat;
    }

    public boolean isBank() {
        return bank;
    }

    public void setBank(boolean bank) {
        this.bank = bank;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

}
