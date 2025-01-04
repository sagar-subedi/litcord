package com.sagarsubedi.litcord.service;

import com.sagarsubedi.litcord.dao.MessageRepository;
import com.sagarsubedi.litcord.dto.MessageDTO;
import com.sagarsubedi.litcord.model.Message;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Add a new message
    public Message saveMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setSenderId(messageDTO.getSenderId());
        message.setChannelId(messageDTO.getChannelId());
        message.setSenderEmail(messageDTO.getSenderEmail());
        message.setSentAt(LocalDateTime.now());

        return messageRepository.save(message);
    }

    public List<Message> getMessagesByChannelId(Long channelId, int page, int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by("sentAt").descending());
        Page<Message> messages = messageRepository.findByChannelId(channelId, pageable);

        return messages.getContent();
    }

//    private MessageDTO mapToDTO(Message message) {
//        return new MessageDTO(
//                message.getId(),
//                message.getChannelId(),
//                message.getSenderId(),
//                message.getSenderEmail(),
//                message.getContent(),
//                message.getSentAt()
//        );
//    }

    // Update an existing message
    @Transactional
    public Message updateMessage(Long messageId, String newMessage) {
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (existingMessage.isPresent()) {
            Message message = existingMessage.get();
            message.setContent(newMessage);  // Update the message content
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



