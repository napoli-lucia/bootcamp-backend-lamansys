package ar.lamansys.messages.infrastructure.output;

import ar.lamansys.messages.domain.user.AppUserBo;

public interface UserStorage {

    void save(
            AppUserBo user
    );

    boolean exists(
            String userId
    );

    void deleteAll();

}
