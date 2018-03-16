package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
@Entity
@Table(name = "NEIGHBORHOOD")
@NamedQueries({
        @NamedQuery(name = "neighborhood.findByName",
                query = "SELECT n FROM Neighborhood n WHERE n.name = :name")
})
public class Neighborhood implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    public Neighborhood() { }

    public Neighborhood(long id) {
        this.id = id;
    }

    public Neighborhood(String name) {
        this.name = name;
    }

    public Neighborhood(long id, String name) {
        this.id = id;
        this.name = name;
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

}
