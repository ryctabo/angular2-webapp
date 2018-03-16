package com.nativapps.arpia.model.dto;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class OperationResponse extends OperationData {

    private Long id;

    private UserResponse adminUser;

    public OperationResponse() {
    }

    public OperationResponse(Long id, String name) {
        super(name);
        this.id = id;
    }

    public OperationResponse(String name, String alias, String description,
                             String imageUrl, Long id, UserResponse adminUser) {
        super(name, alias, description, imageUrl);
        this.id = id;
        this.adminUser = adminUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserResponse getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(UserResponse adminUser) {
        this.adminUser = adminUser;
    }
}
