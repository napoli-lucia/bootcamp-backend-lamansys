package ar.lamansys.messages.application.product;

import ar.lamansys.messages.application.product.exception.ProductExistsException;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.addedproduct.NewProductBo;
import ar.lamansys.messages.domain.product.ProductBo;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AddNewProductToSell {
    private final ProductStorage productStorage;
    private final AssertUserExists assertUserExists;
    private final AssertProductNotExists assertProductNotExists;

    public void run(ProductBo newProduct) throws UserNotExistsException, ProductExistsException {
        assertProductNotExists.run(newProduct.getProductId());
        assertUserExists.run(newProduct.getOwnerId());

        productStorage.save(newProduct);
    }
}