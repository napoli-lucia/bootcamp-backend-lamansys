package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.CartNotExistsException;
import ar.lamansys.messages.application.cart.exception.UserNotOwnsCartException;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.addedproduct.AddedProductBo;
import ar.lamansys.messages.domain.cart.CartStateBo;
import ar.lamansys.messages.domain.product.ProductStateBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.AddedProduct;
import ar.lamansys.messages.infrastructure.output.entity.AddedProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetCartStateTest {

    @Mock
    private AddedProductStorage addedProductStorage;

    @Mock
    private ProductStorage productStorage;

    @Mock
    private AssertUserExists assertUserExists;

    @Mock
    private AssertCartExists assertCartExists;

    @Mock
    private AssertUserOwnsCart assertUserOwnsCart;

    @InjectMocks
    private GetCartState getCartState;

    private String ownerId;
    private String cartId;

    @BeforeEach
    public void setUp() {
        ownerId = "owner1";
        cartId = "cart1";
    }

    @Test
    public void testUserNotExistsException() throws UserNotExistsException {
        doThrow(new UserNotExistsException(ownerId)).when(assertUserExists).run(ownerId);

        UserNotExistsException thrown = assertThrows(UserNotExistsException.class, () ->
                getCartState.run(ownerId, cartId));

        assertEquals(ownerId, thrown.getUserId());
    }

    @Test
    public void testCartNotExistsException() throws CartNotExistsException, UserNotExistsException {
        doNothing().when(assertUserExists).run(ownerId);
        doThrow(new CartNotExistsException(cartId)).when(assertCartExists).run(cartId);

        CartNotExistsException thrown = assertThrows(CartNotExistsException.class, () ->
                getCartState.run(ownerId, cartId));

        assertEquals(cartId, thrown.getCartId());
    }

    @Test
    public void testUserNotOwnsCartException() throws UserNotOwnsCartException, UserNotExistsException, CartNotExistsException {
        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doThrow(new UserNotOwnsCartException(cartId, ownerId)).when(assertUserOwnsCart).run(cartId, ownerId);

        UserNotOwnsCartException thrown = assertThrows(UserNotOwnsCartException.class, () ->
                getCartState.run(ownerId, cartId));

        assertEquals(cartId, thrown.getCartId());
        assertEquals(ownerId, thrown.getOwnerId());
    }

    @Test
    public void testGetCartStateWithSufficientStock() throws Exception {
        // Crear instancias de AddedProductBo
        AddedProductBo addedProductBo1 = new AddedProductBo(cartId, "product1", 2);
        AddedProductBo addedProductBo2 = new AddedProductBo(cartId, "product2", 3);

        // Crear instancias de AddedProduct utilizando AddedProductBo
        AddedProduct product1 = new AddedProduct(addedProductBo1);
        AddedProduct product2 = new AddedProduct(addedProductBo2);

        // Configuración de los mocks
        when(addedProductStorage.findAllByCartId(cartId)).thenReturn(Arrays.asList(product1, product2));
        when(productStorage.getStockByProductId("product1")).thenReturn(5);
        when(productStorage.getStockByProductId("product2")).thenReturn(4);
        when(productStorage.getUnityPriceByProductId("product1")).thenReturn(10.0f);
        when(productStorage.getUnityPriceByProductId("product2")).thenReturn(15.0f);

        // Ejecutar el método bajo prueba
        CartStateBo result = getCartState.run(ownerId, cartId);

        // Verificaciones
        assertEquals(65.0f, result.getTotalPrice());  // Total = 2 * 10.0f + 3 * 15.0f
        assertTrue(result.getLeftOut().isEmpty());
        assertEquals(2, result.getProducts().size());
        assertEquals(new ProductStateBo("product1", 10.0f), result.getProducts().get(0));
        assertEquals(new ProductStateBo("product2", 15.0f), result.getProducts().get(1));
    }


    @Test
    public void testGetCartStateWithInsufficientStock() throws Exception {
        // Crear instancias de AddedProductBo
        AddedProductBo addedProductBo1 = new AddedProductBo(cartId, "product1", 2);
        AddedProductBo addedProductBo2 = new AddedProductBo(cartId, "product2", 3);

        // Crear instancias de AddedProduct utilizando AddedProductBo
        AddedProduct product1 = new AddedProduct(addedProductBo1);
        AddedProduct product2 = new AddedProduct(addedProductBo2);

        when(addedProductStorage.findAllByCartId(cartId)).thenReturn(Arrays.asList(product1, product2));
        when(productStorage.getStockByProductId("product1")).thenReturn(1); // Insufficient stock
        when(productStorage.getStockByProductId("product2")).thenReturn(4);
        when(productStorage.getUnityPriceByProductId("product1")).thenReturn(10.0f);
        when(productStorage.getUnityPriceByProductId("product2")).thenReturn(15.0f);

        CartStateBo result = getCartState.run(ownerId, cartId);

        assertEquals(65.0f, result.getTotalPrice());
        assertEquals(Collections.singletonList("product1"), result.getLeftOut());
        assertEquals(2, result.getProducts().size());
        assertEquals(new ProductStateBo("product1", 10.0f), result.getProducts().get(0));
        assertEquals(new ProductStateBo("product2", 15.0f), result.getProducts().get(1));
    }

    @Test
    public void testGetCartStateWithEmptyCart() throws Exception {
        when(addedProductStorage.findAllByCartId(cartId)).thenReturn(Collections.emptyList());

        CartStateBo result = getCartState.run(ownerId, cartId);

        assertEquals(0.0f, result.getTotalPrice());
        assertTrue(result.getLeftOut().isEmpty());
        assertTrue(result.getProducts().isEmpty());
    }
}
