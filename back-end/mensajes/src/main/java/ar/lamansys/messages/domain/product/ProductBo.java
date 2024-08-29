package ar.lamansys.messages.domain.product;

import lombok.AllArgsConstructor;
import lombok.Value;

//ProductBO tiene muchos datos que lo mejor seria que no los conozca
//como el precio y la cantidad
@AllArgsConstructor
@Value
public class ProductBo {
    String productId;
    String ownerId;
    String productName;
    Integer stock;
    Float unityPrice;
}
