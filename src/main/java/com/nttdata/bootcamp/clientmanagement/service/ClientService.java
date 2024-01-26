package com.nttdata.bootcamp.clientmanagement.service;

import com.nttdata.bootcamp.clientmanagement.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interfaz del Servicio de clientes.
 */
public interface ClientService {

    /**
     * Método para la actualización de un cliente por su Id.
     * @param id Id del cliente a actualizar
     * @param client Cuerpo del cliente a actualizar
     * @return Retorna el cuerpo del cliente actualizado
     */
    Mono<Client> updateClient(String id, Client client);

    /**
     * Método para retornar a todos los clientes de la base de datos.
     * @return Retorna todos los clientes registrados en el sistema
     */
    Flux<Client> findAllClients();

    /**
     * Método para retornar un cliente por su Id.
     * @param id Id del cliente específico a buscar
     * @return Retorna un cliente específico según su Id
     */
    Mono<Client> findById(String id);

    /**
     * Método para eliminar a un cliente por su Id.
     * @param id Id del cliente a elimianr
     * @return Retorna un Void sobre la eliminación del cliente
     */
    Mono<Void> deleteClient(String id);

    /**
     * Método para crear un nuevo cliente.
     * @param client Cuerpo de cliente a crear
     * @return Retorna el cuerpo del cliente ya creado
     */
    Mono<Client> createClient(Client client);
}
