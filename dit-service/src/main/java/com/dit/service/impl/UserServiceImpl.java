package com.dit.service.impl;

import com.dit.Customer;
import com.dit.Person;
import com.dit.account.User;
import com.dit.repository.UserRepository;
import com.dit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User buildUser(UserProfile userProfile) {
        Person person = new Customer();
        person.setFirstName(userProfile.getFirstName());
        person.setLastName(userProfile.getLastName());

        User user = new User();
        user.setPerson(person);
        user.setEmail(userProfile.getEmail());
        user.setUsername(userProfile.getUsername());
        return user;
    }

    @Override
    public List<User> findByUserConnectionProviderId(String providerId) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }


    @Override
    public User findByUsernameAndPasswordAndRestaurant(String username, String password, String regNo) {
        List<User> users = userRepository.findByUsernameAndPassword(username, password);
        for (User user : users) {
            if (regNo.equals(user.getPerson().getRestaurant().getRegNo())) {
                return user;
            }
        }
        return null;
    }
}
