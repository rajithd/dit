package com.dit.connector;

import com.dit.Restaurant;
import com.dit.Zone;
import com.dit.account.User;
import com.dit.response.Success;
import com.dit.service.RestaurantService;
import com.dit.service.UserService;
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

}
