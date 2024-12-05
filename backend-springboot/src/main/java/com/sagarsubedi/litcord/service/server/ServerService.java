package com.sagarsubedi.litcord.service.server;

import com.sagarsubedi.litcord.Exceptions.ServerCreationConflictException;
import com.sagarsubedi.litcord.Exceptions.ServerNotFoundException;
import com.sagarsubedi.litcord.dao.ServerRepository;
import com.sagarsubedi.litcord.dto.ServerDTO;
import com.sagarsubedi.litcord.model.Server;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServerService {
    @Autowired
    ServerRepository serverRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Server createServer(String name, Long userId) {
        Optional<Server> server = serverRepository.findServerByNameAndUserId(name, userId);
        if(server.isPresent()){
            throw new ServerCreationConflictException();
        }
        String inviteCode = UUID.randomUUID().toString();
        return serverRepository.save(new Server(name,inviteCode, userId));
    }

    public ServerDTO getServerById(Long id) {
        return serverRepository.findById(id)
                .map(server -> modelMapper.map(server, ServerDTO.class))
                .orElse(null);
    }

    public void updateServer(Long id, ServerDTO serverDTO) {
        Server server = serverRepository.findById(id)
                .orElseThrow();
        server.setName(serverDTO.getName());
        server.setInviteCode(serverDTO.getInviteCode());
        server.setUserId(serverDTO.getUserId());
        serverRepository.save(server);
    }

    public void deleteServer(Long id) {
        if (!serverRepository.existsById(id)) {
            throw new ServerNotFoundException("Server is not found");
        }
        serverRepository.deleteById(id);
    }

    public List<ServerDTO> getAllServers() {
        return serverRepository.findAll().stream()
                .map(server -> modelMapper.map(server, ServerDTO.class))
                .collect(Collectors.toList());
    }
}
