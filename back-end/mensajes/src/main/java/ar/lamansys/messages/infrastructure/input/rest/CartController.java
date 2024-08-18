package ar.lamansys.messages.infrastructure.input.rest;

import ar.lamansys.messages.application.CreateCart;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.UserSessionNotExists;
import ar.lamansys.messages.domain.NewMessageBo;
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

    @PostMapping("/{ownerId}/{sellerId}")
    public ResponseEntity postNewCart(@PathVariable String ownerId, @PathVariable String sellerId, @RequestBody List<NewProductBo> newProducts) throws UserNotExistsException, UserSessionNotExists {
        createCart.run(ownerId, sellerId, newProducts);
        return ResponseEntity.ok("Cart created successfully");
    }

}
