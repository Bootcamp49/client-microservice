package com.nttdata.bootcamp.clientmanagement.proxy;

import com.nttdata.bootcamp.clientmanagement.model.ProductsActiveResponse;
import com.nttdata.bootcamp.clientmanagement.model.ProductsPasiveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Clase para la implementación de los métodos del Proxy de producto a usar.
 */
@RequiredArgsConstructor
@Service
public class ProductsProxyImpl implements ProductsProxy {

    private WebClient webClient = WebClient.create("http://localhost:8081");

    @Override
    public Flux<ProductsPasiveResponse> getProductsPasiveByClientId(String clientId) {
        Flux<ProductsPasiveResponse> response = webClient.get()
                .uri("/product/pasive/client/{clientId}", clientId)
                .retrieve()
                .bodyToFlux(ProductsPasiveResponse.class);
        return response;
    }

    @Override
    public Flux<ProductsActiveResponse> getProductsActiveByClientId(String clientId) {
        Flux<ProductsActiveResponse> response = webClient.get()
                .uri("/product/active/client/{clientId}", clientId)
                .retrieve()
                .bodyToFlux(ProductsActiveResponse.class);
        return response;
    }

}
