package com.dit.service;

import com.dit.account.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.UserProfile;

import java.util.List;

public interface UserService extends UserDetailsService {

    /**
     * Find by username
     *
     * @param username {@code User}
     * @return {@code User}
     */
    User findByUsername(String username);

    /**
     * Find by id
     *
     * @param id - user id
     * @return {@code User}
     */
    User findById(String id);

    /**
     * Save user
     *
     * @param user {@code User}
     * @return {@code User}
     */
    User save(User user);

    /**
     * Build user
     *
     * @param userProfile
     * @return
     */
    User buildUser(UserProfile userProfile);

    /**
     * Find user by provider id
     *
     * @param providerId
     * @return
     */
    List<User> findByUserConnectionProviderId(String providerId);

    User findByUsernameAndPassword(String username, String password);

}
