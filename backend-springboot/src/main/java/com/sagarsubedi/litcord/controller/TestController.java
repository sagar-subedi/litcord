package com.sagarsubedi.litcord.controller;

import com.sagarsubedi.litcord.dao.MessageRepository;
import com.sagarsubedi.litcord.dummydata.User;
import com.sagarsubedi.litcord.dummydata.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
     private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @GetMapping("test/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        return userService.getUserById(userId)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/test")
    public   ResponseEntity<String> testRoute(){
        return new ResponseEntity<>("Working. This is response from port 8081 inside EC2", HttpStatus.OK);
    }
}
