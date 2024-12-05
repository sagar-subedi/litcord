package com.sagarsubedi.litcord.controller;

import com.sagarsubedi.litcord.Exceptions.ServerCreationConflictException;
import com.sagarsubedi.litcord.Exceptions.ServerNotFoundException;
import com.sagarsubedi.litcord.dto.ServerDTO;
import com.sagarsubedi.litcord.model.Server;
import com.sagarsubedi.litcord.service.server.ServerService;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/server")
public class ServerController {
    @Autowired
    private ServerService serverService;

    @PostMapping
    public ResponseEntity<String> createServer(@RequestBody ServerDTO server){
        try{
            if(!server.getName().isBlank()) {
                Server createdServer = serverService.createServer(server.getName(), server.getUserId());
                return new ResponseEntity<>(createdServer.getId().toString(), HttpStatus.CREATED);
            }
            throw new Exception("Server name can't be blank");
        }catch(ServerCreationConflictException e){
            return new ResponseEntity<>("Server already present", HttpStatus.CONFLICT);
    }catch(Exception e){
            return new ResponseEntity<>("Something happened. Channel not created.", HttpStatus.CONFLICT);
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
            return ResponseEntity.ok("Server deleted successfully.");
        } catch (ServerNotFoundException e) {
            return new ResponseEntity<>("Server not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong. Server not deleted.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // List all servers
    @GetMapping
    public ResponseEntity<List<ServerDTO>> listServers() {
        List<ServerDTO> servers = serverService.getAllServers();
        return ResponseEntity.ok(servers);
    }
}