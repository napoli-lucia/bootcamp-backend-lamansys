package ar.lamansys.messages.application.product.exception;

import lombok.Getter;

@Getter
public class ProductNotExistsException extends Exception{

    String productId;

    public ProductNotExistsException(String productId) {
        super(String.format("Product %s not exists", productId));
        this.productId = productId;
    }
}
