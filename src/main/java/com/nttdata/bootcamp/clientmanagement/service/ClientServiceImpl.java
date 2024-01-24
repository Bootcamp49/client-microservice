package com.nttdata.bootcamp.clientmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.clientmanagement.model.Client;
import com.nttdata.bootcamp.clientmanagement.repository.ClientRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final ClientRepository clientRepository;

    @Override
    public Mono<Client> updateClient(String id, Client client) {
        return clientRepository.findById(id)
        .flatMap(existingClient -> {
            existingClient.setName(client.getName());
            existingClient.setLastName(client.getLastName());
            existingClient.setClientType(client.getClientType());
            existingClient.setDocumentNumber(client.getDocumentNumber());
            existingClient.setPassword(client.getPassword());
            return clientRepository.save(existingClient);
        });
    }

    @Override
    public Flux<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Mono<Client> findById(String id) {
        return clientRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteClient(String id) {
        return clientRepository.deleteById(id);
    }

    @Override
    public Mono<Client> createClient(Client client) {
        return clientRepository.save(client);
    }
    
}
