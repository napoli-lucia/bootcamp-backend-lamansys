package ar.lamansys.messages.application.product;

import ar.lamansys.messages.application.product.exception.ProductNotExistsException;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeleteProductToSell {
    private final ProductStorage productStorage;
    private final AssertProductExists assertProductExists;

    public void run(String productId) throws ProductNotExistsException {
        assertProductExists.run(productId);

        productStorage.delete(productId);
    }
}
