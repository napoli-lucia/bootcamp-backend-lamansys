package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.domain.addedproduct.AddedProductBo;
import ar.lamansys.messages.domain.addedproduct.NewProductBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreateNewProductInCart {

    private final AddedProductStorage addedProduct;
    //private final CartStorage cartStorage;

    public void run(String ownerId, String cartId, NewProductBo newProduct) {

        //TODO: Verificar que el producto exista
        //TODO: Verificar que el producto sea del mismo seller
        //TODO: Verificar que el stock sea suficiente
        //TODO: Verificar que el carrito exista
        //TODO: Verificar que el carrito sea del ownerId

        addedProduct.save(new AddedProductBo(cartId, newProduct.getProductId(), newProduct.getQuantity()));

    }
}
