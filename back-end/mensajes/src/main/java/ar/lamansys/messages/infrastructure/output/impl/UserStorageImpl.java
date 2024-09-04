package ar.lamansys.messages.infrastructure.output.impl;

import ar.lamansys.messages.domain.user.AppUserBo;
import ar.lamansys.messages.infrastructure.output.repository.AppUserRepository;
import ar.lamansys.messages.infrastructure.output.UserStorage;
import ar.lamansys.messages.infrastructure.output.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@RequiredArgsConstructor
@Service
public class UserStorageImpl implements UserStorage {

    private final AppUserRepository appUserRepository;

    @Override
    public void save(AppUserBo user) {
        appUserRepository.save(new AppUser(user));
    }

    @Override
    public boolean exists(String userId) {
        return appUserRepository.existsById(userId);
    }

    @Override
    public void deleteAll() {
        appUserRepository.deleteAll();
    }

}
