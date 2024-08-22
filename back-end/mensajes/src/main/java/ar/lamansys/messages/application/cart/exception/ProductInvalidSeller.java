package ar.lamansys.messages.application.cart.exception;

import lombok.Getter;

@Getter
public class ProductInvalidSeller extends Exception{

    String productId;

    public ProductInvalidSeller(String productId) {
        super(String.format("Product %s has an invalid seller", productId));
        this.productId = productId;
    }
}
