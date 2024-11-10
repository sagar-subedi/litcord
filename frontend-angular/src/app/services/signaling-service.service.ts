import { Injectable } from '@angular/core';
import { Client, StompSubscription } from '@stomp/stompjs';
// import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root',
})
export class SignalingService {
  private stompClient: Client;
  private subscriptions: Map<string, StompSubscription> = new Map();

  constructor() {
    this.stompClient = new Client({
      brokerURL: 'wss://65.0.71.191:8081/ws', // Backend WebSocket URL
      connectHeaders: {},
      debug: (str) => {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });
    this.stompClient.activate();
  }

  // Subscribe to a topic and store the subscription
  subscribe(topic: string, callback: (message: any) => void): void {
    const subscription = this.stompClient.subscribe(
      '/topic/' + topic,
      (message) => {
        callback(message.body);
      }
    );

    // Store the subscription for future reference
    this.subscriptions.set(topic, subscription);
  }

  // Unsubscribe from a specific topic
  unsubscribe(topic: string): void {
    const subscription = this.subscriptions.get(topic);
    if (subscription) {
      subscription.unsubscribe(); // Call the unsubscribe method
      this.subscriptions.delete(topic); // Remove it from the map
      console.log(`Unsubscribed from ${topic}`);
    } else {
      console.log(`No subscription found for topic: ${topic}`);
    }
  }

  // Clear all subscriptions
  clearAllSubscriptions(): void {
    this.subscriptions.forEach((subscription, topic) => {
      subscription.unsubscribe(); // Unsubscribe each
      console.log(`Unsubscribed from ${topic}`);
    });
    this.subscriptions.clear(); // Clear the map
    console.log('All subscriptions cleared');
  }

  publish(destination: string, body: string) {
    this.stompClient.publish({
      destination: '/app/' + destination,
      body,
    });
  }
}
