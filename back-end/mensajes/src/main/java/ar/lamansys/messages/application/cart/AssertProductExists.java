package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.ProductNotExistsException;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertProductExists {

    private ProductStorage productStorage;

    public void run(String productId) throws ProductNotExistsException {
        if ( !productStorage.exists(productId) ) {
            throw new ProductNotExistsException(productId);
        }
    }
}
