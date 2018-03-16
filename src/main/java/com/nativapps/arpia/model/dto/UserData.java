package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.UserType;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0.1
 */
@XmlDiscriminatorNode("utype")
public class UserData {

    private String username;

    private String email;

    private String displayName;

    private Long roleId;

    @XmlElement(name = "userType")
    private UserType type;

    private String urlPic;

    private List<Long> operations;

    public UserData() { }

    public UserData(String username, String email, String displayName, Long roleId,
                    UserType type, String urlPic, List<Long> operations) {
        this.username = username;
        this.email = email;
        this.displayName = displayName;
        this.roleId = roleId;
        this.type = type;
        this.urlPic = urlPic;
        this.operations = operations;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getUrlPic() {
        return urlPic;
    }

    public void setUrlPic(String urlPic) {
        this.urlPic = urlPic;
    }

    public List<Long> getOperations() {
        return operations;
    }

    public void setOperations(List<Long> operations) {
        this.operations = operations;
    }

}
