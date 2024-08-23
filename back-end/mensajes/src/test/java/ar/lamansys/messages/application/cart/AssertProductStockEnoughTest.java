package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.ProductInvalidSeller;
import ar.lamansys.messages.application.cart.exception.ProductStockNotEnough;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertProductStockEnoughTest {

    private ProductStorage productStorage = Mockito.mock(ProductStorage.class);

    private AssertProductStockEnough assertProductStockEnough = new AssertProductStockEnough(productStorage);

    @Test
    void productStockEnough_ok() {
        Mockito.when(productStorage.getStockByProductId("productId")).thenReturn(2);
        assertDoesNotThrow(() -> assertProductStockEnough.run("productId",2));
    }

    @Test
    void productStockNotEnough_throwsException() {
        Mockito.when(productStorage.getStockByProductId("productId")).thenReturn(0);
        assertThrows(ProductStockNotEnough.class, () -> assertProductStockEnough.run("productId",2));
    }
}
