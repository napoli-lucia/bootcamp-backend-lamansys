package ar.lamansys.messages.application.message;

import ar.lamansys.messages.infrastructure.output.MessageStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClearMessages {

    private MessageStorage messageStorage;

    public void run() {
        messageStorage.deleteAll();
    }
}
