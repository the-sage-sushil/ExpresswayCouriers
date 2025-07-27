import { Client } from "./client.model";

// ConsignmentRequest interface for consignment creation
export interface ConsignmentRequest {
  trackingNumber: string;
  channelPartner: string;
  serviceType: string;
  senderName: string;
  senderContact: string;
  senderAddress: string;
  receiverName: string;
  receiverAddress: string;
  weight: number;
  dimensions: string;
  client?: Client;
  totalAmount: number;
  status: string;
}
