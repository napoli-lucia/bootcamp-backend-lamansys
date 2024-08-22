package ar.lamansys.messages.application.cart.exception;

import lombok.Getter;

@Getter
public class ProductStockNotEnough extends Exception{

    String productId;

    public ProductStockNotEnough(String productId) {
        super(String.format("Product %s cannot meet the stock", productId));
        this.productId = productId;
    }
}
