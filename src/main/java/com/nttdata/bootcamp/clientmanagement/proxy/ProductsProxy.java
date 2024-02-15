package com.nttdata.bootcamp.clientmanagement.proxy;

import com.nttdata.bootcamp.clientmanagement.model.ProductsActiveResponse;
import com.nttdata.bootcamp.clientmanagement.model.ProductsPasiveResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Interface para definir los metodos a usar del microservicio de productos.
 */
@FeignClient(name="product-service")
public interface ProductsProxy {

    /**
     * Método para realizar la llamada al microservicio de productos.
     * @param clientId Id del cliente para solicitar sus productos pasivos.
     * @return Retorna La lista de productos pasivos que el cliente pueda tener.
     */
    @GetMapping("/pasive/client/{clientId}")
    List<ProductsPasiveResponse> getProductsPasiveByClientId(@PathVariable String clientId);

    /**
     * Método para realizar la llamada al microservicio de productos.
     * @param clientId Id del cliente para solicitar sus productos activos.
     * @return Retorna la lista de productos activos que el cliente pueda tener.
     */
    @GetMapping("/active/client/{clientId}")
    List<ProductsActiveResponse> getProductsActiveByClientId(@PathVariable String clientId);

    @PostMapping("/pasive")
    ProductsPasiveResponse createPasiveYankeeProduct(ProductsPasiveResponse request);
}
