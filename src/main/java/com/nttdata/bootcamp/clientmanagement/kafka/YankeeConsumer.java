package com.nttdata.bootcamp.clientmanagement.kafka;

import com.nttdata.bootcamp.clientmanagement.model.ClientEvent;

public interface YankeeConsumer {
    void consume(String event);
}
