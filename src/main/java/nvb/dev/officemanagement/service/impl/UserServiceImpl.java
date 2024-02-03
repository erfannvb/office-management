package nvb.dev.officemanagement.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.UserEntity;
import nvb.dev.officemanagement.repository.UserRepository;
import nvb.dev.officemanagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> getUserById(long userId) {
        return Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, userId)));
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        return unwrapUser(optionalUser, optionalUser.get().getId());
    }

    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        if (!userEntityList.isEmpty()) {
            return userEntityList;
        } else {
            throw new NoDataFoundException();
        }
    }

    @Override
    public UserEntity updateUser(long userId, UserEntity user) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {

            UserEntity currentUser = optionalUser.get();

            currentUser.setUsername(user.getUsername());
            currentUser.setPassword(user.getPassword());

            return userRepository.save(currentUser);

        } else {
            throw new EntityNotFoundException(UserEntity.class, userId);
        }
    }

    @Override
    public void deleteUser(long userId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new EntityNotFoundException(UserEntity.class, userId);
        }
    }

    private static UserEntity unwrapUser(Optional<UserEntity> entity, long userId) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(UserEntity.class, userId);
    }
}
