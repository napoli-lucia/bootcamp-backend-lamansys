package ar.lamansys.messages.application.product;

import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.product.PostProductBo;
import ar.lamansys.messages.domain.product.ProductBo;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class AddProduct {

    private final AssertUserExists assertUserExists;
    private final ProductStorage productStorage;

    public void run(PostProductBo newProduct) throws UserNotExistsException {
        assertUserExists.run(newProduct.getOwnerId());

        String productId = UUID.randomUUID().toString();
        ProductBo product = new ProductBo(productId,
                newProduct.getOwnerId(),
                newProduct.getProductName(),
                newProduct.getStock(),
                newProduct.getUnityPrice());

        productStorage.save(product);
    }
}
