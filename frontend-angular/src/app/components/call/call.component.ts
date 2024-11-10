import { Component, OnInit, ViewChild, ElementRef, Input, OnChanges, SimpleChanges, OnDestroy } from '@angular/core';
import { SignalingService } from '../../../services/signaling-service.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-video-call',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './call.component.html',
  styleUrls: ['./call.component.scss']
})
export class CallComponent implements OnInit, OnChanges, OnDestroy {
  @ViewChild('localVideo') localVideo!: ElementRef;
  @ViewChild('remoteVideo') remoteVideo!: ElementRef;
  @ViewChild('videoContainer', { static: true }) videoContainer!: ElementRef;

  private localStream!: MediaStream;
  messages : string[] = [];
  userId: string = crypto.randomUUID();
  channelId!: string;
  @Input() channel:any;

  private peerConnectionMap: any = {};

  constructor(private signalingService: SignalingService, private route: ActivatedRoute) { }

  async ngOnInit() {
    this.channelId = this.route.snapshot.paramMap.get('channelid') ?? '';

    //get video/audio stream and sets the stream to be redered in 'localVideo' element
    this.localStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: false });
    this.localVideo.nativeElement.srcObject = this.localStream;

    this.signalingService.activateStomp();
    //ensure conection is setup before any subscription
    setTimeout(()=>{
      this.signalingService.subscribeOffer((offer: any) => this.handleOffer(offer));
      this.signalingService.subscribeAnswer((answer: any) => this.handleAnswer(answer));
      this.signalingService.subscribeCandidate((candidate: any) => this.handleCandidate(candidate));
      this.signalingService.subscribeChannelMemberRequests((req:any)=> this.handleMemberRequests(req));
      this.signalingService.subscribeChannelMemberResponses((res:any)=> this.handleMemberResponse(res));
      this.sendMemberRequest();
    }, 1000);
  }

  ngOnDestroy() {
    // Clean up connections when component is destroyed
    this.peerConnectionMap.forEach((connection:any) => connection.close());
    this.peerConnectionMap.clear();
  }

  ngOnChanges(changes: SimpleChanges): void {
      console.log(changes);
  }

  sendMemberRequest(){
    this.signalingService.publishMemberRequest(JSON.stringify({requester: this.userId, channelId: this.channelId}))
  }

  async handleMemberRequests(req: any) {
    const reqObj = JSON.parse(req);
    if(reqObj.channelId==this.channelId && reqObj.requester!=this.userId){
      this.signalingService.publishMemberResponse(JSON.stringify({to:reqObj.requester, roomId: reqObj.roomId, idRequested:this.userId}))
    }
  }

  async handleMemberResponse(res: any) {
    const resObj = JSON.parse(res);
    if(resObj.to==this.userId && this.channelId==resObj.channelId){
      const newPeerConnection = this.createRTCPeerConnection(resObj.idRequested);
      this.peerConnectionMap[resObj.idRequested] = newPeerConnection;

      //create and send offer
      const offer = await newPeerConnection.createOffer();
      await newPeerConnection.setLocalDescription(offer);
      this.signalingService.sendOffer(JSON.stringify({offer: offer, to:resObj.idRequested, userId:this.userId, channelId:this.channelId}));
    }
  }

  onicecandidateFunction = (event : any) => {
    if (event.candidate) {
      this.signalingService.sendCandidate(JSON.stringify({candidate: event.candidate, userId: this.userId, channelId: this.channelId}));
    }
  };

  ontrackFunction = (event:any) => {
    const newVideoElement = document.createElement('video');
    newVideoElement.srcObject = event.streams[0];
    newVideoElement.autoplay = true;
    newVideoElement.playsInline = true;

    // Append the new video element to the container
    this.videoContainer.nativeElement.appendChild(newVideoElement);
  };


  createRTCPeerConnection(receiverId: string){
    let peerConnection = new RTCPeerConnection({
      iceServers: [{ urls: 'stun:stun.l.google.com:19302' }]  // STUN server
    });

    //gets track from localStream and for add each track to the peer connection
    this.localStream.getTracks().forEach((track) => {
      peerConnection.addTrack(track, this.localStream);
    });

    peerConnection.ontrack = this.ontrackFunction;

    peerConnection.onicecandidate = (event : any) => {
      if (event.candidate) {
        this.signalingService.sendCandidate(JSON.stringify({candidate: event.candidate, to:receiverId, userId: this.userId, channelId: this.channelId}));
      }
    };

    return peerConnection;
  }

  async handleOffer(message: any) {
    //handle the offer only if you it's not sent by you
    //alternative way is to somehow not have this message be broadcasted to the client that sent it.
    const messageObj = JSON.parse(message);
    const offer = messageObj.offer;
    if(this.userId==messageObj.to && messageObj.channelId==this.channelId){
      const newRtCPeerConnection = this.createRTCPeerConnection(messageObj.userId);
      await newRtCPeerConnection.setRemoteDescription(new RTCSessionDescription(offer));
      const answer = await newRtCPeerConnection.createAnswer();
      await newRtCPeerConnection.setLocalDescription(answer);
      this.peerConnectionMap[messageObj.userId] = newRtCPeerConnection;

      //add new RTCPeerConnection to the RTC map
      this.signalingService.sendAnswer(JSON.stringify({answer:answer, answerBy: this.userId, channelId:this.channelId, userId:messageObj.userId}));
    }
    
  }

  async handleAnswer(message: any) {
    const answerObj = JSON.parse(message);
    const answer = answerObj.answer;
    if(answerObj.userId==this.userId && answerObj.channelId==this.channelId){
      //create new RTCPeerConnection here and add it to the map
      await this.peerConnectionMap[answerObj.answerBy].setRemoteDescription(new RTCSessionDescription(answer));
    }
  }



  handleCandidate(message: any) {
    const messageObj = JSON.parse(message);
    const candidate = messageObj.candidate;
    if(messageObj.to == this.userId && messageObj.channelId == this.channelId){
      this.peerConnectionMap[messageObj.userId].addIceCandidate(new RTCIceCandidate(candidate));
    }
  }

}

