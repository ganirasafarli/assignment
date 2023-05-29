package com.copaco.assignment.controller;

import com.copaco.assignment.domian.dto.QuoteDTO;
import com.copaco.assignment.domian.query.SearchRequest;
import com.copaco.assignment.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/quote")
public class QuoteController {
    private final QuoteService quoteService;


    @GetMapping("/random")
    public ResponseEntity<QuoteDTO> getRandomQuote() {
        return ResponseEntity.ok(quoteService.getRandomQuote());
    }

    @PostMapping("/search")
    public ResponseEntity<Page<QuoteDTO>> searchQuote(@RequestBody SearchRequest request) {
        return ResponseEntity.ok(quoteService.search(request));
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<?> like(@RequestHeader("user_id") Long userId, @PathVariable Long id) {
        return quoteService.likeQuote(id, userId);
    }

}
