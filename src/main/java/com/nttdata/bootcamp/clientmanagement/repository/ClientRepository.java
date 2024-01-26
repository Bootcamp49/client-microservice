package com.nttdata.bootcamp.clientmanagement.repository;

import com.nttdata.bootcamp.clientmanagement.model.Client;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Interfaz repositorio para la colección de clientes.
 */
public interface ClientRepository extends ReactiveCrudRepository<Client, String> {
    
}
