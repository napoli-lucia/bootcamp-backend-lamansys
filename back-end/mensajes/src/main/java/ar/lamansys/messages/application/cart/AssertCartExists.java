package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.CartNotExistsException;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertCartExists {

    private CartStorage cartStorage;

    public void run(String cartId) throws CartNotExistsException {
        if ( !cartStorage.exists(cartId) ) {
            throw new CartNotExistsException(cartId);
        }
    }
}