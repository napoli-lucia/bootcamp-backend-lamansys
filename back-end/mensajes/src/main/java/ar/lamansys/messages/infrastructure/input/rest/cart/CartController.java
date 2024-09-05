package ar.lamansys.messages.infrastructure.input.rest.cart;

import ar.lamansys.messages.application.cart.*;
import ar.lamansys.messages.application.cart.exception.*;
import ar.lamansys.messages.application.product.exception.ProductNotExistsException;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.addedproduct.NewProductBo;
import ar.lamansys.messages.domain.cart.CartCreationBo;
import ar.lamansys.messages.domain.cart.CartStateBo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CreateCart createCart;
    private final CreateNewProductInCart createNewProductInCart;
    private final EditProductInCart editProductInCart;
    private final DeleteProductInCart deleteProductInCart;
    private final GetCartState getCartState;
    private final PurchaseCart purchaseCart;

    @PostMapping("/{ownerId}/{sellerId}")
    public CartCreationBo postNewCart(@PathVariable String ownerId,
                                      @PathVariable String sellerId,
                                      @RequestBody List<NewProductBo> newProducts) throws MultipleExceptions, UserNotExistsException {
        return createCart.run(ownerId, sellerId, newProducts);
        //return ResponseEntity.ok("Cart created successfully");
    }

    @PostMapping("/addProduct/{ownerId}/{cartId}")
    public ResponseEntity postNewProductInCart(@PathVariable String ownerId,
                                               @PathVariable String cartId,
                                               @RequestBody NewProductBo newProduct) throws
            UserNotExistsException, CartNotExistsException, UserNotOwnsCartException, ProductNotExistsException, ProductInvalidSeller, ProductStockNotEnough {
        createNewProductInCart.run(ownerId, cartId, newProduct);
        return ResponseEntity.ok("Product added to cart successfully");
    }

    @PutMapping("/editProduct/{ownerId}/{cartId}")
    public ResponseEntity editProductInCart(@PathVariable String ownerId,
                                            @PathVariable String cartId,
                                            @RequestBody NewProductBo editedProduct) throws
            CartNotExistsException, UserNotExistsException, UserNotOwnsCartException, ProductNotExistsInCartException, ProductStockNotEnough {
        editProductInCart.run(ownerId, cartId, editedProduct);
        return ResponseEntity.ok("Product edited quantity in cart successfully");
    }

    @DeleteMapping("/deleteProduct/{ownerId}/{cartId}/{productId}")
    public ResponseEntity deleteProductInCart(@PathVariable String ownerId,
                                              @PathVariable String cartId,
                                              @PathVariable String productId) throws
            CartNotExistsException, UserNotExistsException, UserNotOwnsCartException, ProductNotExistsInCartException {
        deleteProductInCart.run(ownerId, cartId, productId);
        return ResponseEntity.ok("Product in cart deleted successfully");
    }

    @GetMapping("/state/{ownerId}/{cartId}")
    public CartStateBo getCartState(@PathVariable String ownerId, @PathVariable String cartId) throws
            CartNotExistsException, UserNotExistsException, UserNotOwnsCartException {
        return getCartState.run(ownerId, cartId);
    }

    @GetMapping("/purchase/{ownerId}/{cartId}")
    public ResponseEntity purchaseCart(@PathVariable String ownerId, @PathVariable String cartId) throws
            CartNotExistsException, UserNotExistsException, UserNotOwnsCartException, MultipleExceptions {
        Float totalPrice = purchaseCart.run(ownerId, cartId);
        return ResponseEntity.ok(String.format("Cart purchase successfully. Total price: %s",totalPrice));
    }

}
