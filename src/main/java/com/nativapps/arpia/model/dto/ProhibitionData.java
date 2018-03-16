package com.nativapps.arpia.model.dto;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 *
 * @version 1.0
 */
public class ProhibitionData {

    protected Boolean document;
    protected Boolean shopping;
    protected Boolean rent;
    protected Boolean money;
    protected Boolean liqueur;
    protected Boolean food;
    protected Boolean soat;
    protected Boolean bank;

    public ProhibitionData() {
    }

    public ProhibitionData(Boolean document, Boolean shopping, Boolean rent,
            Boolean money, Boolean liqueur, Boolean food, Boolean soat,
            Boolean bank) {
        this.document = document;
        this.shopping = shopping;
        this.rent = rent;
        this.money = money;
        this.liqueur = liqueur;
        this.food = food;
        this.soat = soat;
        this.bank = bank;
    }

    public Boolean getDocument() {
        return document;
    }

    public void setDocument(Boolean document) {
        this.document = document;
    }

    public Boolean getShopping() {
        return shopping;
    }

    public void setShopping(Boolean shopping) {
        this.shopping = shopping;
    }

    public Boolean getRent() {
        return rent;
    }

    public void setRent(Boolean rent) {
        this.rent = rent;
    }

    public Boolean getMoney() {
        return money;
    }

    public void setMoney(Boolean money) {
        this.money = money;
    }

    public Boolean getLiqueur() {
        return liqueur;
    }

    public void setLiqueur(Boolean liqueur) {
        this.liqueur = liqueur;
    }

    public Boolean getFood() {
        return food;
    }

    public void setFood(Boolean food) {
        this.food = food;
    }

    public Boolean getSoat() {
        return soat;
    }

    public void setSoat(Boolean soat) {
        this.soat = soat;
    }

    public Boolean getBank() {
        return bank;
    }

    public void setBank(Boolean bank) {
        this.bank = bank;
    }

}
