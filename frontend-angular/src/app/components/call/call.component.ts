import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  Input,
  OnChanges,
  SimpleChanges,
  OnDestroy,
  ViewChildren,
  QueryList,
  ChangeDetectorRef,
  EventEmitter,
  Output,
} from '@angular/core';
import { SignalingService } from '../../services/signaling-service.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { ChatComponent } from "../chat/chat.component";
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-video-call',
  standalone: true,
  imports: [FormsModule, CommonModule, ChatComponent, MatIconModule, ChatComponent],
  templateUrl: './call.component.html',
  styleUrls: ['./call.component.scss'],
})
export class CallComponent implements OnInit, OnChanges, OnDestroy {
  @ViewChild('localVideo') localVideo!: ElementRef;
  @ViewChildren('videoElement') videoElements!: QueryList<
    ElementRef<HTMLVideoElement>
  >;

  private localStream!: MediaStream;
  messages: string[] = [];
  userId!: string;
  channelId!: string;
  @Input() channel: any;
  userIds: string[] = []; // List of peer userIds to render


  isMuted: boolean = false;
  isCameraOn: boolean = true;
  isChatOpen: boolean = false;

  @Output() callEnd = new EventEmitter<void>();


  private peerConnectionMap: { [userId: string]: RTCPeerConnection } = {};

  constructor(
    private signalingService: SignalingService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef,
    private authService: AuthService
  ) {}

  async ngOnInit() {
    this.channelId = this.route.snapshot.paramMap.get('channelid') ?? '';
    this.userId = this.authService.getCurrentUserEmail() ?? 'unknown';

    //get video/audio stream and sets the stream to be redered in 'localVideo' element
    this.localStream = await navigator.mediaDevices.getUserMedia({
      video: true,
      audio: true,
    });
    this.localVideo.nativeElement.srcObject = this.localStream;
    this.localVideo.nativeElement.muted = true;

    //ensure conection is setup before any subscription, doing so by delaying subscription
    setTimeout(() => {
      this.signalingService.subscribe('offer', (offer: any) =>
        this.handleOffer(offer)
      );
      this.signalingService.subscribe('answer', (answer: any) =>
        this.handleAnswer(answer)
      );
      this.signalingService.subscribe('candidate', (candidate: any) =>
        this.handleCandidate(candidate)
      );
      this.signalingService.subscribe('members/req', (req: any) =>
        this.handleMemberRequests(req)
      );
      this.signalingService.subscribe('members/res', (res: any) =>
        this.handleMemberResponse(res)
      );
      this.sendMemberRequest();
    }, 1000);
  }

  ngOnDestroy() {
    console.log('Ng destory oncalled');
    // Clean up connections when component is destroyed
    // Iterate and close each peer connection
    Object.entries(this.peerConnectionMap).forEach(
      ([userId, peerConnection]: [string, RTCPeerConnection]) => {
        console.log(`Closing connection for user: ${userId}`);

        // Close the peer connection
        peerConnection.close();

        // Optionally, remove it from the object if necessary
        delete this.peerConnectionMap[userId];
      }
    );
    this.signalingService.clearAllSubscriptions();
  }

  ngOnChanges(changes: SimpleChanges): void {
    //this method is just in case if needed
  }

  sendMemberRequest() {
    this.signalingService.publish(
      'members/req',
      JSON.stringify({ requester: this.userId, channelId: this.channelId })
    );
  }

  async handleMemberRequests(req: any) {
    const reqObj = JSON.parse(req);
    if (reqObj.channelId == this.channelId && reqObj.requester != this.userId) {
      this.signalingService.publish(
        'members/res',
        JSON.stringify({
          to: reqObj.requester,
          roomId: reqObj.roomId,
          idRequested: this.userId,
        })
      );
    }
  }

  async handleMemberResponse(res: any) {
    const resObj = JSON.parse(res);
    if (resObj.to == this.userId && this.channelId == this.channelId) {
      const newPeerConnection = this.createRTCPeerConnection(
        resObj.idRequested
      );
      this.peerConnectionMap[resObj.idRequested] = newPeerConnection;
      this.userIds = Object.keys(this.peerConnectionMap); // Update userIds list

      //create and send offer
      const offer = await newPeerConnection.createOffer();
      await newPeerConnection.setLocalDescription(offer);
      this.signalingService.publish(
        'offer',
        JSON.stringify({
          offer: offer,
          to: resObj.idRequested,
          userId: this.userId,
          channelId: this.channelId,
        })
      );
    }
  }

  onicecandidateFunction = (event: any) => {
    if (event.candidate) {
      this.signalingService.publish(
        'candidate',
        JSON.stringify({
          candidate: event.candidate,
          userId: this.userId,
          channelId: this.channelId,
        })
      );
    }
  };

