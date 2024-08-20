package ar.lamansys.messages.application.user;

import ar.lamansys.messages.application.message.ClearMessages;
import ar.lamansys.messages.infrastructure.output.UserSessionStorage;
import ar.lamansys.messages.infrastructure.output.UserStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClearUsers {

    private final UserStorage userStorage;
    private final ClearMessages clearMessages;
    private final UserSessionStorage userSessionStorage;

    public void run() {
        userStorage.deleteAll();
        clearMessages.run();
        userSessionStorage.set("");
    }
}
