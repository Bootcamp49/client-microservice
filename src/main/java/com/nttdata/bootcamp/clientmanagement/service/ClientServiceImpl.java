package com.nttdata.bootcamp.clientmanagement.service;

import com.nttdata.bootcamp.clientmanagement.model.ProductTypeResponse;
import com.nttdata.bootcamp.clientmanagement.model.ProductsActiveResponse;
import com.nttdata.bootcamp.clientmanagement.model.ProductsPasiveResponse;
import com.nttdata.bootcamp.clientmanagement.model.ProductsReportByClientResponse;
import com.nttdata.bootcamp.clientmanagement.model.entity.Client;
import com.nttdata.bootcamp.clientmanagement.proxy.ProductsProxy;
import com.nttdata.bootcamp.clientmanagement.repository.ClientRepository;
import java.util.ArrayList;
import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación de la interfaz de clientes.
 */
@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final ClientRepository clientRepository;

    @Autowired
    private final ProductsProxy productsProxy;

    @Override
    @CircuitBreaker(name = "client", fallbackMethod = "singleClientFallback")
    public Mono<Client> updateClient(@NonNull String id, Client client) {
        return clientRepository.findById(id)
                .flatMap(existingClient -> {
                    existingClient.setName(client.getName());
                    existingClient.setLastName(client.getLastName());
                    existingClient.setClientType(client.getClientType());
                    existingClient.setDocumentNumber(client.getDocumentNumber());
                    existingClient.setPassword(client.getPassword());
                    existingClient.setCellPhoneNumber(client.getCellPhoneNumber());
                    existingClient.setImei(client.getImei());
                    existingClient.setEmail(client.getEmail());
                    return clientRepository.save(existingClient);
                });
    }

    @Override
    @CircuitBreaker(name = "client", fallbackMethod = "multipleClientFallback")
    public Flux<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    @CircuitBreaker(name = "client", fallbackMethod = "singleClientFallback")
    public Mono<Client> findById(@NonNull String id) {
        return clientRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteClient(@NonNull String id) {
        return clientRepository.deleteById(id);
    }

    @Override
    @CircuitBreaker(name = "client", fallbackMethod = "singleClientFallback")
    public Mono<Client> createClient(@NonNull Client client) {
        return clientRepository.save(client);
    }

    @Override
    @CircuitBreaker(name = "client", fallbackMethod = "singleClientFallback")
    public Mono<Client> createVipClient(@NonNull String clientId) {
        Mono<Client> createVipClientResponse = null;
        Boolean isClientValidToCreate = validateVipPymeClientCreation("VIP", clientId);
        if (isClientValidToCreate) {
            createVipClientResponse = clientRepository.findById(clientId)
                    .flatMap(existingClient -> {
                        existingClient.setClientType("VIP");
                        return clientRepository.save(existingClient);
                    });
        }
        return createVipClientResponse;
    }

    @Override
    @CircuitBreaker(name = "client", fallbackMethod = "singleClientFallback")
    public Mono<Client> createMypeClient(@NonNull String clientId) {
        Mono<Client> createMypeClientResponse = null;
        Boolean isClientValidToCreate = validateVipPymeClientCreation("PYME", clientId);
        if (isClientValidToCreate) {
            createMypeClientResponse = clientRepository.findById(clientId)
                    .flatMap(existingClient -> {
                        existingClient.setClientType("PYME");
                        return clientRepository.save(existingClient);
                    });
        }
        return createMypeClientResponse;
    }

    @Override
    @CircuitBreaker(name = "client", fallbackMethod = "productReportFallback")
    public Mono<ProductsReportByClientResponse> getProductsReportByClientId(String clientId) {
        ProductsReportByClientResponse report = new ProductsReportByClientResponse();
        List<ProductsActiveResponse> activeProducts = new ArrayList<ProductsActiveResponse>();
        List<ProductsPasiveResponse> pasiveProducts = new ArrayList<ProductsPasiveResponse>();

        Flux<ProductsActiveResponse> activeProductsFlux = 
            productsProxy.getProductsActiveByClientId(clientId);
        activeProductsFlux.subscribe(productActive -> activeProducts.add(productActive));

        Flux<ProductsPasiveResponse> pasiveProductsFlux =
            productsProxy.getProductsPasiveByClientId(clientId);
        pasiveProductsFlux.subscribe(productPasive -> pasiveProducts.add(productPasive));

        Mono<ProductsReportByClientResponse> clientReport = findById(clientId).flatMap(client -> {
            report.setName(client.getName());
            report.setLastName(client.getLastName());
            report.setDocumentNumber(client.getDocumentNumber());
            report.setProductsActive(activeProducts);
            report.setProductsPasive(pasiveProducts);
            return Mono.just(report);
        });
        return clientReport;
    }

    @Override
    @CircuitBreaker(name = "client", fallbackMethod = "singleClientFallback")
    public Mono<Client> createYankeeClient(Client client) {
        Mono<Client> createdClient = clientRepository.save(client);
        Client clientToValidate = createdClient.block();
        if (clientToValidate != null && clientToValidate.getId() != null) {
            ProductsPasiveResponse request = new ProductsPasiveResponse();
            ProductTypeResponse typeRequest = new ProductTypeResponse();
            typeRequest.setId(1);
            typeRequest.setDescription("Ahorro");
            request.setClientId(clientToValidate.getId());
            request.setCurrentAmount(0.0);
            request.setType(typeRequest);
            request.setIsYankeeProduct(true);

            Mono<ProductsPasiveResponse> productCreated =
                    productsProxy.createPasiveYankeeProduct(request);
            if(clientToValidate.getId() == null){
                return null;
            }
        }
        return createdClient;
    }

    private Boolean validateVipPymeClientCreation(String clientType, String clientId) {
        Boolean isValid = false;
        try {
            if (clientType == "VIP") {
                Flux<ProductsPasiveResponse> pasiveProducts = productsProxy
                        .getProductsPasiveByClientId(clientId);
                Boolean hasPasiveProductValid = pasiveProducts
                        .filter(p -> p.getType().getId() == 2).count()
                        .block() > 0;

                isValid = hasPasiveProductValid;
            } else if (clientType == "PYME") {
                Flux<ProductsPasiveResponse> pasiveProducts = productsProxy
                        .getProductsPasiveByClientId(clientId);
                Boolean hasPasiveProductValid = pasiveProducts
                        .filter(p -> p.getType().getId() == 2).count()
                        .block() > 0;

                Flux<ProductsActiveResponse> activeProducts = productsProxy
                        .getProductsActiveByClientId(clientId);
                Boolean hasActiveProductValid = activeProducts
                        .filter(p -> p.getType().getId() == 3).count()
                        .block() > 0;

                isValid = hasPasiveProductValid && hasActiveProductValid;
            }
        } catch (Exception e) {
            System.out.println("Error clientServiceImpl: " + e);
        }
        return isValid;
    }

    private Mono<Client> singleClientFallback(Throwable throwable){
        Client clientToReturn = new Client();
        return Mono.just(clientToReturn);
    }
    private Flux<Client> multipleClientFallback(Throwable throwable){
        Client clientToReturn = new Client();
        return Flux.just(clientToReturn);
    }
    private Mono<ProductsReportByClientResponse> productReportFallback(Throwable throwable){
        ProductsReportByClientResponse productsReportByClientResponse = new ProductsReportByClientResponse();
        return Mono.just(productsReportByClientResponse);
    }
}
