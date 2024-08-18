package ar.lamansys.messages.domain;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class CartBo {
    String cartId;
    String ownerId;
    String sellerId;
}
