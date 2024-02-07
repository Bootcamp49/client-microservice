package com.nttdata.bootcamp.clientmanagement.controller;

import com.nttdata.bootcamp.clientmanagement.model.ProductsReportByClientResponse;
import com.nttdata.bootcamp.clientmanagement.model.entity.Client;
import com.nttdata.bootcamp.clientmanagement.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador para el manejo de clientes.
 */
@RestController
@RequestMapping("/clients/v1")
public class ClientController {

    /**
     * Interfaz de clientes.
     */
    @Autowired
    private ClientService clientService;

    /**
     * Método para actualizar un cliente por su Id.
     * @param id     Id del cliente a actualizar
     * @param client Cuerpo del cliente a actualizar
     * @return Retorna el cuerpo del cliente actualizado
     */
    @PutMapping("{id}")
    public Mono<Client> updateClient(@PathVariable String id, @RequestBody Client client) {
        return clientService.updateClient(id, client);
    }

    /**
     * Método para buscar y retornar un cliente por su Id.
     * @param id Id del cliente a obtener
     * @return Retorna un cliente específico por su Id
     */
    @GetMapping("{id}")
    public Mono<Client> findById(@PathVariable String id) {
        return clientService.findById(id);
    }

    /**
     * Método para eliminar un cliente por su Id.
     * @param id Id del cliente a eliminar
     * @return Retorna un Void sobre la eliminación del cliente
     */
    @DeleteMapping("{id}")
    public Mono<Void> deleteClient(@PathVariable String id) {
        return clientService.deleteClient(id);
    }

    /**
     * Método para retornar a todos los clientes.
     * @return Retorna todos los clientes del sistema
     */
    @GetMapping()
    public Flux<Client> findClients() {
        return clientService.findAllClients();
    }

    /**
     * Método para crear nuevos clientes.
     * @param client Cuerpo del cliente a crear
     * @return Rotorna el cuerpo del cliente ya creado
     */
    @PostMapping()
    public Mono<Client> createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    /**
     * Método para cambiar el tipo de un cliente a VIP.
     * @param clientId Id del cliente a volver VIP
     * @return Retorna el cuerpo correcto del cliente con tipo VIP
     */
    @PutMapping("/VIP/{clientId}")
    public Mono<Client> createVipClient(@PathVariable String clientId) {
        return clientService.createVipClient(clientId);
    }

    /**
     * Método para cambiar el tipo de un cliente a PYME.
     * @param clientId Id del cliente a volver PYME
     * @return Retorna el cuerpo correcto del cliente con tipo PYME
     */
    @PutMapping("/PYME/{clientId}")
    public Mono<Client> createPyme(@PathVariable String clientId) {
        return clientService.createMypeClient(clientId);
    }

    /**
     * Método para obtener un reporte cliente con sus productos registrados.
     * @param clientId El clientId del cliente del cual obtener el reporte.
     * @return Se retorna los datos del cliente con sus respectivos productos.
     */
    @GetMapping("/report/{clientId}/products")
    public Mono<ProductsReportByClientResponse> getProductsReportByClientId(@PathVariable String clientId) {
        return clientService.getProductsReportByClientId(clientId);
    }
}
