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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;
    private final QuoteMapper quoteMapper;

    @Transactional
    public QuoteDTO getRandomQuote() {
        long totalCount = quoteRepository.count();
        Random random = new Random();
        long randomOffset = random.nextLong(totalCount);
        Optional<Quote> randomQuoteOptional = quoteRepository.findById(randomOffset);
        return quoteMapper.toDto(randomQuoteOptional.orElseThrow(()->new NotFoundError("There is not Quote in memory")));
    }

    @Transactional
    public Page<QuoteDTO> search(SearchRequest request) {
        SearchSpecification specification = new SearchSpecification(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        Page<Quote> all = quoteRepository.findAll(specification, pageable);
        return all.map(quoteMapper::toDto);
    }

    @Transactional
    public ResponseEntity<Object> likeQuote(Long quoteId, Long userId) {
        User user = retriveAndValidateUser(userId);
        Quote quote = quoteRepository.findById(quoteId).orElseThrow(() -> new NotFoundError("Quote not found"));
        quote.addUserToLikedByUsers(user);
        quoteRepository.save(quote);
        return ResponseEntity.accepted().build();
    }

    public boolean nonExistData() {
        return quoteRepository.count() == 0;
    }

    public void saveAll(List<Quote> quotes) {
        quoteRepository.saveAll(quotes);
    }

    private User retriveAndValidateUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new NotFoundError(MessageFormat.format("User not found with id {0}", userId)));
    }
}
