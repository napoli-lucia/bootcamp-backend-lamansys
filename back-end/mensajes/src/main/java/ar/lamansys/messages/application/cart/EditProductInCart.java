package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.CartNotExistsException;
import ar.lamansys.messages.application.cart.exception.ProductNotExistsInCartException;
import ar.lamansys.messages.application.cart.exception.ProductStockNotEnough;
import ar.lamansys.messages.application.cart.exception.UserNotOwnsCartException;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.addedproduct.AddedProductBo;
import ar.lamansys.messages.domain.addedproduct.NewProductBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EditProductInCart {

    private final AddedProductStorage addedProduct;

    private final AssertUserExists assertUserExists;
    private final AssertCartExists assertCartExists;
    private final AssertUserOwnsCart assertUserOwnsCart;
    private final AssertProductStockEnough assertProductStockEnough;
    private final AssertProductExistsInCart assertProductExistsInCart;

    public void run(String ownerId, String cartId, NewProductBo editedProduct) throws
            UserNotExistsException, CartNotExistsException, UserNotOwnsCartException, ProductNotExistsInCartException, ProductStockNotEnough {

        assertUserExists.run(ownerId);
        assertCartExists.run(cartId);
        assertUserOwnsCart.run(cartId, ownerId);
        assertProductExistsInCart.run(cartId, editedProduct.getProductId());
        assertProductStockEnough.run(editedProduct.getProductId(), editedProduct.getQuantity());

        addedProduct.save(new AddedProductBo(cartId, editedProduct.getProductId(), editedProduct.getQuantity()));

    }
}
