package com.sabonline.notes_api.service;

import com.sabonline.notes_api.dto.UserDTO;
import com.sabonline.notes_api.model.User;

import java.util.List;

public interface IUserService {
  public User createUser(UserDTO userDto);

  public UserDTO getUserById(Long id);

  public List<UserDTO> getAllUsers();
}
