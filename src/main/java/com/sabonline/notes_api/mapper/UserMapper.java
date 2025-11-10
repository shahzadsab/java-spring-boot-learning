package com.sabonline.notes_api.mapper;

import com.sabonline.notes_api.dto.UserDTO;
import com.sabonline.notes_api.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public UserDTO toDto(User user) {
    if (user == null) {
      return null;
    }

    UserDTO dto = new UserDTO();
    dto.setId(user.getId());
    dto.setUsername(user.getUsername());
    dto.setEmail(user.getEmail());
    if (user.getCreatedAt() != null) {
      dto.setCreatedDate(user.getCreatedAt().format(FORMATTER));
    }
    return dto;
  }

  public User toEntity(UserDTO dto) {
    if (dto == null) {
      return null;
    }

    User user = new User();
    user.setId(dto.getId());
    user.setUsername(dto.getUsername());
    user.setEmail(dto.getEmail());

    if (dto.getCreatedDate() != null) {
      user.setCreatedAt(LocalDateTime.parse(dto.getCreatedDate(), FORMATTER));
    } else {
      user.setCreatedAt(LocalDateTime.now());
    }

    return user;
  }

  public List<UserDTO> toDtoList(List<User> users) {
    return users.stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  public List<User> toEntityList(List<UserDTO> dtos) {
    return dtos.stream()
        .map(this::toEntity)
        .collect(Collectors.toList());
  }
}
