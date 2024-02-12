package com.nttdata.bootcamp.clientmanagement.kafka;

import com.google.gson.Gson;
import com.nttdata.bootcamp.clientmanagement.model.ClientEvent;
import com.nttdata.bootcamp.clientmanagement.model.entity.Client;
import com.nttdata.bootcamp.clientmanagement.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class YankeeConsumerImpl implements YankeeConsumer{

    @Autowired
    private ClientService clientService;
    @Override
    @KafkaListener(
            topics = "create_yankee_client",
            groupId = "client"
    )
    public void consume(String event) {
        ClientEvent clientEvent = new Gson().fromJson(event, ClientEvent.class);
        Client clientToCreate = new Client();
        clientToCreate.setEmail(clientEvent.getClient().getEmail());
        clientToCreate.setImei(clientEvent.getClient().getImei());
        clientToCreate.setCellPhoneNumber(clientEvent.getClient().getCellPhoneNumber());
        clientService.createYankeeClient(clientToCreate);
    }
}
