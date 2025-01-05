export interface Message {
    id?: number;
    content: string;
    senderId: string;
    senderEmail: string;
    channelId: number;
    sentAt?: Date;
  }
  