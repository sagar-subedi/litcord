package com.sagarsubedi.litcord.controller;

import com.sagarsubedi.litcord.dao.MessageRepository;
import com.sagarsubedi.litcord.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
public class TestController {

    @Autowired
     private MessageRepository messageRepository;

    @GetMapping("/")
    public   ResponseEntity<String> testRoute(){
        Message message = new Message();

        messageRepository.save(message);
        try {

        }catch (Exception e){
            return new ResponseEntity<>("Some freaking error occurred", HttpStatus.BAD_GATEWAY);
        }
        messageRepository.save(message);
        return new ResponseEntity<>("Everything is working fine", HttpStatus.OK);
    }
}
