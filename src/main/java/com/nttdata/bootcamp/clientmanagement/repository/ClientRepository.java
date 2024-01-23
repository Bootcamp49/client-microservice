package com.nttdata.bootcamp.clientmanagement.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.ResponseEntity;

import com.nttdata.bootcamp.clientmanagement.model.Client;

// import com.nttdata.bootcamp.clientmanagement.model.ClientImpl;
// import com.nttdata.bootcamp.model.Client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ClientRepository extends ReactiveCrudRepository<Client, String>{

}
