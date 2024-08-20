package ar.lamansys.messages.application.user.exception;

import lombok.Getter;

@Getter
public class UserNotExistsException extends Exception{
    String userId;

    public UserNotExistsException(String userId) {
        super(String.format("User %s don't exists", userId));
        this.userId = userId;
    }
}
