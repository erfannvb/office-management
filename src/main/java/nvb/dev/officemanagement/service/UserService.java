package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.model.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity createUser(UserEntity user);

    Optional<UserEntity> getUserById(long userId);

    Optional<UserEntity> getUserByUsername(String username);

    List<UserEntity> getAllUsers();

    UserEntity updateUser(long userId, UserEntity user);

    void deleteUser(long userId);

    boolean isExists(long userId);

    UserDetailsService userDetailsService();

}
