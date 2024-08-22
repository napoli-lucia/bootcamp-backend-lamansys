package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.UserNotOwnsCartException;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class AssertUserOwnsCart {

    private CartStorage cartStorage;

    public void run(String cartId, String ownerId) throws UserNotOwnsCartException {
        if ( !cartStorage.getById(cartId).get().getOwnerId().equals(ownerId) ) {
            throw new UserNotOwnsCartException(cartId, ownerId);
        }
    }
}
