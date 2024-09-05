package ar.lamansys.messages.application.product;

import ar.lamansys.messages.application.product.exception.InvalidSellerException;
import ar.lamansys.messages.application.product.exception.ProductExistsException;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.product.ProductBo;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AddNewProductToSell {
    private final ProductStorage productStorage;
    private final AssertUserExists assertUserExists;
    private final AssertProductNotExists assertProductNotExists;
    private final AssertUserIsSeller assertUserSeller;

    public void run(String sellerId, ProductBo newProduct) throws UserNotExistsException, ProductExistsException, InvalidSellerException {
        assertProductNotExists.run(newProduct.getProductId());
        assertUserExists.run(sellerId);
        assertUserExists.run(newProduct.getOwnerId());
        assertUserSeller.run(sellerId, newProduct.getOwnerId());

        productStorage.save(newProduct);
    }
}