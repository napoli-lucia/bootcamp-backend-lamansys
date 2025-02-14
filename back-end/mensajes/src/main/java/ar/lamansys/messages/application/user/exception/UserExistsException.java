package ar.lamansys.messages.application.user.exception;

import lombok.Getter;

@Getter
public class UserExistsException extends Exception {
    String userId;

    public UserExistsException(String userId) {
        super(String.format("User %s exists", userId));
        this.userId = userId;
    }
}
