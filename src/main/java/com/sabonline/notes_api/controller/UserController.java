package com.sabonline.notes_api.controller;

import com.sabonline.notes_api.dto.UserDTO;
import com.sabonline.notes_api.model.User;
import com.sabonline.notes_api.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  private final IUserService userService;

  public UserController(IUserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
    UserDTO userDto = userService.getUserById(id);
    return ResponseEntity.ok(userDto);
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody UserDTO userDto) {
    User user = userService.createUser(userDto);
    return ResponseEntity.ok(user);
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<UserDTO> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }
}
