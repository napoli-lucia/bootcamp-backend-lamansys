package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.ProductNotExistsInCartException;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.AddedProductId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertProductExistsInCartTest {

    private AddedProductStorage addedProductStorage = Mockito.mock(AddedProductStorage.class);

    private AssertProductExistsInCart assertProductExistsInCart = new AssertProductExistsInCart(addedProductStorage);

    private AddedProductId addedProductId = new AddedProductId("cartId", "productId");

    @Test
    void productExistenceInCart_ok() {
        Mockito.when(addedProductStorage.exists(addedProductId)).thenReturn(true);
        assertDoesNotThrow(() -> assertProductExistsInCart.run("cartId", "productId"));
    }

    @Test
    void productNotExistenceInCart_throwsException() {
        Mockito.when(addedProductStorage.exists(addedProductId)).thenReturn(false);
        assertThrows(ProductNotExistsInCartException.class, () -> assertProductExistsInCart.run("cartId","productId"));
    }
}
