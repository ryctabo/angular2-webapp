package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gustavo Pachecho <ryctabo@gmail.com>
 * @version 2.0
 */
@Entity
@Table(name = "INVENTORY")
@NamedQueries({
        @NamedQuery(name = "inventory.findByName",
                query = "select i from Inventory i where i.name = :name")
})
public class Inventory implements Serializable {

    @Id
    @Column(name = "ID")
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME", nullable = false, length = 25, unique = true)
    private String name;

    @Column(name = "COUNT", nullable = false)
    private int count;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private Set<Stock> stocks;

    @Column(name = "REGISTER_DATE", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registerDate;

    @Column(name = "UPDATE_DATE", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updateDate;

    public Inventory() {
        this.stocks = new HashSet<>();
    }

    public Inventory(long id) {
        this.id = id;
        this.stocks = new HashSet<>();
    }

    public Inventory(String name, int count) {
        this.name = name;
        this.count = count;
        this.stocks = new HashSet<>();
    }

    public Inventory(String name, int count, Calendar registerDate, Calendar updateDate) {
        this.name = name;
        this.count = count;
        this.registerDate = registerDate;
        this.updateDate = updateDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public void addStock(Stock stock) {
        if (stock.getInventory() != this)
            stock.setInventory(this);
        this.stocks.add(stock);
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public Calendar getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Calendar updateDate) {
        this.updateDate = updateDate;
    }
}
