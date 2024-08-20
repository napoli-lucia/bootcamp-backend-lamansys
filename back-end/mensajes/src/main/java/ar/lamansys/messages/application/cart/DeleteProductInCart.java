package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.AddedProductId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeleteProductInCart {

    private final AddedProductStorage addedProduct;

    public void run(String ownerId, String cartId, String productId) {
        //TODO: Verificar que el carrito exista
        //TODO: Verificar que el carrito sea del ownerId
        //TODO: Verificar que el producto exista en el carrito

        addedProduct.delete(new AddedProductId(cartId, productId));
    }

}
