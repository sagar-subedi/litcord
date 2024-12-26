package com.sagarsubedi.litcord.service.channel;

import com.sagarsubedi.litcord.Exceptions.ChannelCreationConflictException;
import com.sagarsubedi.litcord.dao.ChannelRepository;
import com.sagarsubedi.litcord.dao.ServerRepository;
import com.sagarsubedi.litcord.enums.ChannelType;
import com.sagarsubedi.litcord.model.Channel;
import com.sagarsubedi.litcord.model.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ServerRepository serverRepository;

    public Channel createChannel(String name, Long profileId, Long serverId, ChannelType type) throws Exception {
        Optional<Channel> channel = channelRepository.findByNameAndServerId(name, serverId);
        Optional<Server> server = serverRepository.findById(serverId);
        if(channel.isPresent() && !server.isPresent()){
            throw new ChannelCreationConflictException();
        }
        return channelRepository.save(new Channel(name, profileId, server.get(), type));

    }

    public Optional<Channel> getChannelById(Long id) {
        return channelRepository.findById(id);
    }

    public Optional<Channel> updateChannel(Long id, Channel updatedChannel) {
        return channelRepository.findById(id).map(channel -> {
            channel.setName(updatedChannel.getName());
            channel.setAdminId(updatedChannel.getAdminId());
            return channelRepository.save(channel);
        });
    }

    public boolean deleteChannel(Long id) {
        if (channelRepository.existsById(id)) {
            channelRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    //Lists all the channels belonging to a server
    public List<Channel> getChannelsByServerId(Long serverId) {
        return channelRepository.findAllByServerId(serverId);
    }

}
