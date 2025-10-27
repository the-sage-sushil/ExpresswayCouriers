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

export interface ServiceableResponse {
  status: string;
  data: {
    serviceable: boolean;
    destinationBranchCity: string;
    state: string;
    err: string;
    isMBGApplicable: boolean;
  };
}

export interface TatRequest {
  pickupPincode: string;
  deliveryPincode: string;
  weight: string;
  courierType: string;
  isQRBooking: boolean;
  length: string;
  breadth: string;
  height: string;
  commodityId: string;
  commodityName: string;
  declaredPrice: string;
  commodityCode: string;
}
export interface TatResponse {
  explain: ServiceExplain;
}

export interface ServiceData {
  serviceType: string;
  period: string;
  additionalPrice: number;
  price: string;
  priceWithRiskSurcharge: string;
  priceWithCarrierRiskSurcharge: string;
  priceWithOwnerRiskSurcharge: string;
  priceWithOptionalInsurance: string;
  serviceCode: string;
  volumetricWeight: number;
  volumetricWeights: number;
  gstRate: number;
  mbgApplicable: boolean;
  mbgMessage: string;
  mbgVasCharge: number;
  basePriceForDiscount: number;
  GST: string;
  GSTWithRiskSurcharge: string;
  GSTWithCarrierRiskSurcharge: string;
  GSTWithOwnerRiskSurcharge: string;
  chargeableWeight: number;
  edd: string; // can use Date if always ISO date format
  order: number;
}

export interface ServiceExplain {
  message: string;
  destinationType: DestinationType;
  priceData: PriceData;
  source: string;
}

export interface DestinationType {
  _id: string;
  code: string;
  id: string;
  name: string;
}

export interface PriceData {
  ebrCCRate: number;
  basePrice: number;
  basePriceEFR: number;
  internationalCES: number;
  ondcPrice: number;
  finalPrice: number;
}


