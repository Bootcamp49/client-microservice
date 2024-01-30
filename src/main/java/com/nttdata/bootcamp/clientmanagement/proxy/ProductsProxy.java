package com.nttdata.bootcamp.clientmanagement.proxy;

import com.nttdata.bootcamp.clientmanagement.model.ProductsActiveResponse;
import com.nttdata.bootcamp.clientmanagement.model.ProductsPasiveResponse;
import reactor.core.publisher.Flux;

/**
 * Interface para definir los metodos a usar del microservicio de productos.
 */
public interface ProductsProxy {
    Flux<ProductsPasiveResponse> getProductsPasiveByClientId(String clientId);

    Flux<ProductsActiveResponse> getProductsActiveByClientId(String clientId);
}
