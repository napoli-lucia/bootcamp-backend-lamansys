package ar.lamansys.messages.domain.cart;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class CartBo {
    String cartId;
    String ownerId;
    String sellerId;
}
