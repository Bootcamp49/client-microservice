package com.nttdata.bootcamp.clientmanagement.service;

import com.nttdata.bootcamp.clientmanagement.model.Client;
import com.nttdata.bootcamp.clientmanagement.repository.ClientRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación de la interfaz de clientes.
 */
@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final ClientRepository clientRepository;

    @Override
    public Mono<Client> updateClient(@NonNull String id, Client client) {
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
    public Mono<Client> findById(@NonNull String id) {
        return clientRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteClient(@NonNull String id) {
        return clientRepository.deleteById(id);
    }

    @Override
    public Mono<Client> createClient(@NonNull Client client) {
        return clientRepository.save(client);
    }
    
}
