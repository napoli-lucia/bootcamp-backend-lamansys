package ar.lamansys.messages.domain.user;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class AppUserBo {
    String id;
    String username;
    String password;
    String email;
}
