package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.ProductInvalidSeller;
import ar.lamansys.messages.application.cart.exception.ProductStockNotEnough;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertProductValidSeller {

    private ProductStorage productStorage;
    private CartStorage cartStorage;

    //@revision estaria bueno armar las querys y que solo retornen los datos necesarios
    //@revision cuidado: con tener excepciones no chequeadas
    public void run(String cartId, String productId) throws ProductInvalidSeller {
        if ( !productStorage.getOwnerByProductId(productId).equals(cartStorage.getById(cartId).get().getSellerId())) {//tira excepcion si no existe
            throw new ProductInvalidSeller(productId);
        }
    }
}
