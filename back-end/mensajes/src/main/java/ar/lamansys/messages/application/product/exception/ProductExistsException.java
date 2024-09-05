package ar.lamansys.messages.application.product.exception;

import lombok.Getter;

@Getter
public class ProductExistsException extends Exception{

    String productId;

    public ProductExistsException(String productId) {
        super(String.format("Product %s already exists", productId));
        this.productId = productId;
    }
}
