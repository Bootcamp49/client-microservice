package com.nttdata.bootcamp.clientmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase para los tipos de productos.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductType {
    Integer id;
    String description;
}
