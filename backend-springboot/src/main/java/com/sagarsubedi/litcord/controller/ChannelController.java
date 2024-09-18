package com.sagarsubedi.litcord.controller;

import com.sagarsubedi.litcord.Exceptions.ChannelCreationConflictException;
import com.sagarsubedi.litcord.dto.request.ChannelCreateDTO;
import com.sagarsubedi.litcord.model.Channel;
import com.sagarsubedi.litcord.service.channel.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @PostMapping
    public ResponseEntity<String> createChannel(@RequestBody ChannelCreateDTO channel){
        try{
            Channel createdChannel = channelService.createChannel(channel.getName(), channel.getServerId(), channel.getProfileId());
            return new ResponseEntity<>(createdChannel.getId().toString(), HttpStatus.CREATED);
        }catch(ChannelCreationConflictException e){
            return new ResponseEntity<>("Channel already present", HttpStatus.CONFLICT);
        }catch(Exception e){
            return new ResponseEntity<>("Something happened. Channel not created.", HttpStatus.CONFLICT);
        }
    }
}
