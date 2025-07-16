// Client request for creating/updating a client
export interface ClientRequest {
  name: string;
  email: string;
  // Add other fields as needed
}

// Client entity
export interface Client {
  id: number;
  name: string;
  email: string;
  // Add other fields as needed
}
