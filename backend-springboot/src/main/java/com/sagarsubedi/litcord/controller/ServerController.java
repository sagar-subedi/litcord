package com.sagarsubedi.litcord.controller;

import com.sagarsubedi.litcord.Exceptions.ServerCreationConflictException;
import com.sagarsubedi.litcord.dto.request.ServerCreateDTO;
import com.sagarsubedi.litcord.model.Server;
import com.sagarsubedi.litcord.service.server.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class ServerController {
    @Autowired
    private ServerService serverService;

    @PostMapping
    public ResponseEntity<String> createServer(@RequestBody ServerCreateDTO server){
        try{
            Server createdServer = serverService.createServer(server.getName(), server.getUserId());
            return new ResponseEntity<>(createdServer.getId().toString(), HttpStatus.CREATED);
        }catch(ServerCreationConflictException e){
            return new ResponseEntity<>("Server already present", HttpStatus.CONFLICT);
        }catch(Exception e){
            return new ResponseEntity<>("Something happened. Channel not created.", HttpStatus.CONFLICT);
        }
    }
}