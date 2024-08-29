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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CreateCart {

    private final AddedProductStorage addedProduct;
    private final CartStorage cartStorage;
    private final AssertUserExists assertUserExists;
    private final AssertProductStockEnough assertProductStockEnough;
    private final AssertProductExists assertProductExists;
    private final AssertProductSameSeller assertProductSameSeller;

    public CartCreationBo run(String ownerId, String sellerId, List<NewProductBo> newProducts) throws MultipleExceptions, UserNotExistsException {

        assertUserExists.run(ownerId);
        assertUserExists.run(sellerId);

        List<Exception> exceptions = new ArrayList<>();
        List<NewProductBo> availableProducts = new ArrayList<>();

        for (NewProductBo newProduct : newProducts) {

            try {
                assertProductExists.run(newProduct.getProductId());
            } catch (ProductNotExistsException e) {
                exceptions.add(e);
                continue;
            }

            try {
                assertProductSameSeller.run(sellerId, newProduct.getProductId());
            } catch (ProductInvalidSeller e) {
                exceptions.add(e);
                continue;
            }

            try {
                assertProductStockEnough.run(newProduct.getProductId(), newProduct.getQuantity());
                availableProducts.add(newProduct); //Si cumple se agrega a la lista de productos disponibles
            } catch (ProductStockNotEnough e) {
                exceptions.add(e);
            }
        }

        if (!exceptions.isEmpty() && availableProducts.isEmpty()){
            throw new MultipleExceptions(exceptions);
        }

        //Creo carrito y agrego productos disponibles
        String cartId = UUID.randomUUID().toString();
        cartStorage.save(new CartBo(cartId, ownerId, sellerId));

        for (NewProductBo newProduct : availableProducts) {
            addedProduct.save(new AddedProductBo(cartId, newProduct.getProductId(), newProduct.getQuantity()));
        }

        List<String> exceptionMessages = exceptions.stream()
                .map(Throwable::getLocalizedMessage)
                .collect(Collectors.toList());

        return new CartCreationBo(cartId, availableProducts, exceptionMessages);
    }
}