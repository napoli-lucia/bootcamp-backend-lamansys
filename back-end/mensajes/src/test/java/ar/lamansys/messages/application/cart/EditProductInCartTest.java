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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditProductInCartTest {

    @Mock
    private AddedProductStorage addedProductStorage;
    @Mock
    private AssertUserExists assertUserExists;
    @Mock
    private AssertCartExists assertCartExists;
    @Mock
    private AssertUserOwnsCart assertUserOwnsCart;
    @Mock
    private AssertProductStockEnough assertProductStockEnough;
    @Mock
    private AssertProductExistsInCart assertProductExistsInCart;

    @InjectMocks
    private EditProductInCart editProductInCart;

    private String ownerId;
    private String cartId;
    private NewProductBo editedProduct;

    @BeforeEach
    public void setUp() {
        ownerId = "owner1";
        cartId = "cart1";
        editedProduct = new NewProductBo("product1", 5);
    }

    @Test
    public void testUserNotExistsException() throws UserNotExistsException {
        doThrow(new UserNotExistsException(ownerId)).when(assertUserExists).run(ownerId);

        UserNotExistsException thrown = assertThrows(UserNotExistsException.class, () ->
                editProductInCart.run(ownerId, cartId, editedProduct));

        assertEquals(ownerId, thrown.getUserId());
    }

    @Test
    public void testCartNotExistsException() throws Exception {
        doNothing().when(assertUserExists).run(ownerId);
        doThrow(new CartNotExistsException(cartId)).when(assertCartExists).run(cartId);

        CartNotExistsException thrown = assertThrows(CartNotExistsException.class, () ->
                editProductInCart.run(ownerId, cartId, editedProduct));

        assertEquals(cartId, thrown.getCartId());
    }

    @Test
    public void testUserNotOwnsCartException() throws Exception {
        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doThrow(new UserNotOwnsCartException(cartId, ownerId)).when(assertUserOwnsCart).run(cartId, ownerId);

        UserNotOwnsCartException thrown = assertThrows(UserNotOwnsCartException.class, () ->
                editProductInCart.run(ownerId, cartId, editedProduct));

        assertEquals(cartId, thrown.getCartId());
        assertEquals(ownerId, thrown.getOwnerId());
    }

    @Test
    public void testProductNotExistsInCartException() throws Exception {
        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doNothing().when(assertUserOwnsCart).run(cartId, ownerId);
        doThrow(new ProductNotExistsInCartException(editedProduct.getProductId())).when(assertProductExistsInCart).run(cartId, editedProduct.getProductId());

        ProductNotExistsInCartException thrown = assertThrows(ProductNotExistsInCartException.class, () ->
                editProductInCart.run(ownerId, cartId, editedProduct));

        assertEquals(editedProduct.getProductId(), thrown.getProductId());
    }

    @Test
    public void testProductStockNotEnoughException() throws Exception {
        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doNothing().when(assertUserOwnsCart).run(cartId, ownerId);
        doNothing().when(assertProductExistsInCart).run(cartId, editedProduct.getProductId());
        doThrow(new ProductStockNotEnough(editedProduct.getProductId())).when(assertProductStockEnough).run(editedProduct.getProductId(), editedProduct.getQuantity());

        ProductStockNotEnough thrown = assertThrows(ProductStockNotEnough.class, () ->
                editProductInCart.run(ownerId, cartId, editedProduct));

        assertEquals(editedProduct.getProductId(), thrown.getProductId());
    }

    @Test
    public void testSuccessfulProductEdition() throws Exception {
        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doNothing().when(assertUserOwnsCart).run(cartId, ownerId);
        doNothing().when(assertProductExistsInCart).run(cartId, editedProduct.getProductId());
        doNothing().when(assertProductStockEnough).run(editedProduct.getProductId(), editedProduct.getQuantity());

        editProductInCart.run(ownerId, cartId, editedProduct);

        verify(addedProductStorage).save(new AddedProductBo(cartId, editedProduct.getProductId(), editedProduct.getQuantity()));
    }
}
