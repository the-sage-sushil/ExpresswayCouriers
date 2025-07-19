// Client request for creating/updating a client
export interface ClientRequest {
  name: string;
  contactNumber: string;
  email: string;
  standardPricePerKg: number;
  premiumPricePerKg: number;
  surfacePricePerKg: number;
  address: string;
}

export interface Client {
  id: number;
  name: string;
  contactNumber: string;
  email: string;
  standardPricePerKg: number;
  premiumPricePerKg: number;
  surfacePricePerKg: number;
  address: string;
}
