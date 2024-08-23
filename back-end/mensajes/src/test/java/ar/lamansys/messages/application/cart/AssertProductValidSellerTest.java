package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.ProductInvalidSeller;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.Cart;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertProductValidSellerTest {

    private ProductStorage productStorage = Mockito.mock(ProductStorage.class);
    private CartStorage cartStorage = Mockito.mock(CartStorage.class);

    private AssertProductValidSeller assertProductValidSeller = new AssertProductValidSeller(productStorage, cartStorage);

    @Test
    void productValidSeller_ok() {
        Mockito.when(productStorage.getOwnerByProductId("productId")).thenReturn("cartSellerId");

        Cart mockCart = Mockito.mock(Cart.class);
        Mockito.when(mockCart.getSellerId()).thenReturn("cartSellerId");
        Mockito.when(cartStorage.getById("cartId")).thenReturn(Optional.of(mockCart));

        assertDoesNotThrow(() -> assertProductValidSeller.run("cartId","productId"));
    }

    @Test
    void productNotValidSeller_throwsException() {
        Mockito.when(productStorage.getOwnerByProductId("productId")).thenReturn("notCartSellerId");

        Cart mockCart = Mockito.mock(Cart.class);
        Mockito.when(mockCart.getSellerId()).thenReturn("cartSellerId");
        Mockito.when(cartStorage.getById("cartId")).thenReturn(Optional.of(mockCart));

        assertThrows(ProductInvalidSeller.class, () -> assertProductValidSeller.run("cartId","productId"));
    }

}
