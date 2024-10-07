package com.sagarsubedi.litcord.controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SignalingController {

    @MessageMapping("/offer")
    @SendTo("/topic/offer")
    public String sendOffer(String offer) {
        System.out.println("Offer " + offer);
        return offer;  // Send SDP offer to the other peer
    }

    @MessageMapping("/answer")
    @SendTo("/topic/answer")
    public String sendAnswer(String answer) {
        return answer;  // Send SDP answer to the other peer
    }

    @MessageMapping("/candidate")
    @SendTo("/topic/candidate")
    public String sendCandidate(String candidate) {
        return candidate;  // Send ICE candidates
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String sendTest(String message) {
        System.out.println("this is a test: " + message); //.getUser() + " " + message.getMessage());
        return message; //.getMessage();  // Send ICE candidates
    }
}