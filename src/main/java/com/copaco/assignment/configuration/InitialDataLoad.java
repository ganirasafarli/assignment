package com.copaco.assignment.configuration;

import com.copaco.assignment.domian.dto.QuoteDTO;
import com.copaco.assignment.domian.dto.UserDTO;
import com.copaco.assignment.domian.mapper.QuoteMapper;
import com.copaco.assignment.domian.mapper.UserMapper;
import com.copaco.assignment.service.QuoteService;
import com.copaco.assignment.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitialDataLoad {
    private final ObjectMapper mapper;
    private final QuoteService quoteService;
    private final UserService userService;
    private final QuoteMapper quoteMapper;
    private final UserMapper userMapper;

    @Bean
    CommandLineRunner loadQuotes() {
        return args -> {
            if (quoteService.nonExistData()) {
                TypeReference<List<QuoteDTO>> typeReference = new TypeReference<>() {
                };
                InputStream inputStream = TypeReference.class.getClassLoader().getResourceAsStream("initial-quote-data.json");
                try {
                    List<QuoteDTO> quotes = mapper.readValue(inputStream, typeReference);
                    quoteService.saveAll(quoteMapper.toEntityList(quotes));
                    log.info("Quotes Saved");
                } catch (IOException e) {
                    log.error("Unable to save quotes: " + e.getMessage());
                }
            }
        };
    }

    @Bean
    CommandLineRunner loadUsers() {
        return args -> {
            if (userService.nonExistData()) {
                TypeReference<List<UserDTO>> typeReference = new TypeReference<>() {
                };
                InputStream inputStream = TypeReference.class.getClassLoader().getResourceAsStream("initial-user-data.json");
                try {
                    List<UserDTO> users = mapper.readValue(inputStream, typeReference);
                    userService.saveAll(userMapper.toEntityList(users));
                    log.info("Users Saved");
                } catch (IOException e) {
                    log.error("Unable to save users: " + e.getMessage());
                }
            }
        };
    }
}
