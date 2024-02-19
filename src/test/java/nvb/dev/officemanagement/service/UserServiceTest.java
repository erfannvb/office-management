package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.config.UserTransferBatchJob;
import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.UserEntity;
import nvb.dev.officemanagement.repository.UserRepository;
import nvb.dev.officemanagement.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static nvb.dev.officemanagement.MotherObject.anyValidUpdatedUser;
import static nvb.dev.officemanagement.MotherObject.anyValidUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    JobLauncher jobLauncher;

    @Mock
    UserTransferBatchJob job;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void testThatCreateUserSavesUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(anyValidUser());
        when(passwordEncoder.encode(anyString())).thenReturn(anyValidUser().getPassword());

        UserEntity savedUser = userService.createUser(anyValidUser());

        assertEquals("dummy", savedUser.getUsername());
        assertEquals("dummy", savedUser.getPassword());

        verify(userRepository, atLeastOnce()).save(any(UserEntity.class));
    }

    @Test
    void testThatGetUserByIdReturnsTheExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(anyValidUser()));

        Optional<UserEntity> optionalUser = userService.getUserById(1L);

        assertEquals("dummy", optionalUser.get().getUsername());
        assertEquals("dummy", optionalUser.get().getPassword());

        verify(userRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetUserByIdCannotReturnTheNonExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetUserByUsernameReturnsTheExistingUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(anyValidUser()));

        Optional<UserEntity> optionalUser = userService.getUserByUsername("dummy");

        assertEquals("dummy", optionalUser.get().getUsername());
        assertEquals("dummy", optionalUser.get().getPassword());

        verify(userRepository, atLeastOnce()).findByUsername(anyString());
    }

    @Test
    void testThatGetUserByUsernameCannotReturnTheNonExistingUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(NoDataFoundException.class, () -> userService.getUserByUsername("dummy"));
        verify(userRepository, atLeastOnce()).findByUsername(anyString());
    }

    @Test
    void testThatUsernameExistsReturnsTrue() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(anyValidUser()));

        boolean usernameExists = userService.usernameExists("dummy");

        assertTrue(usernameExists);
        verify(userRepository, atLeastOnce()).findByUsername(anyString());
    }

    @Test
    void testThatUsernameExistsReturnsFalse() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        boolean usernameExists = userService.usernameExists("dummy");

        assertFalse(usernameExists);
        verify(userRepository, atLeastOnce()).findByUsername(anyString());
    }

    @Test
    void testThatGetAllUsersReturnsListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(anyValidUser(), anyValidUser()));

        List<UserEntity> userEntities = userService.getAllUsers();

        assertEquals(2, userEntities.size());
        verify(userRepository, atLeastOnce()).findAll();
    }

    @Test
    void testThatGetAllUsersReturnsAnEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        assertThrows(NoDataFoundException.class, () -> userService.getAllUsers());
        verify(userRepository, atLeastOnce()).findAll();
    }

    @Test
    void testThatUpdateUserUpdatesTheExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(anyValidUser()));
        when(userRepository.save(any(UserEntity.class))).thenReturn(anyValidUpdatedUser());

        UserEntity userEntity = userService.updateUser(1L, anyValidUser());

        assertEquals("updatedDummy", userEntity.getUsername());
        assertEquals("updatedDummy", userEntity.getPassword());

        verify(userRepository, atLeastOnce()).findById(anyLong());
        verify(userRepository, atLeastOnce()).save(any(UserEntity.class));
    }

    @Test
    void testThatUpdateUserCannotUpdateTheNonExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(anyValidUpdatedUser());

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(1L, anyValidUser()));

        verify(userRepository, atLeastOnce()).findById(anyLong());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testThatDeleteUserDeletesTheExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(anyValidUser()));

        userService.deleteUser(1L);

        verify(userRepository, atLeastOnce()).findById(anyLong());
        verify(userRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void testThatDeleteUserCannotDeleteTheNonExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(1L));

        verify(userRepository, atLeastOnce()).findById(anyLong());
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void testThatIsExistsReturnsTrue() {
        when(userRepository.existsById(anyLong())).thenReturn(true);

        boolean exists = userService.isExists(1L);

        assertTrue(exists);
        verify(userRepository, atLeastOnce()).existsById(anyLong());
    }

    @Test
    void testThatIsExistsReturnsFalse() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        boolean exists = userService.isExists(1L);

        assertFalse(exists);
        verify(userRepository, atLeastOnce()).existsById(anyLong());
    }

    @Test
    void testThatLaunchJobWorksProperly() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        userService.launchJob();
        verify(jobLauncher, atLeastOnce()).run(any(), any());
    }

}