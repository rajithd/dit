package com.dit.service.impl;

import com.dit.service.UserConnectionService;
import com.dit.social.SocialNetwork;
import com.dit.social.UserConnection;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class UserConnectionServiceImpl implements UserConnectionService {

    @Override
    public UserConnection buildUserConnection(UserProfile userProfile, SocialNetwork socialNetwork) {
        UserConnection userConnection = new UserConnection();
        userConnection.setProviderId(socialNetwork);
        userConnection.setDisplayName(userProfile.getName());
        return userConnection;
    }
}
