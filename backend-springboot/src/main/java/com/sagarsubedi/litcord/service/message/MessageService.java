package com.sagarsubedi.litcord.service.message;

import com.sagarsubedi.litcord.dao.MessageRepository;
import com.sagarsubedi.litcord.model.Message;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Add a new message
    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    // Update an existing message
    @Transactional
    public Message updateMessage(Long messageId, String newMessage) {
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (existingMessage.isPresent()) {
            Message message = existingMessage.get();
            message.setMessage(newMessage);  // Update the message content
            return messageRepository.save(message);
        }
        return null; // Or throw an exception depending on the requirement
    }

    // Delete a message
    public void deleteMessage(Long messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
        }
    }

    // Optionally, you can add a method to get a message by ID
    public Optional<Message> getMessageById(Long messageId) {
        return messageRepository.findById(messageId);
    }
    // Get last 'n' messages for a given channelId
//    public List<Message> getLastNMessages(Long channelId, int n) {
//        Pageable pageable = (Pageable) PageRequest.of(0, n); // Pageable for first 'n' messages
////        return messageRepository.findLastNMessagesByChannelId(channelId, pageable);
//    }
}



