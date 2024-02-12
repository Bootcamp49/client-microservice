package com.nttdata.bootcamp.clientmanagement.proxy;

import com.nttdata.bootcamp.clientmanagement.model.ProductsActiveResponse;
import com.nttdata.bootcamp.clientmanagement.model.ProductsPasiveResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interface para definir los metodos a usar del microservicio de productos.
 */
public interface ProductsProxy {

    /**
     * Método para realizar la llamada al microservicio de productos.
     * @param clientId Id del cliente para solicitar sus productos pasivos.
     * @return Retorna La lista de productos pasivos que el cliente pueda tener.
     */
    Flux<ProductsPasiveResponse> getProductsPasiveByClientId(String clientId);

    /**
     * Método para realizar la llamada al microservicio de productos.
     * @param clientId Id del cliente para solicitar sus productos activos.
     * @return Retorna la lista de productos activos que el cliente pueda tener.
     */
    Flux<ProductsActiveResponse> getProductsActiveByClientId(String clientId);

    Mono<ProductsPasiveResponse> createPasiveYankeeProduct(String clientId);
}
