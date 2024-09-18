package com.sagarsubedi.litcord.service.server;

import com.sagarsubedi.litcord.Exceptions.ServerCreationConflictException;
import com.sagarsubedi.litcord.dao.ServerRepository;
import com.sagarsubedi.litcord.model.Channel;
import com.sagarsubedi.litcord.model.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ServerService {
    @Autowired
    ServerRepository serverRepository;

    public Server createServer(String name, Long userId) {
        Optional<Server> server = serverRepository.findServerByNameAndUserId(name, userId);
        if(server.isPresent()){
            throw new ServerCreationConflictException();
        }
        String inviteCode = UUID.randomUUID().toString();
        return serverRepository.save(new Server(name,inviteCode, userId));
    }
}
