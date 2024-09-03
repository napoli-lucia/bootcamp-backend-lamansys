package ar.lamansys.messages.application.product;

import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.product.PostProductBo;
import ar.lamansys.messages.domain.product.ProductBo;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddProductTest {

    @Mock
    private AssertUserExists assertUserExists;

    @Mock
    private ProductStorage productStorage;

    @InjectMocks
    private AddProduct addProduct;

    private PostProductBo newProduct;

    @BeforeEach
    public void setUp() {
        newProduct = new PostProductBo("owner1", "Product Name", 10, 100.0f);
    }

    @Test
    public void testAddProductSuccessfully() throws UserNotExistsException {
        // Mocking
        doNothing().when(assertUserExists).run(newProduct.getOwnerId());

        // Execute
        addProduct.run(newProduct);

        // Verify
        verify(assertUserExists, times(1)).run(newProduct.getOwnerId());
        verify(productStorage, times(1)).save(any(ProductBo.class));
    }

    @Test
    public void testUserNotExistsException() throws UserNotExistsException {
        // Mocking
        doThrow(new UserNotExistsException(newProduct.getOwnerId())).when(assertUserExists).run(newProduct.getOwnerId());

        // Execute & Verify
        UserNotExistsException thrown = assertThrows(UserNotExistsException.class, () ->
                addProduct.run(newProduct));

        assertEquals(newProduct.getOwnerId(), thrown.getUserId());

        // Verify that product is not saved
        verify(productStorage, never()).save(any(ProductBo.class));
    }
}

