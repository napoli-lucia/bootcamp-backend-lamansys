package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.UserNotOwnsCartException;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.entity.Cart;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertUserOwnsCartTest {

    private CartStorage cartStorage = Mockito.mock(CartStorage.class);

    private AssertUserOwnsCart assertUserOwnsCart = new AssertUserOwnsCart(cartStorage);

    @Test
    void userOwnsCart_ok() {
        Cart mockCart = Mockito.mock(Cart.class);
        Mockito.when(mockCart.getOwnerId()).thenReturn("ownerId");
        Mockito.when(cartStorage.getById("cartId")).thenReturn(Optional.of(mockCart));

        assertDoesNotThrow(() -> assertUserOwnsCart.run("cartId","ownerId"));
    }

    @Test
    void userDoesNotOwnsCart_throwsException() {
        Cart mockCart = Mockito.mock(Cart.class);
        Mockito.when(mockCart.getOwnerId()).thenReturn("notOwnerId");
        Mockito.when(cartStorage.getById("cartId")).thenReturn(Optional.of(mockCart));

        assertThrows(UserNotOwnsCartException.class, () -> assertUserOwnsCart.run("cartId","ownerId"));
    }
}