  createRTCPeerConnection(receiverId: string) {
    console.log('hello from create rtc connection');
    let peerConnection = new RTCPeerConnection({
      iceServers: [{ urls: 'stun:stun.l.google.com:19302' }], // STUN server
    });

    //gets track from localStream and for add each track to the peer connection
    this.localStream.getTracks().forEach((track) => {
      peerConnection.addTrack(track, this.localStream);
    });

    peerConnection.onicecandidate = (event: any) => {
      if (event.candidate) {
        this.signalingService.publish(
          'candidate',
          JSON.stringify({
            candidate: event.candidate,
            to: receiverId,
            userId: this.userId,
            channelId: this.channelId,
          })
        );
      }
    };

    // Event listener: Monitor connection state changes
    peerConnection.onconnectionstatechange = () => {
      const state = peerConnection.connectionState;
      console.log(`Connection state for ${receiverId}: ${state}`);
      if (
        state === 'disconnected' ||
        state === 'failed' ||
        state === 'closed'
      ) {
        console.log(
          `Removing peer connection for user ${receiverId} due to state: ${state}`
        );
        this.removePeerConnection(receiverId);
      }
    };

    // Event listener: Monitor ICE connection state changes
    peerConnection.oniceconnectionstatechange = () => {
      const iceState = peerConnection.iceConnectionState;
      console.log(`ICE connection state for ${receiverId}: ${iceState}`);
      if (
        iceState === 'disconnected' ||
        iceState === 'failed' ||
        iceState === 'closed'
      ) {
        console.log(
          `Removing peer connection for user ${receiverId} due to ICE state: ${iceState}`
        );
        this.removePeerConnection(receiverId);
      }
    };

    // Event listener: Monitor track removal or stream stop
    peerConnection.ontrack = (event) => {
      const track = event.track;
      track.onended = () => {
        console.log(`Track ended for user ${receiverId}`);
        this.removePeerConnection(receiverId);
      };

      // Attach the incoming stream to a video element
      setTimeout(() => {
        const videoElement = this.getVideoElement(receiverId);
        if (videoElement) {
          videoElement.srcObject = event.streams[0];
          videoElement.muted = false;
        }
      }, 1000);
    };

    return peerConnection;
  }

  private getVideoElement(userId: string): HTMLVideoElement | undefined {
    // Find the video element for the specified userId
    const index = this.userIds.indexOf(userId);
    const videoElementRef = this.videoElements.get(index);
    return videoElementRef?.nativeElement;
  }

  async handleOffer(message: any) {
    //handle the offer only if you it's not sent by you
    //alternative way is to somehow not have this message be broadcasted to the client that sent it.
    const messageObj = JSON.parse(message);
    const offer = messageObj.offer;
    if (
      this.userId == messageObj.to &&
      messageObj.channelId == this.channelId
    ) {
      const newRtCPeerConnection = this.createRTCPeerConnection(
        messageObj.userId
      );
      await newRtCPeerConnection.setRemoteDescription(
        new RTCSessionDescription(offer)
      );
      const answer = await newRtCPeerConnection.createAnswer();
      await newRtCPeerConnection.setLocalDescription(answer);
      this.peerConnectionMap[messageObj.userId] = newRtCPeerConnection;
      this.userIds = Object.keys(this.peerConnectionMap); // Update userIds list

      //add new RTCPeerConnection to the RTC map
      this.signalingService.publish(
        'answer',
        JSON.stringify({
          answer: answer,
          answerBy: this.userId,
          channelId: this.channelId,
          userId: messageObj.userId,
        })
      );
    }
  }

  async handleAnswer(message: any) {
    const answerObj = JSON.parse(message);
    const answer = answerObj.answer;
    if (
      answerObj.userId == this.userId &&
      answerObj.channelId == this.channelId
    ) {
      //create new RTCPeerConnection here and add it to the map
      await this.peerConnectionMap[answerObj.answerBy].setRemoteDescription(
        new RTCSessionDescription(answer)
      );
    }
  }

  handleCandidate(message: any) {
    const messageObj = JSON.parse(message);
    const candidate = messageObj.candidate;
    if (
      messageObj.to == this.userId &&
      messageObj.channelId == this.channelId
    ) {
      this.peerConnectionMap[messageObj.userId].addIceCandidate(
        new RTCIceCandidate(candidate)
      );
    }
  }

  removePeerConnection(userId: string) {
    console.log(this.peerConnectionMap);
    const peerConnection = this.peerConnectionMap[userId];
    if (peerConnection) {
      peerConnection.close(); // Close the connection
      delete this.peerConnectionMap[userId]; // Remove from map
      console.log(`Peer connection removed for user ${userId}`);
    }
    this.userIds = Object.keys(this.peerConnectionMap); // Update userIds list
    this.cdr.detectChanges();
  }

  // End Call
  endCall() {
    console.log('Ending call...');
    this.localStream?.getTracks().forEach((track) => track.stop());
    this.callEnd.emit();
    // Add your logic to terminate the WebRTC call
  }

  // Toggle Mute
  toggleMute() {
    if (this.localStream) {
      this.isMuted = !this.isMuted;
      this.localStream.getAudioTracks().forEach(track => {
        track.enabled = !this.isMuted;
      });
    }
  }

  // Toggle Camera
  toggleCamera() {
    if (this.localStream) {
      this.isCameraOn = !this.isCameraOn;
      this.localStream.getVideoTracks().forEach(track => {
        track.enabled = this.isCameraOn;
      });
    }
  }

  // Open/Close Chat
  toggleChat() {
    this.isChatOpen = !this.isChatOpen;
    console.log(this.isChatOpen ? 'Chat Opened' : 'Chat Closed');
    // Add logic to display chat UI
  }

  chatMessages: string[] = [
    'Hello, welcome to the call!',
    'Let me know if you need anything.',
  ];

  // Add new messages here
  addMessage(newMessage: string) {
    this.chatMessages.push(newMessage);
  }


}
