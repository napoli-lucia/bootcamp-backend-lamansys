package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.CartNotExistsException;
import ar.lamansys.messages.application.cart.exception.UserNotOwnsCartException;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAllProductsInCartTest {

    @Mock
    private AddedProductStorage addedProductStorage;
    @Mock
    private AssertUserExists assertUserExists;
    @Mock
    private AssertCartExists assertCartExists;
    @Mock
    private AssertUserOwnsCart assertUserOwnsCart;

    @InjectMocks
    private DeleteAllProductsInCart deleteAllProductsInCart;

    @Test
    public void testUserNotExistsException() throws UserNotExistsException {

        doThrow(new UserNotExistsException("owner1")).when(assertUserExists).run("owner1");

        UserNotExistsException thrown = assertThrows(UserNotExistsException.class, () ->
                deleteAllProductsInCart.run("owner1", "cart1"));

        assertEquals("owner1", thrown.getUserId());
    }

    @Test
    public void testCartNotExistsException() throws CartNotExistsException, UserNotExistsException {

        doNothing().when(assertUserExists).run("owner1");
        doThrow(new CartNotExistsException("cart1")).when(assertCartExists).run("cart1");

        CartNotExistsException thrown = assertThrows(CartNotExistsException.class, () ->
                deleteAllProductsInCart.run("owner1", "cart1"));

        assertEquals("cart1", thrown.getCartId());
    }

    @Test
    public void testUserNotOwnsCartException() throws Exception {
        String ownerId = "owner1";
        String cartId = "cart1";

        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doThrow(new UserNotOwnsCartException(cartId, ownerId)).when(assertUserOwnsCart).run(cartId, ownerId);

        UserNotOwnsCartException thrown = assertThrows(UserNotOwnsCartException.class, () ->
                deleteAllProductsInCart.run(ownerId, cartId));

        assertEquals(cartId, thrown.getCartId());
        assertEquals(ownerId, thrown.getOwnerId());
    }

    @Test
    public void testSuccessfulAllProductsDeletion() throws Exception {
        String ownerId = "owner1";
        String cartId = "cart1";

        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doNothing().when(assertUserOwnsCart).run(cartId, ownerId);

        deleteAllProductsInCart.run(ownerId, cartId);

        verify(addedProductStorage).deleteAllByCartId(cartId);
    }
}
