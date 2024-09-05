package ar.lamansys.messages.application.product.exception;

import lombok.Getter;

@Getter
public class InvalidSellerException extends Exception{

    public InvalidSellerException() {
        super("Invalid seller: You are not the owner of the product.");
    }
}
