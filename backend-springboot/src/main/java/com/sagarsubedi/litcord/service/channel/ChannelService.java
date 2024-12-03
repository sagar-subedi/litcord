package com.sagarsubedi.litcord.service.channel;

import com.sagarsubedi.litcord.Exceptions.ChannelCreationConflictException;
import com.sagarsubedi.litcord.dao.ChannelRepository;
import com.sagarsubedi.litcord.model.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    public Channel createChannel(String name, Long profileId, Long serverId) throws Exception {
        Optional<Channel> channel = channelRepository.findByNameAndServerId(name, serverId);
        if(channel.isPresent()){
            throw new ChannelCreationConflictException();
        }
        return channelRepository.save(new Channel(name, profileId, serverId));

    }

    public Optional<Channel> getChannelById(Long id) {
        return channelRepository.findById(id);
    }

    public Optional<Channel> updateChannel(Long id, Channel updatedChannel) {
        return channelRepository.findById(id).map(channel -> {
            channel.setName(updatedChannel.getName());
            channel.setProfileId(updatedChannel.getProfileId());
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

}
