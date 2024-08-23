package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.MultipleExceptions;
import ar.lamansys.messages.application.cart.exception.ProductInvalidSeller;
import ar.lamansys.messages.application.cart.exception.ProductNotExistsException;
import ar.lamansys.messages.application.cart.exception.ProductStockNotEnough;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.addedproduct.AddedProductBo;
import ar.lamansys.messages.domain.addedproduct.NewProductBo;
import ar.lamansys.messages.domain.cart.CartCreationBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCartTest {

    @Mock
    private ProductStorage productStorage;
    @Mock
    private AddedProductStorage addedProduct;
    @Mock
    private CartStorage cartStorage;
    @Mock
    private AssertUserExists assertUserExists;
    @Mock
    private AssertProductStockEnough assertProductStockEnough;
    @Mock
    private AssertProductExists assertProductExists;
    @Mock
    private AssertProductSameSeller assertProductSameSeller;

    @InjectMocks
    private CreateCart createCart;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and set up any common test data if necessary
    }

    @Test
    public void testUserNotExistsException() throws UserNotExistsException {
        String ownerId = "owner1";
        String sellerId = "seller1";
        List<NewProductBo> newProducts = Collections.emptyList();

        doThrow(new UserNotExistsException(ownerId)).when(assertUserExists).run(ownerId);

        UserNotExistsException thrown = assertThrows(UserNotExistsException.class, () ->
                createCart.run(ownerId, sellerId, newProducts));

        assertEquals(ownerId, thrown.getUserId());
    }

    @Test
    public void testProductNotExistsException() throws UserNotExistsException, ProductNotExistsException {
        String ownerId = "owner1";
        String sellerId = "seller1";
        List<NewProductBo> newProducts = Collections.singletonList(new NewProductBo("product1", 1));

        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertUserExists).run(sellerId);
        doThrow(new ProductNotExistsException("product1")).when(assertProductExists).run("product1");

        MultipleExceptions thrown = assertThrows(MultipleExceptions.class, () -> {
            createCart.run(ownerId, sellerId, newProducts);
        });

        assertTrue(thrown.getExceptions().stream().anyMatch(ex -> ex instanceof ProductNotExistsException));
    }



    @Test
    public void testProductInvalidSellerException() throws Exception {
        String ownerId = "owner1";
        String sellerId = "seller1";
        List<NewProductBo> newProducts = Collections.singletonList(new NewProductBo("product1", 1));

        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertUserExists).run(sellerId);
        doNothing().when(assertProductExists).run("product1");
        when(productStorage.getOwnerByProductId("product1")).thenReturn("wrongSellerId");
        doThrow(new ProductInvalidSeller("product1")).when(assertProductSameSeller).run(sellerId, "product1");

        MultipleExceptions thrown = assertThrows(MultipleExceptions.class, () -> {
            createCart.run(ownerId, sellerId, newProducts);
        });

        // Verificamos que la excepción lanzada contiene la excepción ProductInvalidSeller
        assertTrue(thrown.getExceptions().stream().anyMatch(ex -> ex instanceof ProductInvalidSeller));
    }

    @Test
    public void testSuccessfulCartCreation() throws Exception {
        String ownerId = "owner1";
        String sellerId = "seller1";
        String productId = "product1";
        int quantity = 1;
        List<NewProductBo> newProducts = Collections.singletonList(new NewProductBo(productId, quantity));

        // Configuración de mocks
        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertUserExists).run(sellerId);
        doNothing().when(assertProductExists).run(productId);
        when(productStorage.getStockByProductId(productId)).thenReturn(100); // Stock suficiente
        when(productStorage.getOwnerByProductId(productId)).thenReturn(sellerId); // El vendedor es correcto

        // Ejecutar el método bajo prueba
        CartCreationBo result = createCart.run(ownerId, sellerId, newProducts);

        verify(addedProduct).save(any(AddedProductBo.class)); // Verifica que se haya guardado un producto en el carrito

        // Verificar el resultado
        assertNotNull(result.getCartId()); // El ID del carrito no debe ser nulo
        assertEquals(1, result.getAddedProducts().size()); // Debe haber un producto disponible en el carrito
        assertTrue(result.getExceptions().isEmpty()); // No debe haber mensajes de excepción
    }

}
