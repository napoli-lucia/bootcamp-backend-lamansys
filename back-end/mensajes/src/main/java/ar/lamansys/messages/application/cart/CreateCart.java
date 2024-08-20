package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.domain.addedproduct.AddedProductBo;
import ar.lamansys.messages.domain.cart.CartBo;
import ar.lamansys.messages.domain.addedproduct.NewProductBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CreateCart {

    private final ProductStorage productStorage;
    private final AddedProductStorage addedProduct;
    private final CartStorage cartStorage;

    public void run(String ownerId, String sellerId, List<NewProductBo> newProducts) {

        String cartId = UUID.randomUUID().toString();

        cartStorage.save(new CartBo(cartId, ownerId, sellerId));

        for (NewProductBo newProduct : newProducts) {
            addedProduct.save(new AddedProductBo(cartId, newProduct.getProductId(), newProduct.getQuantity()));
        }

    }
}
