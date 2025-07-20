package com.sushil.expressway.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sushil.expressway.common.Mapper;
import com.sushil.expressway.entitys.Client;
import com.sushil.expressway.models.ClientRequest;
import com.sushil.expressway.repositories.ClientRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientService {

    private final Mapper mapper;
    private final ClientRepository clientRepository;

    public Long save(ClientRequest request) {
        Client client = mapper.toClient(request);
        return clientRepository.save(client).getId();
    }
    
    public Client getClientById(Long id) {
        return clientRepository.findById(id.longValue())
        .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }
    
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    
    public ResponseEntity<String> deleteClient(Long id) {
        clientRepository.deleteById(id);
        return ResponseEntity.ok("Client deleted successfully");
    }
    
    public Long updateClient(Long id, ClientRequest request) {

        Client existingClient = clientRepository.findById(id.longValue()) .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        existingClient = mapper.toClient(request);
        existingClient.setId(id);
        return clientRepository.save(existingClient).getId();
    }
}
