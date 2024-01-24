package com.nttdata.bootcamp.clientmanagement.service;

import com.nttdata.bootcamp.clientmanagement.model.Client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    /**
     * @param id Id del cliente a actualizar
     * @param client Cuerpo del cliente a actualizar
     * @return Retorna el cuerpo del cliente actualizado
     */
    Mono<Client> updateClient(String id, Client client);

    /**
     * @return Retorna todos los clientes registrados en el sistema
     */
    Flux<Client> findAllClients();

    /**
     * @param id Id del cliente específico a buscar
     * @return Retorna un cliente específico según su Id
     */
    Mono<Client> findById(String id);

    /**
     * @param id Id del cliente a elimianr
     * @return Retorna un Void sobre la eliminación del cliente
     */
    Mono<Void> deleteClient(String id);

    /**
     * @param client Cuerpo de cliente a crear
     * @return Retorna el cuerpo del cliente ya creado
     */
    Mono<Client> createClient(Client client);
}
