package com.dit.social;

public class UserConnection {

    private String id;
    private SocialNetwork providerId;
    private String displayName;
    private String profileUrl;
    private String accessToken;
    private String secret;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SocialNetwork getProviderId() {
        return providerId;
    }

    public void setProviderId(SocialNetwork providerId) {
        this.providerId = providerId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
