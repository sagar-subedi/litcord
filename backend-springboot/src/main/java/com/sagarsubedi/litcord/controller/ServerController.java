package com.sagarsubedi.litcord.controller;

import com.sagarsubedi.litcord.Exceptions.ServerNotFoundException;
import com.sagarsubedi.litcord.dto.ChannelDTO;
import com.sagarsubedi.litcord.dto.ServerDTO;
import com.sagarsubedi.litcord.model.Channel;
import com.sagarsubedi.litcord.model.Server;
import com.sagarsubedi.litcord.service.ServerService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/server")
public class ServerController {
    @Autowired
    private ServerService serverService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ServerDTO> createServer(
            @RequestParam("name") String name,
            @RequestParam("userId") Long userId,
            @RequestParam("dp") MultipartFile dp
    ){
        try{
// Add validation to ensure that a user can only add a server to only his account, i.e username fromm token should match with the uerId passed with request
            if(!name.isBlank()) {
                Server createdServer = serverService.createServer(name, userId, dp);
                ServerDTO createdServerDTO = modelMapper.map(createdServer, ServerDTO.class);
                return new ResponseEntity<>(createdServerDTO, HttpStatus.CREATED);
            }
            throw new Exception("Server name can't be blank");
        } catch(Exception e){
            return new ResponseEntity<>(new ServerDTO(), HttpStatus.CONFLICT);
        }
    }

    // Get a server by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServerDTO> getServer(@PathVariable Long id) {
        ServerDTO server = serverService.getServerById(id);
        return server != null
                ? ResponseEntity.ok(server)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Update a server
    @PutMapping("/{id}")
    public ResponseEntity<String> updateServer(@PathVariable Long id, @Valid @RequestBody ServerDTO server) {
        try {
            serverService.updateServer(id, server);
            return ResponseEntity.ok("Server updated successfully.");
        } catch (ServerNotFoundException e) {
            return new ResponseEntity<>("Server not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong. Server not updated.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a server
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServer(@PathVariable Long id) {
        try {
            serverService.deleteServer(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServerNotFoundException e) {
            return new ResponseEntity<>("Server not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong. Server not deleted.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public List<ServerDTO> getServersForUserByUserId(@RequestParam Long userId) {
            return serverService.getServersForUser(userId);
    }

    @GetMapping("/from-email/{email}")
    public ResponseEntity<List<ServerDTO>> getServersByEmail(@PathVariable String email) {
        List<ServerDTO> servers = serverService.getServersByEmail(email);
        return ResponseEntity.ok(servers);
    }

    @PostMapping("/channels")
    public ResponseEntity<ChannelDTO> addChannel(@RequestBody ChannelDTO channel) {
        Channel channelEntity = serverService.addChannelToServer(channel);
        ChannelDTO dto = modelMapper.map(channelEntity, ChannelDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // List all servers
    @GetMapping
    public ResponseEntity<List<ServerDTO>> listServers() {
        List<ServerDTO> servers = serverService.getAllServers();
        return ResponseEntity.ok(servers);
    }
}