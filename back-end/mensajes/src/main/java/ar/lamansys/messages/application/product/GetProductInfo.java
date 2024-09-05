package ar.lamansys.messages.application.product;

import ar.lamansys.messages.application.product.exception.ProductNotExistsException;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class GetProductInfo {

    private final ProductStorage productStorage;
    private final AssertProductExists assertProductExists;

    public Optional<Product> run(String productId) throws ProductNotExistsException {
        assertProductExists.run(productId);

        return productStorage.getProductByProductId(productId);
    }
}
