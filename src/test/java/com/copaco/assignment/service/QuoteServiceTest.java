package com.copaco.assignment.service;

import com.copaco.assignment.domian.dto.QuoteDTO;
import com.copaco.assignment.domian.entity.Quote;
import com.copaco.assignment.domian.entity.User;
import com.copaco.assignment.domian.mapper.QuoteMapper;
import com.copaco.assignment.domian.query.SearchRequest;
import com.copaco.assignment.domian.query.SearchSpecification;
import com.copaco.assignment.domian.repo.QuoteRepository;
import com.copaco.assignment.domian.repo.UserRepository;
import com.copaco.assignment.exception.NotFoundError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuoteServiceTest {
    @Mock
    private QuoteRepository quoteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuoteMapper quoteMapper;

    @InjectMocks
    private QuoteService quoteService;

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
    void testSearch() {
        List<Quote> quotes = Collections.singletonList(quote);
        Page<Quote> quotePage = new PageImpl<>(quotes);

        when(quoteRepository.findAll(any(SearchSpecification.class), any(Pageable.class)))
                .thenReturn(quotePage);
        when(quoteMapper.toDto(quote)).thenReturn(quoteDTO);

        Page<QuoteDTO> result = quoteService.search(searchRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(quoteDTO, result.getContent().get(0));
        verify(quoteRepository).findAll(any(SearchSpecification.class), any(Pageable.class));
        verify(quoteMapper).toDto(quote);
    }

    @Test
    void testLikeQuote() {
        Long quoteId = 1L;
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(quoteRepository.findById(quoteId)).thenReturn(Optional.of(quote));

        ResponseEntity<?> response = quoteService.likeQuote(quoteId, user.getId());

        assertEquals(ResponseEntity.accepted().build(), response);
    }

    @Test
    void givenUserIdAndQuoteIdWhenUserNotFoundThenThrowNotFoundError() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        NotFoundError exception = assertThrows(NotFoundError.class, () -> quoteService.likeQuote(quote.getId(), user.getId()));
        assertEquals("User not found with id "+ user.getId(), exception.getMessage());
    }

    @Test
    void givenUserIdAndQuoteIdWhenQuoteNotFoundThenThrowNotFoundError() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(quoteRepository.findById(quote.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundError.class, () -> quoteService.likeQuote(quote.getId(), user.getId()));
    }

    @Test
    void testNonExistData() {
        when(quoteRepository.count()).thenReturn(0L);
        boolean result = quoteService.nonExistData();
        assertTrue(result);
    }

    @Test
    void testSaveAll() {
        List<Quote> quotes = Collections.singletonList(quote);
        quoteService.saveAll(quotes);
        verify(quoteRepository).saveAll(quotes);
    }
}