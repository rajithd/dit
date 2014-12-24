package com.dit;

import com.dit.account.Permission;
import org.springframework.stereotype.Component;

@Component
public class PersonFactory {

    public Person personFactory(String role) {
        if (role.equals(Permission.OWNER.name())) {
            return new Owner();
        }
        if (role.equals(Permission.MANAGER.name())) {
            return new Manager();
        }
        if (role.equals(Permission.STAFF.name())) {
            return new Staff();
        }
        return null;
    }
}
