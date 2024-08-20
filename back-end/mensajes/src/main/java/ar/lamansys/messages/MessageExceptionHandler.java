package ar.lamansys.messages;

import ar.lamansys.messages.application.user.exception.UserExistsException;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.application.user.exception.UserSessionNotExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MessageExceptionHandler {

    @ExceptionHandler({UserNotExistsException.class, UserSessionNotExists.class})
    public ResponseEntity<String> notExistsHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<String> existsHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


}
