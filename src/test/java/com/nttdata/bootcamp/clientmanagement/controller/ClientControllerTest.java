package com.nttdata.bootcamp.clientmanagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nttdata.bootcamp.clientmanagement.model.entity.Client;
import com.nttdata.bootcamp.clientmanagement.service.ClientService;
import com.nttdata.bootcamp.clientmanagement.util.ConstantsUtil;
import com.nttdata.bootcamp.clientmanagement.util.Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class ClientControllerTest {

    @Autowired
    private ClientController clientController;

    @MockBean
    private ClientService clientService;

    private Util util = new Util();

    @Test
    void contextLoads() {
        assertThat(clientController).isNotNull();
    }

    @Test
    void testFindClients() {
        when(clientService.findAllClients()).thenReturn(Flux.just(clientMockResponse()));
        Flux<Client> responseController = clientController.findClients();
        Flux<Client> responseToCompare = Flux.just(
                util.serializeArchive(ConstantsUtil.findAllClientsMock_Success, Client[].class));
        assertThat(responseController).usingRecursiveComparison().isEqualTo(responseToCompare);
    }

    @Test
    void testCreateClient() {
        when(clientService.createClient(any(Client.class)))
                .thenReturn(Mono.just(clientMockResponse()));
        Client mockedRequest = clientMockRequest();
        Mono<Client> responseClientController = clientController.createClient(mockedRequest);
        Mono<Client> responseToCompare = Mono.just(
            util.serializeArchive(ConstantsUtil.createClientMock_Success, Client.class)
        );
        assertThat(responseClientController)
        .usingRecursiveComparison().isEqualTo(responseToCompare);
    }

    @Test
    void testDeleteClient() {
        when(clientService.deleteClient(anyString()))
                .thenReturn(Mono.empty());
        clientController.deleteClient("1234");
        verify(clientService).deleteClient(anyString());
    }

    @Test
    void testFindById() {
        when(clientService.findById(anyString())).thenReturn(Mono.just(clientMockResponse()));
        Mono<Client> responseMono = clientController.findById("asd1234567890fghjkl");
        Mono<Client> responseToCompare = Mono.just(
            util.serializeArchive(ConstantsUtil.findByIdClientMock_Success, Client.class));
        assertThat(responseMono)
        .usingRecursiveComparison().isEqualTo(responseToCompare);
    }

    @Test
    void testUpdateClient() {
        when(clientService.updateClient(anyString(), any(Client.class)))
                .thenReturn(Mono.just(clientMockResponse()));
        Client mockedRequest = clientMockRequest();
        Mono<Client> responseClientController = 
            clientController.updateClient("asd1234567890fghjkl", mockedRequest);
        Mono<Client> responseToCompare = Mono.just(
            util.serializeArchive(ConstantsUtil.updateClientMock_Success, Client.class)
        );
        assertThat(responseClientController)
        .usingRecursiveComparison().isEqualTo(responseToCompare);
    }

    private Client clientMockResponse() {
        Client clientMocked = new Client();
        clientMocked.setId("asd1234567890fghjkl");
        clientMocked.setClientType("personal");
        clientMocked.setDocumentNumber("72094565");
        clientMocked.setLastName("Ramos");
        clientMocked.setName("Juan");
        clientMocked.setPassword("12345");
        return clientMocked;
    }

    private Client clientMockRequest() {
        Client clientMocked = new Client();
        clientMocked.setClientType("personal");
        clientMocked.setDocumentNumber("72094565");
        clientMocked.setLastName("Ramos");
        clientMocked.setName("Juan");
        clientMocked.setPassword("12345");
        return clientMocked;
    }
}
