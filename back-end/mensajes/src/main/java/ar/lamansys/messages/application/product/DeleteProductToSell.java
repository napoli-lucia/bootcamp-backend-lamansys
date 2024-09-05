package ar.lamansys.messages.application.product;

import ar.lamansys.messages.application.product.exception.InvalidSellerException;
import ar.lamansys.messages.application.product.exception.ProductNotExistsException;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeleteProductToSell {
    private final ProductStorage productStorage;
    private final AssertProductExists assertProductExists;
    private final AssertUserExists assertUserExists;
    private final AssertUserIsSeller assertUserSeller;

    public void run(String sellerId, String productId) throws ProductNotExistsException, UserNotExistsException, InvalidSellerException {
        assertProductExists.run(productId);
        assertUserExists.run(sellerId);
        assertUserSeller.run(sellerId, productStorage.getOwnerByProductId(productId));

        productStorage.delete(productId);
    }
}
