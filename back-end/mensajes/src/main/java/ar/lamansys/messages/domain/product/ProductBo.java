package ar.lamansys.messages.domain.product;

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
