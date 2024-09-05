package ar.lamansys.messages.application.product.exception;

import lombok.Getter;

@Getter
public class InvalidSeller extends Exception{

    public InvalidSeller() {
        super("Invalid seller: You are not the owner of the product.");
    }
}
