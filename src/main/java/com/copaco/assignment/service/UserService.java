package com.copaco.assignment.service;

import com.copaco.assignment.domian.entity.User;
import com.copaco.assignment.domian.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public boolean nonExistData() {
        List<User> all = userRepository.findAll();
        return userRepository.count() == 0;
    }

    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }
}
