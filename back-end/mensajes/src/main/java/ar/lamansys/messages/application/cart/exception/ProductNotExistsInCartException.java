package ar.lamansys.messages.application.cart.exception;

import lombok.Getter;

@Getter
public class ProductNotExistsInCartException extends Exception{

    String productId;

    public ProductNotExistsInCartException(String productId) {
        super(String.format("Product %s not exists in cart", productId));
        this.productId = productId;
    }
}
