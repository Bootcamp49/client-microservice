package com.nttdata.bootcamp.clientmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase organizar los subtipos de los productos activos o pasivos.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeResponse {
    Integer id;
    String description;
}
