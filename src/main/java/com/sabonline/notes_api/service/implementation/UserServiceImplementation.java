package com.sabonline.notes_api.service.implementation;

import com.sabonline.notes_api.dto.UserDTO;
import com.sabonline.notes_api.mapper.UserObjectMapper;
import com.sabonline.notes_api.model.User;
import com.sabonline.notes_api.repository.UserRepository;
import com.sabonline.notes_api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements IUserService {

    private final UserRepository userRepo;
    private final UserObjectMapper userMapper;

    @Autowired
    public UserServiceImplementation(UserRepository userRepo, UserObjectMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(UserDTO userDto) {
        return userRepo.save(userMapper.toEntity(userDto));
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepo.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}
