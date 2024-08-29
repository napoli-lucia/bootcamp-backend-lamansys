package ar.lamansys.messages.domain.product;

import lombok.AllArgsConstructor;
import lombok.Value;

//@revision es necesario este Bo?
@AllArgsConstructor
@Value
public class ProductStateBo {
    String productId;
    Float unityPrice;
}
