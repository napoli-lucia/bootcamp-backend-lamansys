package ar.lamansys.messages.application.product;

import ar.lamansys.messages.application.product.exception.ProductExistsException;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertProductNotExists {

    private ProductStorage productStorage;

    public void run(String productId) throws ProductExistsException {
        if ( productStorage.exists(productId) ) {
            throw new ProductExistsException(productId);
        }
    }
}
