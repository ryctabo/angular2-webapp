package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "CHIP")
public class Chip implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "MESSENGER_ID", nullable = false)
    private Messenger messenger;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "`TYPE`", nullable = false)
    private ChipType type;

    @OneToMany(mappedBy = "chip",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<ChipItem> items;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED", nullable = false, updatable = false)
    private Calendar created;

    public Chip() {
        this.items = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public ChipType getType() {
        return type;
    }

    public void setType(ChipType type) {
        this.type = type;
    }

    public void addItem(ChipItem item) {
        if (item.getChip() != this)
            item.setChip(this);
        this.items.add(item);
    }

    public Set<ChipItem> getItems() {
        return items;
    }

    public void setItems(Set<ChipItem> items) {
        this.items = items;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }
}
