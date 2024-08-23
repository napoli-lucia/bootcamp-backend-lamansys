package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.CartNotExistsException;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertCartExistsTest {

    private CartStorage cartStorage = Mockito.mock(CartStorage.class);

    private AssertCartExists assertCartExists = new AssertCartExists(cartStorage);

    @Test
    void cartExistence_ok() {
        Mockito.when(cartStorage.exists("cartId")).thenReturn(true);
        assertDoesNotThrow(() -> assertCartExists.run("cartId"));
    }

    @Test
    void cartNotExistence_throwsException() {
        Mockito.when(cartStorage.exists("cartId")).thenReturn(false);
        assertThrows(CartNotExistsException.class, () -> assertCartExists.run("cartId"));
    }
}
