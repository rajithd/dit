package com.dit.service;

import com.dit.social.SocialNetwork;
import com.dit.social.UserConnection;
import org.springframework.social.connect.UserProfile;

public interface UserConnectionService {

    /**
     * Build user connection
     * @param userProfile {code Twitter#userProfile}
     * @param socialNetwork  {code socialNetwork}
     * @return
     */
    UserConnection buildUserConnection(UserProfile userProfile, SocialNetwork socialNetwork);

}
