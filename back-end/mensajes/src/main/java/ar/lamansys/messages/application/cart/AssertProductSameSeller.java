package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.ProductInvalidSeller;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertProductSameSeller {

    private ProductStorage productStorage;

    public void run(String sellerId, String productId) throws ProductInvalidSeller {
        if ( !productStorage.getOwnerByProductId(productId).equals(sellerId)) {
            throw new ProductInvalidSeller(productId);
        }
    }
}
