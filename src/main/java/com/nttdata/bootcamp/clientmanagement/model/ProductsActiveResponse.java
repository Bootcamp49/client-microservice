package com.nttdata.bootcamp.clientmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase para leer la respuesta del servicio de productos activos.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsActiveResponse {
    private String id;

    private ProductType type;

    private String accountNumber;

    private Double currentCredit;

    private Integer movements;

    private Double creditLine;

    private String creditCardNumber;

    private String clientId;
}
