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
//@revision no deberia conocer nada del infrastructure
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


    //@revision Estaria bueno agregar el Transaccional en este metodo que es donde se realiza el save
    public CartCreationBo run(String ownerId, String sellerId, List<NewProductBo> newProducts) throws MultipleExceptions, UserNotExistsException {

        List<Exception> exceptions = new ArrayList<>();

        assertUserExists.run(ownerId);
        assertUserExists.run(sellerId);

        List<NewProductBo> availableProducts = new ArrayList<>();

        //@revision se podria separar la logica en metodos mas pequeños
        //facilitaria la legibilidad

        for (NewProductBo newProduct : newProducts) {

            try {
                assertProductExists.run(newProduct.getProductId());
            } catch (ProductNotExistsException e) {
                exceptions.add(e);
            }

            //ya se chequeo arriba si existe el producto
            try {
                if(productStorage.exists(newProduct.getProductId())) assertProductSameSeller.run(sellerId, newProduct.getProductId());
            } catch (ProductInvalidSeller e) {
                exceptions.add(e);
            }

            //@revision ya se chequeo arriba si existe el producto, entiendo que se puede omitir el if y hacer un .get
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

        //@revision no seria mejor tener un caso de uso para crear el carrito
        // y otro para agregar los productos?

        //Agrego productos disponibles
        String cartId = UUID.randomUUID().toString();
        cartStorage.save(new CartBo(cartId, ownerId, sellerId));

        for (NewProductBo newProduct : availableProducts) {
            addedProduct.save(new AddedProductBo(cartId, newProduct.getProductId(), newProduct.getQuantity()));
        }

        //@revision creo que puede haber excepciones y no estar vacio los productos disponibles, por que no se considera ese caso?
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