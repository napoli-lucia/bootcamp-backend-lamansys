package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.*;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.addedproduct.AddedProductBo;
import ar.lamansys.messages.domain.addedproduct.NewProductBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateNewProductInCartTest {

    @Mock
    private AddedProductStorage addedProduct;
    @Mock
    private AssertUserExists assertUserExists;
    @Mock
    private AssertCartExists assertCartExists;
    @Mock
    private AssertUserOwnsCart assertUserOwnsCart;
    @Mock
    private AssertProductExists assertProductExists;
    @Mock
    private AssertProductValidSeller assertProductValidSeller;
    @Mock
    private AssertProductStockEnough assertProductStockEnough;

    @InjectMocks
    private CreateNewProductInCart createNewProductInCart;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and set up any common test data if necessary
    }

    @Test
    public void testUserNotExistsException() throws Exception {
        //Verifica que se lance UserNotExistsException cuando el usuario no existe.
        String ownerId = "owner1";
        String cartId = "cart1";
        NewProductBo newProduct = new NewProductBo("product1", 1);

        doThrow(new UserNotExistsException(ownerId)).when(assertUserExists).run(ownerId);

        UserNotExistsException thrown = assertThrows(UserNotExistsException.class, () ->
                createNewProductInCart.run(ownerId, cartId, newProduct));

        assertEquals(ownerId, thrown.getUserId());
    }

    @Test
    public void testCartNotExistsException() throws Exception {
        String ownerId = "owner1";
        String cartId = "cart1";
        NewProductBo newProduct = new NewProductBo("product1", 1);

        doNothing().when(assertUserExists).run(ownerId);
        doThrow(new CartNotExistsException(cartId)).when(assertCartExists).run(cartId);

        CartNotExistsException thrown = assertThrows(CartNotExistsException.class, () ->
                createNewProductInCart.run(ownerId, cartId, newProduct));

        assertEquals(cartId, thrown.getCartId());
    }

    @Test
    public void testUserNotOwnsCartException() throws Exception {
        String ownerId = "owner1";
        String cartId = "cart1";
        NewProductBo newProduct = new NewProductBo("product1", 1);

        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doThrow(new UserNotOwnsCartException(cartId, ownerId)).when(assertUserOwnsCart).run(cartId, ownerId);

        UserNotOwnsCartException thrown = assertThrows(UserNotOwnsCartException.class, () ->
                createNewProductInCart.run(ownerId, cartId, newProduct));

        assertEquals(cartId, thrown.getCartId());
        assertEquals(ownerId, thrown.getOwnerId());
    }

    @Test
    public void testProductNotExistsException() throws Exception {
        String ownerId = "owner1";
        String cartId = "cart1";
        NewProductBo newProduct = new NewProductBo("product1", 1);

        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doNothing().when(assertUserOwnsCart).run(cartId, ownerId);
        doThrow(new ProductNotExistsException("product1")).when(assertProductExists).run("product1");

        ProductNotExistsException thrown = assertThrows(ProductNotExistsException.class, () ->
                createNewProductInCart.run(ownerId, cartId, newProduct));

        assertEquals("product1", thrown.getProductId());
    }

    @Test
    public void testProductInvalidSellerException() throws Exception {
        String ownerId = "owner1";
        String cartId = "cart1";
        NewProductBo newProduct = new NewProductBo("product1", 1);

        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doNothing().when(assertUserOwnsCart).run(cartId, ownerId);
        doNothing().when(assertProductExists).run("product1");
        doThrow(new ProductInvalidSeller("product1")).when(assertProductValidSeller).run(cartId, "product1");

        ProductInvalidSeller thrown = assertThrows(ProductInvalidSeller.class, () ->
                createNewProductInCart.run(ownerId, cartId, newProduct));

        assertEquals("product1", thrown.getProductId());
    }

    @Test
    public void testProductStockNotEnoughException() throws Exception {
        String ownerId = "owner1";
        String cartId = "cart1";
        NewProductBo newProduct = new NewProductBo("product1", 10);

        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doNothing().when(assertUserOwnsCart).run(cartId, ownerId);
        doNothing().when(assertProductExists).run("product1");
        doNothing().when(assertProductValidSeller).run(cartId, "product1");
        doThrow(new ProductStockNotEnough("product1")).when(assertProductStockEnough).run("product1", 10);

        ProductStockNotEnough thrown = assertThrows(ProductStockNotEnough.class, () ->
                createNewProductInCart.run(ownerId, cartId, newProduct));

        assertEquals("product1", thrown.getProductId());
    }

    @Test
    public void testSuccessfulProductAddition() throws Exception {
        String ownerId = "owner1";
        String cartId = "cart1";
        String productId = "product1";
        int quantity = 1;
        NewProductBo newProduct = new NewProductBo(productId, quantity);

        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doNothing().when(assertUserOwnsCart).run(cartId, ownerId);
        doNothing().when(assertProductExists).run(productId);
        doNothing().when(assertProductValidSeller).run(cartId, productId);
        doNothing().when(assertProductStockEnough).run(productId, quantity);

        createNewProductInCart.run(ownerId, cartId, newProduct);

        verify(addedProduct).save(new AddedProductBo(cartId, productId, quantity)); // Verifica que se haya guardado el producto
    }
}
