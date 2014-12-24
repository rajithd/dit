package com.dit.account;

public enum Permission {
    OWNER, MANAGER, STAFF;

    public static Permission getPermissionVal(String permissionString) throws IllegalAccessException {
        for (Permission permission : Permission.values()) {
            if (permission.name().equals(permissionString)) {
                return permission;
            }
        }
        throw new IllegalAccessException("Wrong enum");

    }


}
