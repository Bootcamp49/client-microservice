package com.nttdata.bootcamp.clientmanagement.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

// import com.nttdata.bootcamp.api.ClientApi;
import com.nttdata.bootcamp.clientmanagement.api.ClientApiDelegate;
import com.nttdata.bootcamp.clientmanagement.model.Client;
// import com.nttdata.bootcamp.clientmanagement.model.Client;
import com.nttdata.bootcamp.clientmanagement.repository.ClientRepository;
// import com.nttdata.bootcamp.model.Client;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ClientDelegateImpl implements ClientApiDelegate{
    
    @Autowired
    private final ClientRepository clientRepository;
    
    @Override
    public Mono<ResponseEntity<Client>> addClient(Mono<Client> client, ServerWebExchange exchange) {
        System.out.println("Dentro del add client");
    
        //Pendiente de ver como realizar la implementación, de lo contrario manejarlo sin el contrato primero

        Client clientToReturnService = new Client(); 
        client.map(clientMapped -> {
            return clientMapped;
        }).subscribe(c -> {
            clientRepository.save(c).map(clientReturnnedMapped -> {
                return clientReturnnedMapped;
            }).subscribe(savedClient -> {
                clientToReturnService.setId(savedClient.getId());
                clientToReturnService.setClientType(savedClient.getClientType());
                clientToReturnService.setDocumentNumber(savedClient.getDocumentNumber());
                clientToReturnService.setLastName(savedClient.getLastName());
                clientToReturnService.setName(savedClient.getName());
                clientToReturnService.setPassword(savedClient.getPassword());
            });
        });
        return Mono.just(ResponseEntity.ok().body(clientToReturnService));
    }

    @Override
    public Mono<ResponseEntity<Flux<Client>>> findAllClients(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok().body(clientRepository.findAll().cast(Client.class)));
        
    }
    
    @Override
    public Mono<ResponseEntity<Void>> deleteClientById(String id, ServerWebExchange exchange) {
        return clientRepository.deleteById(id).map(r -> ResponseEntity.ok().body(r));
    } 
    
    @Override
    public Mono<ResponseEntity<Client>> findClientById(String id, ServerWebExchange exchange) {
        return clientRepository.findById(id).map(r -> ResponseEntity.ok().body(r));
    }

    @Override
    public Mono<ResponseEntity<Client>> updateClientById(String id, Mono<Client> client, ServerWebExchange exchange) {
        Client clientToUpdate = client.block();
        return clientRepository.findById(id).
        flatMap(c -> {
            c.setDocumentNumber(clientToUpdate.getDocumentNumber());
            c.setName(clientToUpdate.getName());
            c.setLastName(clientToUpdate.getLastName());
            c.setPassword(clientToUpdate.getPassword());
            c.clientType(clientToUpdate.getClientType());
            return clientRepository.save(c).map(r -> ResponseEntity.ok().body(r));
        });
    }
    
}
