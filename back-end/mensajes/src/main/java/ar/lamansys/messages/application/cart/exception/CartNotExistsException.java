package ar.lamansys.messages.application.cart.exception;

import lombok.Getter;

@Getter
public class CartNotExistsException extends Exception{
    String cartId;

    public CartNotExistsException(String cartId) {
        super(String.format("Cart %s not exists", cartId));
        this.cartId = cartId;
    }
}
