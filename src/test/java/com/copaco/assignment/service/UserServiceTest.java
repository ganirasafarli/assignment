package com.copaco.assignment.service;

import com.copaco.assignment.domian.dto.QuoteDTO;
import com.copaco.assignment.domian.entity.Quote;
import com.copaco.assignment.domian.entity.User;
import com.copaco.assignment.domian.query.SearchRequest;
import com.copaco.assignment.domian.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private SearchRequest searchRequest;
    private Quote quote;
    private QuoteDTO quoteDTO;
    private User user;

    @BeforeEach
    void setUp() {
        searchRequest = new SearchRequest();
        searchRequest.setPage(1);
        searchRequest.setSize(5);

        user = new User();
        user.setId(1l);
        user.setUsername("user");

        quote = new Quote();
        quote.setId(1L);
        quote.setQuote("President Ford used humor a great deal");
        quote.setAuthor("Robert Orben");
        quote.setTags(Arrays.asList("tag1", "tag2"));
        quote.setCategory("humor");
        quote.setLikedByUsers(new HashSet<>(Collections.singletonList(user)));

        quoteDTO = new QuoteDTO();
        quoteDTO.setId(1L);
        quoteDTO.setQuote("President Ford used humor a great deal");
        quoteDTO.setAuthor("Robert Orben");
        quoteDTO.setTags(Arrays.asList("tag1", "tag2"));
        quoteDTO.setCategory("humor");
    }

    @Test
    public void testNonExistData_EmptyUserList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        when(userRepository.count()).thenReturn(0L);

        boolean result = userService.nonExistData();

        assertTrue(result);

        verify(userRepository).findAll();
        verify(userRepository).count();
    }

    @Test
    public void testNonExistData_NonEmptyUserList() {
        List<User> users = Collections.singletonList(user);

        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.count()).thenReturn((long) users.size());

        boolean result = userService.nonExistData();

        assertFalse(result);

        verify(userRepository).findAll();
        verify(userRepository).count();
    }

    @Test
    public void testSaveAll() {
        List<User> users = Collections.singletonList(user);
        userService.saveAll(users);

        verify(userRepository).saveAll(users);
    }
}