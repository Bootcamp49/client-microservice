package com.nttdata.bootcamp.clientmanagement.service;

import com.nttdata.bootcamp.clientmanagement.model.Client;
import com.nttdata.bootcamp.clientmanagement.model.ProductsActiveResponse;
import com.nttdata.bootcamp.clientmanagement.model.ProductsPasiveResponse;
import com.nttdata.bootcamp.clientmanagement.model.ProductsReportByClient;
import com.nttdata.bootcamp.clientmanagement.proxy.ProductsProxy;
import com.nttdata.bootcamp.clientmanagement.repository.ClientRepository;
import java.util.ArrayList;
import java.util.List;
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
    public Mono<Client> updateClient(@NonNull String id, Client client) {
        return clientRepository.findById(id)
                .flatMap(existingClient -> {
                    existingClient.setName(client.getName());
                    existingClient.setLastName(client.getLastName());
                    existingClient.setClientType(client.getClientType());
                    existingClient.setDocumentNumber(client.getDocumentNumber());
                    existingClient.setPassword(client.getPassword());
                    return clientRepository.save(existingClient);
                });
    }

    @Override
    public Flux<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Mono<Client> findById(@NonNull String id) {
        return clientRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteClient(@NonNull String id) {
        return clientRepository.deleteById(id);
    }

    @Override
    public Mono<Client> createClient(@NonNull Client client) {
        return clientRepository.save(client);
    }

    @Override
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
    public Mono<ProductsReportByClient> getProductsReportByClientId(String clientId) {
        ProductsReportByClient report = new ProductsReportByClient();
        List<ProductsActiveResponse> activeProducts = new ArrayList<ProductsActiveResponse>();
        List<ProductsPasiveResponse> pasiveProducts = new ArrayList<ProductsPasiveResponse>();

        Flux<ProductsActiveResponse> activeProductsFlux = 
            productsProxy.getProductsActiveByClientId(clientId);
        activeProductsFlux.subscribe(productActive -> activeProducts.add(productActive));

        Flux<ProductsPasiveResponse> pasiveProductsFlux =
            productsProxy.getProductsPasiveByClientId(clientId);
        pasiveProductsFlux.subscribe(productPasive -> pasiveProducts.add(productPasive));

        Mono<ProductsReportByClient> clientReport = findById(clientId).flatMap(client -> {
            report.setName(client.getName());
            report.setLastName(client.getLastName());
            report.setDocumentNumber(client.getDocumentNumber());
            report.setProductsActive(activeProducts);
            report.setProductsPasive(pasiveProducts);
            return Mono.just(report);
        });
        return clientReport;
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
}
