package ar.lamansys.messages.domain.product;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class ProductStateBo {
    String productId;
    Float unityPrice;
}
