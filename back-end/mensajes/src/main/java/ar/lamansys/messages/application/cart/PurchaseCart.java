package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.CartNotExistsException;
import ar.lamansys.messages.application.cart.exception.MultipleExceptions;
import ar.lamansys.messages.application.cart.exception.ProductStockNotEnough;
import ar.lamansys.messages.application.cart.exception.UserNotOwnsCartException;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.AddedProduct;
import ar.lamansys.messages.infrastructure.output.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PurchaseCart {

    private final AddedProductStorage addedProduct;
    private final ProductStorage productStorage;
    private final CartStorage cartStorage;
    private final AssertUserExists assertUserExists;
    private final AssertCartExists assertCartExists;
    private final AssertUserOwnsCart assertUserOwnsCart;
    private final AssertProductStockEnough assertProductStockEnough;

    public float run(String ownerId, String cartId) throws CartNotExistsException, UserNotExistsException, UserNotOwnsCartException, MultipleExceptions {

        assertUserExists.run(ownerId);
        assertCartExists.run(cartId);
        assertUserOwnsCart.run(cartId, ownerId);

        List<Exception> exceptions = new ArrayList<>();
        Float totalPrice = 0.0f;

        List<AddedProduct> products = addedProduct.findAllByCartId(cartId);


        for (AddedProduct product : products) {
            try {
                assertProductStockEnough.run(product.getAddedProductId().getProductId(), product.getQuantity());

                totalPrice += productStorage.getUnityPriceByProductId(product.getAddedProductId().getProductId()) * product.getQuantity();

            }
            catch (ProductStockNotEnough e) {
                exceptions.add(e);
            }
        }

        if (!exceptions.isEmpty()){
            throw new MultipleExceptions(exceptions);
        }

        // Como hay stock, elimino carrito y added products
        addedProduct.deleteAllByCartId(cartId);
        cartStorage.delete(cartId);

        //Actualizo el stock de los productos
        for (AddedProduct product : products) {
            String productId = product.getAddedProductId().getProductId();
            Integer quantity = product.getQuantity();

            Product updatedProduct = productStorage.getProductByProductId(productId).get();
            updatedProduct.setStock(updatedProduct.getStock() - quantity);
            productStorage.updateProduct(updatedProduct);
        }

        return totalPrice;
    }
}