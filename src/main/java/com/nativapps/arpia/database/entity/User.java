package com.nativapps.arpia.database.entity;

import com.nativapps.arpia.database.converter.PasswordConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.1.0
 */
@Entity
@Table(name = "`USER`")
@NamedQueries({
        @NamedQuery(name = "user.findByUsername",
                query = "select u from User u where u.username = :username"),
        @NamedQuery(name = "user.findByEmail",
                query = "select u from User u where u.email = :email"),
        @NamedQuery(name = "user.findByType",
                query = "select u from User u where u.type = :type"),
        @NamedQuery(name = "user.login",
                query = "select u from User u where (u.username = :dataUser or "
                        + "u.email = :dataUser) and u.password = :password"),
        @NamedQuery(name = "user.search",
                query = "select u from User u where u.username = :data or u.email = :data")
})
public class User implements Serializable {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "USERNAME", unique = true, length = 12)
    private String username;

    @Column(name = "EMAIL", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "`PASSWORD`", nullable = false)
    @Convert(converter = PasswordConverter.class)
    private String password;

    @Column(name = "DISPLAY_NAME", nullable = false, length = 45)
    private String displayName;

    @Column(name = "URL_PIC")
    private String urlPic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @Column(name = "ROLE_ID", insertable = false, updatable = false)
    private Long roleId;

    @Column(name = "USER_TYPE", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private UserType type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USR_CMP",
            joinColumns = @JoinColumn(name = "USR_ID",
                    referencedColumnName = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "CMP_ID",
                    referencedColumnName = "OPERATION_ID"))
    private List<Operation> operations;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED", nullable = false, updatable = false)
    private Calendar created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED")
    private Calendar updated;

    public User() {
        this.enabled = true;
        this.operations = new ArrayList<>();
        this.created = Calendar.getInstance();
    }

    public User(long id) {
        this.id = id;
        this.enabled = true;
        this.operations = new ArrayList<>();
        this.created = Calendar.getInstance();
    }

    public User(String email, String password, String displayName, UserType type) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.type = type;
        this.enabled = true;
        this.operations = new ArrayList<>();
        this.created = Calendar.getInstance();
    }

    public User(String username, String email, String password,
                String displayName, String urlPic, UserType type) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.urlPic = urlPic;
        this.type = type;
        this.enabled = true;
        this.operations = new ArrayList<>();
        this.created = Calendar.getInstance();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUrlPic() {
        return urlPic;
    }

    public void setUrlPic(String urlPic) {
        this.urlPic = urlPic;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getUpdated() {
        return updated;
    }

    public void setUpdated(Calendar updated) {
        this.updated = updated;
    }
}
