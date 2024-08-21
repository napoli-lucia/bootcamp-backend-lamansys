package ar.lamansys.messages.domain.cart;

import ar.lamansys.messages.domain.product.ProductStateBo;
import lombok.AllArgsConstructor;
import lombok.Value;
import java.util.List;

@AllArgsConstructor
@Value
public class CartStateBo {
    Float totalPrice;
    List<String> leftOut;
    List<ProductStateBo> products;

}
