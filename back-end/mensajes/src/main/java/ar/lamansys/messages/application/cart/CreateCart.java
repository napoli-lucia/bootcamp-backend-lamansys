package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.MultipleExceptions;
import ar.lamansys.messages.application.cart.exception.ProductInvalidSeller;
import ar.lamansys.messages.application.cart.exception.ProductNotExistsException;
import ar.lamansys.messages.application.cart.exception.ProductStockNotEnough;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.addedproduct.AddedProductBo;
import ar.lamansys.messages.domain.cart.CartBo;
import ar.lamansys.messages.domain.addedproduct.NewProductBo;
import ar.lamansys.messages.domain.cart.CartCreationBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CreateCart {

    private final ProductStorage productStorage;
    private final AddedProductStorage addedProduct;
    private final CartStorage cartStorage;
    private final AssertUserExists assertUserExists;
    private final AssertProductStockEnough assertProductStockEnough;
    private final AssertProductExists assertProductExists;
    private final AssertProductSameSeller assertProductSameSeller;

    public CartCreationBo run(String ownerId, String sellerId, List<NewProductBo> newProducts) throws MultipleExceptions, UserNotExistsException {

        List<Exception> exceptions = new ArrayList<>();

        assertUserExists.run(ownerId);
        assertUserExists.run(sellerId);

        List<NewProductBo> availableProducts = new ArrayList<>();

        for (NewProductBo newProduct : newProducts) {

            try {
                assertProductExists.run(newProduct.getProductId());
            } catch (ProductNotExistsException e) {
                exceptions.add(e);
            }

            try {
                if(productStorage.exists(newProduct.getProductId())) assertProductSameSeller.run(sellerId, newProduct.getProductId());
            } catch (ProductInvalidSeller e) {
                exceptions.add(e);
            }

            try {
                Optional<Product> product = productStorage.getProductByProductId(newProduct.getProductId());
                if(product.isPresent()){
                    assertProductStockEnough.run(newProduct.getProductId(), newProduct.getQuantity());
                    availableProducts.add(newProduct);
                }

            } catch (ProductStockNotEnough e) {
                exceptions.add(e);
            }
        }

        //Agrego productos disponibles
        String cartId = UUID.randomUUID().toString();
        cartStorage.save(new CartBo(cartId, ownerId, sellerId));

        for (NewProductBo newProduct : availableProducts) {
            addedProduct.save(new AddedProductBo(cartId, newProduct.getProductId(), newProduct.getQuantity()));
        }

        if (!exceptions.isEmpty() && availableProducts.isEmpty()){
            throw new MultipleExceptions(exceptions);
        }

        List<String> exceptionMessages = exceptions.stream()
                .map(Throwable::getLocalizedMessage)
                .collect(Collectors.toList());

        if (!exceptions.isEmpty()){
            return new CartCreationBo(cartId, availableProducts, exceptionMessages);
        }

        return new CartCreationBo(cartId, availableProducts, Collections.emptyList());

    }
}