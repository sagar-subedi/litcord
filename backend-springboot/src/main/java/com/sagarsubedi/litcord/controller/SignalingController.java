package com.sagarsubedi.litcord.controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SignalingController {

    @MessageMapping("/offer")
    @SendTo("/topic/offer")
    public String sendOffer(String offer) {
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
        return message; //.getMessage();  // Send ICE candidates
    }

    @MessageMapping("/members/req")
    @SendTo("/topic/members/req")
    public String sendMembersReq(String message) {
        return message; //  // Send ICE candidates
    }

    @MessageMapping("/members/res")
    @SendTo("/topic/members/res")
    public String sendMembersRes(String message) {
        return message;  // Send ICE candidates
    }
}