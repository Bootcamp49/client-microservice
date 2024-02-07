package com.nttdata.bootcamp.clientmanagement.repository;

import com.nttdata.bootcamp.clientmanagement.model.entity.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Interfaz repositorio para la colección de clientes.
 */
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
    
}
