package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.database.entity.UserType;
import com.nativapps.arpia.database.exception.IncorrectCredentialsException;
import com.nativapps.arpia.model.OrderType;

import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.1.0
 */
public interface UserDao extends DataAccessObject<User, Long> {


    /**
     * Get all user from the given parameters.
     *
     * @param type      user type
     * @param search    value to search
     * @param start     initial index of element to get
     * @param size      list size
     * @param orderBy   property to ordering
     * @param orderType order type ASC or DESC
     * @return List of users
     */
    List<User> findAll(UserType type, String search, int start, int size,
                       String orderBy, OrderType orderType);

    /**
     * Get count elements exists with the parameters provider.
     *
     * @param type   user type
     * @param search value to search
     * @return count elements
     */
    long count(UserType type, String search);

    /**
     * Get a user from the given username.
     *
     * @param username username
     * @return User information
     */
    User findByUsername(String username);

    /**
     * Get a user from the given email.
     *
     * @param email email address
     * @return User information
     */
    User findByEmail(String email);

    /**
     * Get a user from the given data, this data can be a username or an email.
     *
     * @param data user data
     * @return user information
     */
    User search(String data);

    /**
     * Get user information if the user data and password is valid.
     *
     * @param userData username or email
     * @param password password
     * @return long, user id
     * @throws IncorrectCredentialsException if the credentials are incorrect
     */
    User login(String userData, String password)
            throws IncorrectCredentialsException;

}
