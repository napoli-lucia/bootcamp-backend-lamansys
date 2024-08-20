package ar.lamansys.messages.application.user;

import ar.lamansys.messages.application.user.exception.UserSessionNotExists;
import ar.lamansys.messages.infrastructure.output.UserSessionStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetUserSession {
    private final UserSessionStorage userSessionStorage;

    public String run() throws UserSessionNotExists {
        var user = userSessionStorage.get();
        if (user.isEmpty()) {
            throw new UserSessionNotExists();
        }
        return user;
    }
}
