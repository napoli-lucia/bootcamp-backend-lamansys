package ar.lamansys.messages.infrastructure.input.rest.message;

import ar.lamansys.messages.application.message.ClearMessages;
import ar.lamansys.messages.application.message.SendUserMessage;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.application.user.exception.UserSessionNotExists;
import ar.lamansys.messages.domain.message.NewMessageBo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final SendUserMessage sendUserMessage;
    private final ClearMessages clearMessages;

    @PostMapping("/send")
    public void sendUserMessage(@RequestBody NewMessageBo newMessage) throws UserNotExistsException, UserSessionNotExists {
        sendUserMessage.run(newMessage);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Object> clearMessage() {
        clearMessages.run();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
