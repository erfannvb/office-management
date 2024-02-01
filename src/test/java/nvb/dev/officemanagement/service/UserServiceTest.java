package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.UserEntity;
import nvb.dev.officemanagement.repository.UserRepository;
import nvb.dev.officemanagement.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void testThatCreateUserSavesUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(anyValidUser());

        UserEntity savedUser = userService.createUser(anyValidUser());

        assertEquals("dummy", savedUser.getUsername());
        assertEquals("dummy", savedUser.getPassword());
        assertEquals("dummy", savedUser.getRole());

        verify(userRepository, atLeastOnce()).save(any(UserEntity.class));
    }

    @Test
    void testThatGetUserByIdReturnsTheExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(anyValidUser()));

        Optional<UserEntity> optionalUser = userService.getUserById(1L);

        assertEquals("dummy", optionalUser.get().getUsername());
        assertEquals("dummy", optionalUser.get().getPassword());
        assertEquals("dummy", optionalUser.get().getRole());

        verify(userRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetUserByIdReturnsNothing() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetAllUsersReturnsListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(anyValidUser(), anyValidUser()));

        List<UserEntity> allUsers = userService.getAllUsers();

        assertEquals(2, allUsers.size());
        verify(userRepository, atLeastOnce()).findAll();
    }

    @Test
    void testThatGetAllUsersReturnsEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        assertThrows(NoDataFoundException.class, () -> userService.getAllUsers());
        verify(userRepository, atLeastOnce()).findAll();
    }

    @Test
    void testThatUpdateUserUpdatesTheExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(anyValidUser()));
        when(userRepository.save(any(UserEntity.class))).thenReturn(anyValidUpdatedUser());

        UserEntity updatedUser = userService.updateUser(1L, anyValidUpdatedUser());

        assertEquals("updatedDummy", updatedUser.getUsername());
        assertEquals("updatedDummy", updatedUser.getPassword());
        assertEquals("updatedDummy", updatedUser.getRole());

        verify(userRepository, atLeastOnce()).findById(anyLong());
        verify(userRepository, atLeastOnce()).save(any(UserEntity.class));
    }

    @Test
    void testThatUpdateUserCannotUpdateTheNonExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.updateUser(1L, anyValidUpdatedUser()));

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

}