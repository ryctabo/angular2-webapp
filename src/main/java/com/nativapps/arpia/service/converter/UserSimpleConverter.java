package com.nativapps.arpia.service.converter;

import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.dto.UserRequest;
import com.nativapps.arpia.model.dto.UserResponse;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class UserSimpleConverter implements DtoConverter<User, UserRequest, UserResponse> {

    private static final UserSimpleConverter INSTANCE = new UserSimpleConverter();

    /**
     * Don't let anyone instantiate this class.
     */
    private UserSimpleConverter() { }

    public static UserSimpleConverter instance() {
        return INSTANCE;
    }

    @Override
    public User convertToEntity(UserRequest data) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public UserResponse convertToDto(User user) {
        if (user == null)
            return null;

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setDisplayName(user.getDisplayName());
        response.setEmail(user.getEmail());
        response.setUrlPic(user.getUrlPic());
        response.setUsername(user.getUsername());

        return response;
    }

}
