package com.dit.connector;

import com.dit.*;
import com.dit.account.User;
import com.dit.response.Success;
import com.dit.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/public/admin")
public class AdminConnector {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private PersonFactory personFactory;

    @RequestMapping(method = RequestMethod.GET, value = "/user/{username}/{password}/{regNo}", produces = "application/json")
    @ResponseBody
    public User getUser(@PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("regNo") String regNo) {
        return userService.findByUsernameAndPasswordAndRestaurant(username, password, regNo);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{restaurantId}/zone", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateZone(@PathVariable("restaurantId") String restaurantId, @RequestBody Zone zone) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        restaurant.getZones().add(zone);
        return new ResponseEntity<Success>(new Success(restaurantService.save(restaurant)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{restaurantId}/zone/{zoneId}", produces = "application/json")
    @ResponseBody
    public Zone getZoneById(@PathVariable("restaurantId") String restaurantId, @PathVariable("zoneId") String zoneId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        List<Zone> zones = restaurant.getZones();
        for (Zone zone : zones) {
            if (zoneId.equals(zone.getId())) {
                return zone;
            }
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{userId}/create/user/{role}", produces = "application/json")
    @ResponseBody
    public ResponseEntity createUser(@PathVariable("userId") String userId, @PathVariable("role") String role, @RequestBody User user) {
        User authUser = userService.findById(userId);
        Person person = user.getPerson();
        person.setRestaurant(authUser.getPerson().getRestaurant());

        Person factoryPerson = personFactory.personFactory(role);
        factoryPerson.setFirstName(person.getFirstName());
        factoryPerson.setLastName(person.getLastName());
        Person savedPerson = null;
        if (factoryPerson instanceof Owner) {
            Owner owner = new Owner();
            owner.setFirstName(factoryPerson.getFirstName());
            owner.setLastName(factoryPerson.getLastName());
            owner.setRestaurant(authUser.getPerson().getRestaurant());
            savedPerson = ownerService.save(owner);
        } else if (factoryPerson instanceof Manager) {
            Manager manager = new Manager();
            manager.setFirstName(factoryPerson.getFirstName());
            manager.setLastName(factoryPerson.getLastName());
            manager.setRestaurant(authUser.getPerson().getRestaurant());
            savedPerson = managerService.save(manager);
        } else if (factoryPerson instanceof Staff) {
            Staff staff = new Staff();
            staff.setFirstName(factoryPerson.getFirstName());
            staff.setLastName(factoryPerson.getLastName());
            staff.setRestaurant(authUser.getPerson().getRestaurant());
            savedPerson = staffService.save(staff);
        }
        user.setPerson(savedPerson);
        return new ResponseEntity<Success>(new Success(userService.save(user)), HttpStatus.OK);

    }

}
