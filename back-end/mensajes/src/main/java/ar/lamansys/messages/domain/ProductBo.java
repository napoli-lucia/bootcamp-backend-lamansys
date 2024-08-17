package ar.lamansys.messages.domain;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class ProductBo {
    String productId;
    String ownerId;
    String productName;
    Integer stock;
    Float unityPrice;
}
