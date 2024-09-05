package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.product.AssertProductExists;
import ar.lamansys.messages.application.product.exception.ProductNotExistsException;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertProductExistsTest {

    private ProductStorage productStorage = Mockito.mock(ProductStorage.class);

    private AssertProductExists assertProductExists = new AssertProductExists(productStorage);

    @Test
    void productExistence_ok() {
        Mockito.when(productStorage.exists("productId")).thenReturn(true);
        assertDoesNotThrow(() -> assertProductExists.run("productId"));
    }

    @Test
    void productNotExistence_throwsException() {
        Mockito.when(productStorage.exists("productId")).thenReturn(false);
        assertThrows(ProductNotExistsException.class, () -> assertProductExists.run("productId"));
    }
}
