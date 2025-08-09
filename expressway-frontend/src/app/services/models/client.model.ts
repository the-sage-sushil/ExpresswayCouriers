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
  id?: number; // Optional if not always present

  name: string;
  email: string;
  contactPerson?: string;
  contactNumber?: string;
  address?: string;

  airLocal250?: number;
  airLocal500?: number;
  airLocalAdd500?: number;

  airNearby250?: number;
  airNearby500?: number;
  airNearbyAdd500?: number;

  airPanIndia250?: number;
  airPanIndia500?: number;
  airPanIndiaAdd500?: number;

  airSpecial250?: number;
  airSpecial500?: number;
  airSpecialAdd500?: number;
  // airEcommarce?: number;

  surfaceLocal500?: number;
  surfaceNearby500?: number;
  surfacePanIndia500?: number;
  surfaceSpecial500?: number;
  // surfaceEcommarce?: number;

  premiumLocal500?: number;
  premiumLocalAdd500?: number;
  premiumNearby500?: number;
  premiumNearbyAdd500?: number;
  premiumPanIndia500?: number;
  premiumPanIndiaAdd500?: number;
  premiumSpecial500?: number;
  premiumSpecialAdd500?: number;
}

export interface PricingItem {
  label: string;
  controlName: string;
}

export interface PricingCategory {
  label: string;
  items: PricingItem[];
}