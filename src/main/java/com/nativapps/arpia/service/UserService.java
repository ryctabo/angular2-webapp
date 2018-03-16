package com.nativapps.arpia.service;

import com.nativapps.arpia.database.entity.UserType;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.UserRequest;
import com.nativapps.arpia.model.dto.UserResponse;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.1.0
 */
public interface UserService extends Service, AccountService {

    /**
     * Get all users information from the parameters provider.
     *
     * @param type      user type
     * @param search    value to search
     * @param start     first element to get
     * @param size      list size
     * @param orderBy   property to ordering
     * @param orderType order type ASC or DESC
     * @return list of users
     */
    ListResponse<UserResponse> getAll(UserType type, String search, int start, int size,
                                      String orderBy, OrderType orderType);

    /**
     * Get a user information from the given ID.
     *
     * @param userId user ID
     * @return user information
     */
    UserResponse getUser(Long userId);

    /**
     * Get user from the given email.
     *
     * @param email email
     * @return user data
     */
    UserResponse getUserByEmail(String email);

    /**
     * Get user from the given username.
     *
     * @param username username
     * @return user data
     */
    UserResponse getUserByUsername(String username);

    /**
     * Get user from the given data, this data can be a username or an email.
     *
     * @param data user data
     * @return user information
     */
    UserResponse getUserSearch(String data);

    /**
     * Create a new user in the platform.
     *
     * @param user user data
     * @return user information
     */
    UserResponse addNewUser(UserRequest user);

    /**
     * Update user information from your ID.
     *
     * @param userId user ID
     * @param user   user data
     * @return user information
     */
    UserResponse updateUser(Long userId, UserRequest user);

    /**
     * Delete a user from your ID.
     *
     * @param userId user ID
     * @return user information
     */
    UserResponse deleteUser(Long userId);

    /**
     * Change the state of a user from the given ID.
     *
     * @param userId user ID
     * @param enable true, if you want active the user
     * @return user information
     */
    UserResponse changeStateUser(Long userId, boolean enable);

}