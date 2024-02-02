package com.nttdata.bootcamp.clientmanagement.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase organizar el reporte de clientes y sus productos.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsReportByClient extends Client {
    List<ProductsActiveResponse> productsActive;

    List<ProductsPasiveResponse> productsPasive;
}
