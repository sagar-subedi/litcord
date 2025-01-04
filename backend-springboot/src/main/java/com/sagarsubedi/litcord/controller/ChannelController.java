package com.sagarsubedi.litcord.controller;

import com.sagarsubedi.litcord.Exceptions.ChannelCreationConflictException;
import com.sagarsubedi.litcord.dto.ChannelDTO;
import com.sagarsubedi.litcord.model.Channel;
import com.sagarsubedi.litcord.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @PostMapping
    public ResponseEntity<String> createChannel(@RequestBody ChannelDTO channel){
        try{
            Channel createdChannel = channelService.createChannel(channel.getName(), channel.getServerId(), channel.getAdminId(), channel.getType());
            return new ResponseEntity<>(createdChannel.getId().toString(), HttpStatus.CREATED);
        }catch(ChannelCreationConflictException e){
            return new ResponseEntity<>("Channel already present", HttpStatus.CONFLICT);
        }catch(Exception e){
            return new ResponseEntity<>("Something happened. Channel not created.", HttpStatus.CONFLICT);
        }
    }

    // Retrieve a channel by ID
    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannelById(@PathVariable Long id) {
        Optional<Channel> channel = channelService.getChannelById(id);
        return channel.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update a channel
    @PutMapping("/{id}")
    public ResponseEntity<Channel> updateChannel(@PathVariable Long id, @RequestBody Channel updatedChannel) {
        Optional<Channel> channel = channelService.updateChannel(id, updatedChannel);
        return channel.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete a channel
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
        boolean deleted = channelService.deleteChannel(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // List all channels
    @GetMapping
    public ResponseEntity<List<Channel>> getAllChannels() {
        List<Channel> channels = channelService.getAllChannels();
        return new ResponseEntity<>(channels, HttpStatus.OK);
    }
}
