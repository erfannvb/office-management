package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity createUser(UserEntity user);

    Optional<UserEntity> getUserById(long userId);

    List<UserEntity> getAllUsers();

    UserEntity updateUser(long userId, UserEntity user);

    void deleteUser(long userId);

}
