package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.CartNotExistsException;
import ar.lamansys.messages.application.cart.exception.ProductNotExistsInCartException;
import ar.lamansys.messages.application.cart.exception.UserNotOwnsCartException;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.AddedProductId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeleteProductInCart {

    private final AddedProductStorage addedProduct;
    private final AssertUserExists assertUserExists;
    private final AssertCartExists assertCartExists;
    private final AssertUserOwnsCart assertUserOwnsCart;
    private final AssertProductExistsInCart assertProductExistsInCart;

    public void run(String ownerId, String cartId, String productId) throws UserNotExistsException, CartNotExistsException, UserNotOwnsCartException, ProductNotExistsInCartException {

        assertUserExists.run(ownerId);
        assertCartExists.run(cartId);
        assertUserOwnsCart.run(cartId, ownerId);
        assertProductExistsInCart.run(cartId, productId);

        addedProduct.delete(new AddedProductId(cartId, productId));
    }

}
