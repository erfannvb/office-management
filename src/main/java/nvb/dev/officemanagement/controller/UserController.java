package nvb.dev.officemanagement.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.mapper.UserMapper;
import nvb.dev.officemanagement.model.dto.UserDto;
import nvb.dev.officemanagement.model.entity.UserEntity;
import nvb.dev.officemanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        UserEntity userEntity = userMapper.toUserEntity(userDto);
        UserEntity savedUser = userService.createUser(userEntity);
        return new ResponseEntity<>(userMapper.toUserDto(savedUser), HttpStatus.CREATED);
    }

    @GetMapping(path = "/users/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long userId) {
        Optional<UserEntity> foundUser = userService.getUserById(userId);
        return foundUser.map(userEntity -> {
            UserDto userDto = userMapper.toUserDto(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserEntity> userEntityList = userService.getAllUsers();
        List<UserDto> userDtoList = userEntityList.stream().map(userMapper::toUserDto).toList();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @PutMapping(path = "/users/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable long userId, @RequestBody @Valid UserDto userDto) {
        if (!userService.isExists(userId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        UserEntity userEntity = userMapper.toUserEntity(userDto);
        UserEntity updatedUser = userService.updateUser(userId, userEntity);
        return new ResponseEntity<>(userMapper.toUserDto(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping(path = "/users/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
