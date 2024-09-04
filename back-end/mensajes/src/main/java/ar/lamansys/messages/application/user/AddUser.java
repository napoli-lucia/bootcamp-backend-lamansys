package ar.lamansys.messages.application.user;

import ar.lamansys.messages.application.user.exception.UserExistsException;
import ar.lamansys.messages.domain.user.AppUserBo;
import ar.lamansys.messages.infrastructure.output.UserStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddUser {
    private final UserStorage userStorage;
    private final AssertUserNotExists assertUserNotExists;

    public void run(AppUserBo user) throws UserExistsException {
        assertUserNotExists.run(user.getId());
        userStorage.save(user);
    }
}
