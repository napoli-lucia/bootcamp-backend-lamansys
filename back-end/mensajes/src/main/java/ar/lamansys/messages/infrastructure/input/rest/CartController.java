package ar.lamansys.messages.infrastructure.input.rest;

import ar.lamansys.messages.application.CreateCart;
import ar.lamansys.messages.application.CreateNewProductInCart;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.UserSessionNotExists;
import ar.lamansys.messages.domain.NewProductBo;
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

}
