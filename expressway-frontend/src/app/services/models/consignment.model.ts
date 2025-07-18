// Consignment interface for consignment entity
export interface Consignment {
  id: number;
  awbNumber: string;
  chennalPatner: string;
  serviceType: string;
  senderName: string;
  senderContact: string;
  senderAddress: string;
  receiverName: string;
  receiverAddress: string;
  bookingDate: string;
  weight: number;
  dimensions: string;
  price: number;
  status: string;
  bookedBy: number;
}


export interface ConsignmentResponse {
  consignments?: Consignment[];
}
