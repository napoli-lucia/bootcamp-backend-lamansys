package ar.lamansys.messages.domain;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class AddedProductBo {

    String cartId;
    String productId;
    Integer quantity;
}
