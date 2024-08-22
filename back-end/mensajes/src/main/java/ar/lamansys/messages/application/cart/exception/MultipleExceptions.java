package ar.lamansys.messages.application.cart.exception;

import lombok.Getter;
import java.util.List;

@Getter
public class MultipleExceptions extends Exception{
    private final List<Exception> exceptions;

    public MultipleExceptions(List<Exception> exceptions) {
        super("Multiple exceptions occurred");
        this.exceptions = exceptions;
    }

}
