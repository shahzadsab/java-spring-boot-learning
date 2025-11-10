package com.sabonline.notes_api.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabonline.notes_api.dto.UserDTO;
import com.sabonline.notes_api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserObjectMapper {
  private final ObjectMapper objectMapper;

  @Autowired
  public UserObjectMapper (ObjectMapper objectMapper)  {
    this.objectMapper  =  objectMapper;
  }

  public <T, R> R  map (T source, Class<R> targetClass) {
    return objectMapper.convertValue(source,  targetClass);
  }

  public UserDTO toDTO(User user) {
    return map(user, UserDTO.class);
  }

  public User  toEntity (UserDTO dto) {
    return map(dto, User.class);
  }
}
