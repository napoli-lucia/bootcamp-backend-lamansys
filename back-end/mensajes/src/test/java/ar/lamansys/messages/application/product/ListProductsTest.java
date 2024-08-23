package ar.lamansys.messages.application.product;

import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.product.ProductBo;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListProductsTest {

    @Mock
    private ProductStorage productStorage;

    @Mock
    private AssertUserExists assertUserExists;

    @InjectMocks
    private ListProducts listProducts;

    private String ownerId;

    @BeforeEach
    public void setUp() {
        ownerId = "owner1";
    }

    @Test
    public void testListProductsForExistingUser() throws UserNotExistsException {
        // Prepare test data
        ProductBo product1 = new ProductBo("product1", ownerId, "Product 1", 10, 100.0f);
        ProductBo product2 = new ProductBo("product2", ownerId, "Product 2", 5, 50.0f);

        // Mocking
        doNothing().when(assertUserExists).run(ownerId);
        when(productStorage.getAllProductsByUserId(ownerId)).thenReturn(Stream.of(product1, product2));

        // Execute
        List<ProductBo> result = listProducts.run(ownerId);

        // Verify
        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
    }

    @Test
    public void testUserNotExistsException() throws UserNotExistsException {
        // Mocking
        doThrow(new UserNotExistsException(ownerId)).when(assertUserExists).run(ownerId);

        // Execute & Verify
        UserNotExistsException thrown = assertThrows(UserNotExistsException.class, () ->
                listProducts.run(ownerId));

        assertEquals(ownerId, thrown.getUserId());
    }

    @Test
    public void testListsProductsReturnsEmptyList() throws UserNotExistsException {
        //Arrange
        doNothing().when(assertUserExists).run(ownerId);
        when(productStorage.getAllProductsByUserId(ownerId)).thenReturn(Stream.empty());

        // Execute
        List<ProductBo> result = listProducts.run(ownerId);

        // Verify
        assertTrue(result.isEmpty());

    }
}
