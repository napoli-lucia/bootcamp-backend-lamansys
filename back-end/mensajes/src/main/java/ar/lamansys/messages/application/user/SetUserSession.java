package ar.lamansys.messages.application.user;

import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.infrastructure.output.UserSessionStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SetUserSession {
    private final UserSessionStorage userSessionStorage;
    private final AssertUserExists assertUserExists;

    public void run(String userId) throws UserNotExistsException {
        assertUserExists.run(userId);
        userSessionStorage.set(userId);
    }
}
