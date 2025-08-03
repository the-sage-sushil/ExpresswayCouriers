// Consignment interface for consignment entity
export interface Consignment {
  id: number;
  bookingDate: string;
  bookedBy: number;
  trackingNumber: string;
  chennalPatner: string;
  serviceType: string;
  senderName: string;
  senderContact: string;
  senderAddress: string;
  destPincode: number;
  receiverName: string;
  receiverAddress: string;
  receiverContact: string;
  weight: number;
  dimensions: string;
  numberOfPackages: number,
  paymentMode : string,
  totalAmount: number,
  expectedDeliveryDate : Date
  actualDeliveryDate : Date
  status : string
}
