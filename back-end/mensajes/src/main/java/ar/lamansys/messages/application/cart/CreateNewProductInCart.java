package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.*;
import ar.lamansys.messages.application.product.AssertProductExists;
import ar.lamansys.messages.application.product.exception.ProductNotExistsException;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.addedproduct.AddedProductBo;
import ar.lamansys.messages.domain.addedproduct.NewProductBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreateNewProductInCart {

    private final AddedProductStorage addedProduct;
    private final AssertUserExists assertUserExists;
    private final AssertCartExists assertCartExists;
    private final AssertUserOwnsCart assertUserOwnsCart;
    private final AssertProductExists assertProductExists;
    private final AssertProductValidSeller assertProductValidSeller;
    private final AssertProductStockEnough assertProductStockEnough;

    public void run(String ownerId, String cartId, NewProductBo newProduct) throws
            UserNotExistsException, CartNotExistsException, UserNotOwnsCartException,
            ProductNotExistsException, ProductInvalidSeller, ProductStockNotEnough {

        assertUserExists.run(ownerId);
        assertCartExists.run(cartId);
        assertUserOwnsCart.run(cartId, ownerId);
        assertProductExists.run(newProduct.getProductId());
        assertProductValidSeller.run(cartId, newProduct.getProductId());
        assertProductStockEnough.run(newProduct.getProductId(), newProduct.getQuantity());

        addedProduct.save(new AddedProductBo(cartId, newProduct.getProductId(), newProduct.getQuantity()));

    }
}
