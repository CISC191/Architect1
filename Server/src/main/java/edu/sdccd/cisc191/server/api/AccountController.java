package edu.sdccd.cisc191.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sdccd.cisc191.common.entities.User;
import edu.sdccd.cisc191.server.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/user")
public class AccountController {

    @Autowired(required = true)
    private UserService userService;


    @GetMapping("/sanitycheck")
    public String ineedsanity() {
        return "yes, heres ur sanity";
    }
    
    @PostMapping("/add")
    public void add(User user) {
        // System.out.println(user.getName());
        userService.createUser(user);
    }
}
