package com.nttdata.bootcamp.clientmanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase de la colección Cliente de la base de datos.
 */
@Document(collection = "client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String documentNumber;
    private String password;
    private String clientType;
}
