package com.nttdata.bootcamp.clientmanagement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.nttdata.bootcamp.clientmanagement.controller.ClientController;
import com.nttdata.bootcamp.clientmanagement.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ClientControllerTests {

    @Autowired
    private ClientController clientController;

    @MockBean
    private ClientService clientService;


    @Test
    void contextLoads() {
        assertNotNull(clientController);
    }

    void testingFindClients() {
        // when(clientService.findAllClients()).thenReturn(any)
    }

}
