  import { Injectable } from '@angular/core';
  import { Client } from '@stomp/stompjs';
  // import * as SockJS from 'sockjs-client';

  @Injectable({
    providedIn: 'root',
  })
  export class SignalingService {
    private stompClient: Client;

    constructor() {
      this.stompClient = new Client({
        brokerURL: 'wss://65.0.71.191:8081/ws',  // Backend WebSocket URL
        connectHeaders: {},
        debug: (str) => {
          console.log(str);   
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
      });

    }

    activateStomp(){
      this.stompClient.activate();
    }

    sendOffer(offer: any) {
      this.stompClient.publish({ destination: '/app/offer', body: offer });
    }

    sendAnswer(answer: any) {
      this.stompClient.publish({ destination: '/app/answer', body: answer });
    }

    sendCandidate(candidate: any) {
      this.stompClient.publish({ destination: '/app/candidate', body: candidate });
    }

    subscribeOffer(callback: any) {
      this.stompClient.subscribe('/topic/offer', (message) => {
        callback(message.body);
      });
    }

    subscribeAnswer(callback: any) {
      this.stompClient.subscribe('/topic/answer', (message) => {
        callback(message.body);
      });
    }

    subscribeCandidate(callback: any) {
      this.stompClient.subscribe('/topic/candidate', (message) => {
        callback(message.body);
      });
    }

    subscribeChannelMemberRequests(callback: any) {
      this.stompClient.subscribe('/topic/members/req', (message) => {
        callback(message.body);
      });
    }

    subscribeChannelMemberResponses(callback: any) {
      this.stompClient.subscribe('/topic/members/res', (message) => {
        callback(message.body);
      });
    }



    subscribeMessage(callback: any) {
      console.log("Subscribed to /topic/messages")
      this.stompClient.subscribe('/topic/messages', (message) => {
      callback(message);
      });
    }

    publishMessage(message: string) {
      this.stompClient.publish({ destination: '/app/chat', body: message });
    }

    publishMemberRequest(message: string) {
      this.stompClient.publish({ destination: '/app/members/req', body: message });
    }

    publishMemberResponse(message: string) {
      this.stompClient.publish({ destination: '/app/members/res', body: message });
    }
  }
