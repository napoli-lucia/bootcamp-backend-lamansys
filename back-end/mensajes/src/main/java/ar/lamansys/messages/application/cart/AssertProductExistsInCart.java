package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.ProductNotExistsException;
import ar.lamansys.messages.application.cart.exception.ProductNotExistsInCartException;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.AddedProductId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertProductExistsInCart {

    private AddedProductStorage addedProductStorage;

    public void run(String cartId, String productId) throws ProductNotExistsInCartException {
        if ( !addedProductStorage.exists(new AddedProductId(cartId, productId) )) {
            throw new ProductNotExistsInCartException(productId);
        }
    }
}
