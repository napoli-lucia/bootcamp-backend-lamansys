package ar.lamansys.messages.domain.addedproduct;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class NewProductBo {
    String productId;
    Integer quantity;
}
