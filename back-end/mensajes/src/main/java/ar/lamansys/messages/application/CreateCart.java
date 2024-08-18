package ar.lamansys.messages.application;

import ar.lamansys.messages.domain.AddedProductBo;
import ar.lamansys.messages.domain.CartBo;
import ar.lamansys.messages.domain.NewProductBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.AddedProduct;
import ar.lamansys.messages.infrastructure.output.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
@Service
public class CreateCart {

    private final ProductStorage productStorage;
    private final AddedProductStorage addedProduct;
    private final CartStorage cartStorage;

    public void run(String ownerId, String sellerId, List<NewProductBo> newProducts) {

        /*
        for (NewProductBo newProduct : newProducts) {
            Optional<Product> product = productStorage.getProductByProductId(newProduct.getProductId());
            if(product.isPresent()) {
                if(!product.get().getOwnerId().equals(sellerId) && product.get().getStock() < newProduct.getQuantity() {
                    //error de que existe pero no cumplio condicion valida
                    System.out.println("Existe el producto pero es invalido");
                } else {
                    // aca deberia devolver un error de que no existe es producto
                    System.out.println("No existe el producto");
                }
            }
        }*/

        // aca creamos el cart

        String cartId = UUID.randomUUID().toString();

        cartStorage.save(new CartBo(cartId, ownerId, sellerId));

        for (NewProductBo newProduct : newProducts) {
            addedProduct.save(new AddedProductBo(cartId, newProduct.getProductId(), newProduct.getQuantity()));
        }

    }
}
