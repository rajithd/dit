package models;

import com.dit.Manager;
import com.dit.Owner;
import com.dit.Person;
import com.dit.Staff;
import com.dit.account.Permission;

public class PersonFactory {

    public Person personFactory(String firstName, String lastName, String role) {
        Person person = null;
        if (role.equals(Permission.OWNER.name())) {
            person = new Owner();
        }
        if (role.equals(Permission.MANAGER.name())) {
            person = new Manager();
        }
        if (role.equals(Permission.STAFF.name())) {
            person = new Staff();
        }
        if (person == null) {
            return null;
        }
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }

}
