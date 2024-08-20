package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.domain.addedproduct.AddedProductBo;
import ar.lamansys.messages.domain.addedproduct.NewProductBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EditProductInCart {

    private final AddedProductStorage addedProduct;

    public void run(String ownerId, String cartId, NewProductBo editedProduct) {

        //TODO: Verificar que el carrito exista
        //TODO: Verificar que el carrito sea del ownerId
        //TODO: Verificar que el producto exista en el carrito
        //TODO: Verificar que el stock sea suficiente

        addedProduct.save(new AddedProductBo(cartId, editedProduct.getProductId(), editedProduct.getQuantity()));

    }
}
