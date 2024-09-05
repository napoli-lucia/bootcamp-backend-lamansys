package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.MultipleExceptions;
import ar.lamansys.messages.application.cart.exception.ProductInvalidSeller;
import ar.lamansys.messages.application.product.AssertProductExists;
import ar.lamansys.messages.application.product.exception.ProductNotExistsException;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.addedproduct.NewProductBo;
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
    private AssertUserExists assertUserExists;
    @Mock
    private AssertProductExists assertProductExists;
    @Mock
    private AssertProductSameSeller assertProductSameSeller;

    @InjectMocks
    private CreateCart createCart;


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
        String productId = "product1";
        List<NewProductBo> newProducts = Collections.singletonList(new NewProductBo(productId, 1));

        doNothing().when(assertUserExists).run(ownerId);
        doNothing().when(assertUserExists).run(sellerId);
        doNothing().when(assertProductExists).run(productId);
        doThrow(new ProductInvalidSeller(productId)).when(assertProductSameSeller).run(sellerId, productId);

        MultipleExceptions thrown = assertThrows(MultipleExceptions.class, () -> {
            createCart.run(ownerId, sellerId, newProducts);
        });

        assertEquals("Product product1 has an invalid seller", thrown.getExceptions().get(0).getMessage());
    }

}
