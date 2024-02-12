package com.nttdata.bootcamp.clientmanagement.proxy;

import com.nttdata.bootcamp.clientmanagement.model.ProductTypeResponse;
import com.nttdata.bootcamp.clientmanagement.model.ProductsActiveResponse;
import com.nttdata.bootcamp.clientmanagement.model.ProductsPasiveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase para la implementación de los métodos del Proxy de producto a usar.
 */
@RequiredArgsConstructor
@Service
public class ProductsProxyImpl implements ProductsProxy {

    private final WebClient webClient = WebClient.create("http://localhost:8084");

    @Override
    public Flux<ProductsPasiveResponse> getProductsPasiveByClientId(String clientId) {
        Flux<ProductsPasiveResponse> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/product/pasive/client/{clientId}")
                    .build(clientId))
                .retrieve()
                .bodyToFlux(ProductsPasiveResponse.class);
        return response;
    }

    @Override
    public Flux<ProductsActiveResponse> getProductsActiveByClientId(String clientId) {
        Flux<ProductsActiveResponse> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/product/active/client/{clientId}")
                    .build(clientId))
                .retrieve()
                .bodyToFlux(ProductsActiveResponse.class);
        return response;
    }

    @Override
    public Mono<ProductsPasiveResponse> createPasiveYankeeProduct(String clientId) {
        ProductsPasiveResponse request = new ProductsPasiveResponse();
        ProductTypeResponse typeRequest = new ProductTypeResponse();
        typeRequest.setId(1);
        typeRequest.setDescription("Ahorro");
        request.setClientId(clientId);
        request.setCurrentAmount(0.0);
        request.setType(typeRequest);
        request.setIsYankeeProduct(true);
        //request.
        Mono<ProductsPasiveResponse> response = webClient.post()
                .uri("/pasive")
                .body(BodyInserters.fromValue(request))
                .exchangeToMono(productResponse -> {
                    if(productResponse.statusCode().equals(HttpStatus.OK)){
                        return productResponse.bodyToMono(ProductsPasiveResponse.class);
                    }else {
                        return productResponse.createError();
                    }
                });
        return response;
    }

}
