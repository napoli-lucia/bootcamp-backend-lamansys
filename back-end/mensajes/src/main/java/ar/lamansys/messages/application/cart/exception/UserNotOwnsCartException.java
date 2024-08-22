package ar.lamansys.messages.application.cart.exception;

import lombok.Getter;

@Getter
public class UserNotOwnsCartException extends Exception{

    String cartId;
    String ownerId;

    public UserNotOwnsCartException(String cartId, String ownerId) {
        super(String.format("Owner %s does not own cart %s", ownerId, cartId));
        this.cartId = cartId;
        this.ownerId = ownerId;
    }
}
