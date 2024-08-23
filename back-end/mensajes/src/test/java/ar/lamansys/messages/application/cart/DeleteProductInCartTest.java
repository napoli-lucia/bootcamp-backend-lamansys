package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.CartNotExistsException;
import ar.lamansys.messages.application.cart.exception.ProductNotExistsInCartException;
import ar.lamansys.messages.application.cart.exception.UserNotOwnsCartException;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.AddedProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteProductInCartTest {

    @Mock
    private AddedProductStorage addedProductStorage;
    @Mock
    private AssertUserExists assertUserExists;
    @Mock
    private AssertCartExists assertCartExists;
    @Mock
    private AssertUserOwnsCart assertUserOwnsCart;
    @Mock
    private AssertProductExistsInCart assertProductExistsInCart;

    @InjectMocks
    private DeleteProductInCart deleteProductInCart;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and set up any common test data if necessary
    }

    @Test
    public void testUserNotExistsException() throws UserNotExistsException {
        String ownerId = "owner1";
        String cartId = "cart1";
        String productId = "product1";

        doThrow(new UserNotExistsException(ownerId)).when(assertUserExists).run(ownerId);

        UserNotExistsException thrown = assertThrows(UserNotExistsException.class, () ->
                deleteProductInCart.run(ownerId, cartId, productId));

        assertEquals(ownerId, thrown.getUserId());
    }

    @Test
    public void testCartNotExistsException() throws Exception {
        String ownerId = "owner1";
        String cartId = "cart1";
        String productId = "product1";

        doNothing().when(assertUserExists).run(ownerId);
        doThrow(new CartNotExistsException(cartId)).when(assertCartExists).run(cartId);

        CartNotExistsException thrown = assertThrows(CartNotExistsException.class, () ->
                deleteProductInCart.run(ownerId, cartId, productId));

        assertEquals(cartId, thrown.getCartId());
    }

    @Test
    public void testUserNotOwnsCartException() throws Exception {
        String ownerId = "owner1";
        String cartId = "cart1";
        String productId = "product1";

        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doThrow(new UserNotOwnsCartException(cartId, ownerId)).when(assertUserOwnsCart).run(cartId, ownerId);

        UserNotOwnsCartException thrown = assertThrows(UserNotOwnsCartException.class, () ->
                deleteProductInCart.run(ownerId, cartId, productId));

        assertEquals(cartId, thrown.getCartId());
        assertEquals(ownerId, thrown.getOwnerId());
    }

    @Test
    public void testProductNotExistsInCartException() throws Exception {
        String ownerId = "owner1";
        String cartId = "cart1";
        String productId = "product1";

        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doNothing().when(assertUserOwnsCart).run(cartId, ownerId);
        doThrow(new ProductNotExistsInCartException(productId)).when(assertProductExistsInCart).run(cartId, productId);

        ProductNotExistsInCartException thrown = assertThrows(ProductNotExistsInCartException.class, () ->
                deleteProductInCart.run(ownerId, cartId, productId));

        assertEquals(productId, thrown.getProductId());
    }

    @Test
    public void testSuccessfulProductDeletion() throws Exception {
        String ownerId = "owner1";
        String cartId = "cart1";
        String productId = "product1";

        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doNothing().when(assertUserOwnsCart).run(cartId, ownerId);
        doNothing().when(assertProductExistsInCart).run(cartId, productId);

        deleteProductInCart.run(ownerId, cartId, productId);

        verify(addedProductStorage).delete(new AddedProductId(cartId, productId));
    }
}
