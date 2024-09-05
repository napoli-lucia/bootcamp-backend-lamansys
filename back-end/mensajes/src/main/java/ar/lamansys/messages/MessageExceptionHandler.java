package ar.lamansys.messages;

import ar.lamansys.messages.application.cart.exception.*;
import ar.lamansys.messages.application.product.exception.InvalidSellerException;
import ar.lamansys.messages.application.product.exception.ProductExistsException;
import ar.lamansys.messages.application.product.exception.ProductNotExistsException;
import ar.lamansys.messages.application.user.exception.UserExistsException;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.application.user.exception.UserSessionNotExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class MessageExceptionHandler {

    @ExceptionHandler(MultipleExceptions.class)
    public ResponseEntity<List<String>> multipleExceptionsHandler(MultipleExceptions ex) {
        List<String> exceptionMessages = ex.getExceptions().stream()
                .map(Throwable::getLocalizedMessage)
                .collect(Collectors.toList());

        return ResponseEntity.status(400).body(exceptionMessages);
    }

    @ExceptionHandler({UserNotExistsException.class, UserSessionNotExists.class, CartNotExistsException.class, ProductNotExistsException.class})
    public ResponseEntity<String> notExistsHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getLocalizedMessage());
    }

    @ExceptionHandler({UserExistsException.class, ProductStockNotEnough.class, ProductInvalidSeller.class, ProductNotExistsInCartException.class, ProductExistsException.class})
    public ResponseEntity<String> existsHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());
    }

    @ExceptionHandler({UserNotOwnsCartException.class, InvalidSellerException.class})
    public ResponseEntity<String> unauthorizedHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getLocalizedMessage());
    }

}
