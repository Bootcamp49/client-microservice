package com.nttdata.bootcamp.clientmanagement.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase para organizar la respuesta de productos pasivos.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsPasiveResponse {
    private String id;

    private ProductType type;

    private String accountNumber;

    private Double currentAmount;

    private Integer movements;

    private LocalDate creationDate;

    private String clientId;
}
