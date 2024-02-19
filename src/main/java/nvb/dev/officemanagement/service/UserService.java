package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.model.entity.UserEntity;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity createUser(UserEntity user);

    Optional<UserEntity> getUserById(long userId);

    Optional<UserEntity> getUserByUsername(String username);

    boolean usernameExists(String username);

    List<UserEntity> getAllUsers();

    UserEntity updateUser(long userId, UserEntity user);

    void deleteUser(long userId);

    boolean isExists(long userId);

    void launchJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException;

}
