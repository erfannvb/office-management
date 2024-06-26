package nvb.dev.officemanagement.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.UserEntity;
import nvb.dev.officemanagement.repository.UserRepository;
import nvb.dev.officemanagement.service.UserService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JobLauncher jobLauncher;
    private final Job job;

    @Override
    public UserEntity createUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> getUserById(long userId) {
        return Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, userId)));
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(NoDataFoundException::new));
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
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

    @Override
    public boolean isExists(long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void launchJob() throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(job, jobParameters);
    }

}
