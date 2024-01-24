package com.nttdata.bootcamp.clientmanagement.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.nttdata.bootcamp.clientmanagement.model.Client;

public interface ClientRepository extends ReactiveCrudRepository<Client, String>{
    
}
