package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.CartNotExistsException;
import ar.lamansys.messages.application.cart.exception.MultipleExceptions;
import ar.lamansys.messages.application.cart.exception.ProductStockNotEnough;
import ar.lamansys.messages.application.cart.exception.UserNotOwnsCartException;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.addedproduct.AddedProductBo;
import ar.lamansys.messages.domain.cart.CartStateBo;
import ar.lamansys.messages.domain.product.ProductStateBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.AddedProduct;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseCartTest {

    @Mock
    private AddedProductStorage addedProductStorage;
    @Mock
    private ProductStorage productStorage;
    @Mock
    private CartStorage cartStorage;
    @Mock
    private AssertUserExists assertUserExists;
    @Mock
    private AssertCartExists assertCartExists;
    @Mock
    private AssertUserOwnsCart assertUserOwnsCart;
    @Mock
    private AssertProductStockEnough assertProductStockEnough;

    @InjectMocks
    private PurchaseCart purchaseCart;

    private String ownerId;
    private String cartId;
    private AddedProduct product1;
    private AddedProduct product2;

    @BeforeEach
    public void setUp() {
        ownerId = "owner1";
        cartId = "cart1";
        product1 = new AddedProduct(new AddedProductBo(cartId, "product1", 2));
        product2 = new AddedProduct(new AddedProductBo(cartId, "product2", 3));
    }

    @Test
    public void testSuccessfulPurchase() throws Exception {
        // Configuración de mocks
        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doNothing().when(assertUserOwnsCart).run(cartId, ownerId);

        // Los productos ya están inicializados en el setup
        when(addedProductStorage.findAllByCartId(cartId)).thenReturn(Arrays.asList(product1, product2));
        when(productStorage.getUnityPriceByProductId("product1")).thenReturn(10.0f);
        when(productStorage.getUnityPriceByProductId("product2")).thenReturn(20.0f);

        // Ejecutar el método bajo prueba
        float totalPrice = purchaseCart.run(ownerId, cartId);

        // Verificaciones
        assertEquals(2 * 10.0f + 3 * 20.0f, totalPrice, 0.001); // Agregamos un delta para comparación de floats
        verify(addedProductStorage).deleteAllByCartId(cartId);
        verify(cartStorage).delete(cartId);
        verify(productStorage).updateStock("product1", 2);
        verify(productStorage).updateStock("product2", 3);
    }


    @Test
    public void testProductStockNotEnough() throws Exception {
        // Configuración de mocks
        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doNothing().when(assertUserOwnsCart).run(cartId, ownerId);

        // Configura el comportamiento esperado cuando se detecta un stock insuficiente
        doThrow(new ProductStockNotEnough("product1")).when(assertProductStockEnough).run("product1", 2);

        // Configuración de los datos que se utilizarán
        when(addedProductStorage.findAllByCartId(cartId)).thenReturn(Arrays.asList(product1, product2));
        when(productStorage.getUnityPriceByProductId("product2")).thenReturn(20.0f);

        // Ejecutar el método bajo prueba y capturar la excepción
        MultipleExceptions thrown = assertThrows(MultipleExceptions.class, () ->
                purchaseCart.run(ownerId, cartId));

        // Verificación de las excepciones
        assertEquals(1, thrown.getExceptions().size());
        assertTrue(thrown.getExceptions().get(0) instanceof ProductStockNotEnough);
        assertEquals("product1", ((ProductStockNotEnough) thrown.getExceptions().get(0)).getProductId());
    }




    @Test
    public void testCartNotExistsException() throws Exception {
        // Configuración de mocks
        doNothing().when(assertUserExists).run(ownerId);
        doThrow(new CartNotExistsException(cartId)).when(assertCartExists).run(cartId);

        // Ejecutar el método bajo prueba
        CartNotExistsException thrown = assertThrows(CartNotExistsException.class, () ->
                purchaseCart.run(ownerId, cartId));

        // Verificación de la excepción
        assertEquals(cartId, thrown.getCartId());
    }

    @Test
    public void testUserNotExistsException() throws Exception {
        // Configuración de mocks
        doThrow(new UserNotExistsException(ownerId)).when(assertUserExists).run(ownerId);

        // Ejecutar el método bajo prueba
        UserNotExistsException thrown = assertThrows(UserNotExistsException.class, () ->
                purchaseCart.run(ownerId, cartId));

        // Verificación de la excepción
        assertEquals(ownerId, thrown.getUserId());
    }

    @Test
    public void testUserNotOwnsCartException() throws Exception {
        // Configuración de mocks
        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertCartExists).run(cartId);
        doThrow(new UserNotOwnsCartException(cartId, ownerId)).when(assertUserOwnsCart).run(cartId, ownerId);

        // Ejecutar el método bajo prueba
        UserNotOwnsCartException thrown = assertThrows(UserNotOwnsCartException.class, () ->
                purchaseCart.run(ownerId, cartId));

        // Verificación de la excepción
        assertEquals(cartId, thrown.getCartId());
        assertEquals(ownerId, thrown.getOwnerId());
    }
}
