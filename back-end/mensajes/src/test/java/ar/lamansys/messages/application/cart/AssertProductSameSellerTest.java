package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.ProductInvalidSeller;
import ar.lamansys.messages.application.cart.exception.ProductNotExistsException;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertProductSameSellerTest {

    private ProductStorage productStorage = Mockito.mock(ProductStorage.class);

    private AssertProductSameSeller assertProductSameSeller = new AssertProductSameSeller(productStorage);

    @Test
    void productSameSeller_ok() {
        Mockito.when(productStorage.getOwnerByProductId("productId")).thenReturn("sellerId");
        assertDoesNotThrow(() -> assertProductSameSeller.run("sellerId","productId"));
    }

    @Test
    void productNotSameSeller_throwsException() {
        Mockito.when(productStorage.getOwnerByProductId("productId")).thenReturn("notSameSellerId");
        assertThrows(ProductInvalidSeller.class, () -> assertProductSameSeller.run("sellerId","productId"));
    }
}
