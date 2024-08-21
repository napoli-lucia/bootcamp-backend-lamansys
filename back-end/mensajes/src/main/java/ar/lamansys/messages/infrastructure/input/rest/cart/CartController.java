package ar.lamansys.messages.infrastructure.input.rest.cart;

import ar.lamansys.messages.application.cart.*;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.application.user.exception.UserSessionNotExists;
import ar.lamansys.messages.domain.addedproduct.NewProductBo;
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

    @PostMapping("/{ownerId}/{sellerId}")
    public ResponseEntity postNewCart(@PathVariable String ownerId, @PathVariable String sellerId, @RequestBody List<NewProductBo> newProducts) throws UserNotExistsException, UserSessionNotExists {
        createCart.run(ownerId, sellerId, newProducts);
        return ResponseEntity.ok("Cart created successfully");
    }

    //ProductNotExistException
    //CartNotExistException
    //UserNotExistsException
    //UserNotOwnerCartException
    //ProductNotValidException -> excepcion valida que producto es el mismo del seller
    //ProductStockNotEnough -> excepcion valida que el stock sea suficiente
    @PostMapping("/addProduct/{ownerId}/{cartId}")
    public ResponseEntity postNewProductInCart(@PathVariable String ownerId, @PathVariable String cartId, @RequestBody NewProductBo newProduct) {
        createNewProductInCart.run(ownerId, cartId, newProduct);
        return ResponseEntity.ok("Product added to cart successfully");
    }

    @PutMapping("/editProduct/{ownerId}/{cartId}")
    public ResponseEntity editProductInCart(@PathVariable String ownerId, @PathVariable String cartId, @RequestBody NewProductBo editedProduct) {
        editProductInCart.run(ownerId, cartId, editedProduct);
        return ResponseEntity.ok("Product edited quantity in cart successfully");
    }

    @DeleteMapping("/deleteProduct/{ownerId}/{cartId}/{productId}")
    public ResponseEntity deleteProductInCart(@PathVariable String ownerId, @PathVariable String cartId, @PathVariable String productId) {
        deleteProductInCart.run(ownerId, cartId, productId);
        return ResponseEntity.ok("Product in cart deleted successfully");
    }

    @GetMapping("/state/{ownerId}/{cartId}")
    public CartStateBo getCartState(@PathVariable String ownerId, @PathVariable String cartId) {
        return getCartState.run(ownerId, cartId);
    }

}
