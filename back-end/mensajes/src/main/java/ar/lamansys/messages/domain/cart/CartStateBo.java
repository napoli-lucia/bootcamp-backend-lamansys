package ar.lamansys.messages.domain.cart;

import ar.lamansys.messages.domain.product.ProductStateBo;
import lombok.AllArgsConstructor;
import lombok.Value;
import java.util.List;

@AllArgsConstructor
@Value
public class CartStateBo {
    //@revision el totalPrice seria mejor si es una funcionalidad del carrito
    //no deberia hacer falta definirlo y tener que recargar el dato
    Float totalPrice;
    List<String> leftOut;
    List<ProductStateBo> products;

}
