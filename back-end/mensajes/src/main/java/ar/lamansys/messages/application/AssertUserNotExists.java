package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.UserExistsException;
import ar.lamansys.messages.infrastructure.output.UserStorage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AssertUserNotExists {
    private UserStorage userStorage;

    public void run(String userId) throws UserExistsException {
        if ( userStorage.exists(userId) ) {
            throw new UserExistsException(userId);
        }
    }
}
