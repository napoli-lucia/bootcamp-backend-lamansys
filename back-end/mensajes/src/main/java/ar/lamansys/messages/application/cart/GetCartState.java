package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.domain.cart.CartStateBo;
import ar.lamansys.messages.domain.product.ProductStateBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.AddedProduct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class GetCartState {

    private final AddedProductStorage addedProduct;
    private final ProductStorage productStorage;

    public CartStateBo run(String ownerId, String cartId) {

        Float totalPrice = 0.0f;
        List<String> leftOut = new ArrayList<>();
        List<ProductStateBo> generalInfo = new ArrayList<>();

        // Lanzan error
        //TODO: Verificar que el carrito exista
        //TODO: Verificar que el carrito sea del ownerId

        List<AddedProduct> products = addedProduct.findAllByCartId(cartId);


        //Verifica que el stock sea suficiente. Sino agrega en leftOut
        for (AddedProduct product : products) {
            if(productStorage.getStockByProductId(product.getAddedProductId().getProductId()) <= product.getQuantity()) {
                leftOut.add(product.getAddedProductId().getProductId());
            }
            generalInfo.add(new ProductStateBo(product.getAddedProductId().getProductId(), productStorage.getUnityPriceByProductId(product.getAddedProductId().getProductId())));
            totalPrice += productStorage.getUnityPriceByProductId(product.getAddedProductId().getProductId()) * product.getQuantity();

        }

        return new CartStateBo(totalPrice, leftOut, generalInfo);
    }
}
