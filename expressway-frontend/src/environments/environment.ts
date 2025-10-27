export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8088/api/v1/',
  apiUrl: 'http://localhost:8088/api/v1',
  getServicableUrl: 'https://ebookingbackend.dtdc.in/serviceableDelivery/',
};

export const serviceTypes = [
  { value: 'air', label: 'Standard Air' },
  { value: 'premium', label: 'Premium Air' },
  { value: 'surface', label: 'Surface' },
  // { value: 'eExpress', label: 'Ecom-Express' },
  // { value: 'eSurface', label: 'Ecom-Surface' },
];

export const locationTypes = [
  { value: 'PanIndia', label: 'REST OF INDIA' },
  { value: 'PanIndia', label: 'METROS' },
  { value: 'Special', label: 'SPECIAL DESTINATONS' },
  { value: 'Nearby', label: 'WITH IN ZONE' },
  { value: 'Nearby', label: 'WITH IN STATE' },
  { value: 'Local', label: 'WITH IN CITY' },
];
