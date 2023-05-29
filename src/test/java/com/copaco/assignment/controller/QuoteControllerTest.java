package com.copaco.assignment.controller;

import com.copaco.assignment.domian.dto.QuoteDTO;
import com.copaco.assignment.domian.query.SearchRequest;
import com.copaco.assignment.service.QuoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuoteService quoteService;

    private QuoteDTO quoteDTO;
    private SearchRequest searchRequest;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        quoteDTO = new QuoteDTO();
        quoteDTO.setId(1L);
        quoteDTO.setQuote("President Ford used humor a great deal");
        quoteDTO.setAuthor("Robert Orben");
        quoteDTO.setTags(Arrays.asList("tag1", "tag2"));
        quoteDTO.setCategory("humor");

        searchRequest = new SearchRequest();
        searchRequest.setFilters(Collections.emptyList());
        searchRequest.setSorts(Collections.emptyList());
        searchRequest.setPage(0);
        searchRequest.setSize(10);

        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetRandomQuote() throws Exception {
        when(quoteService.getRandomQuote()).thenReturn(quoteDTO);
        String responseJsonStringFormat = objectMapper.writeValueAsString(quoteDTO);

        mockMvc.perform(get("/v1/quote/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJsonStringFormat, true));

        verify(quoteService).getRandomQuote();
    }

    @Test
    public void testQuoteSearch() throws Exception {
        Page<QuoteDTO> page = new PageImpl<>(Collections.singletonList(quoteDTO));
        when(quoteService.search(searchRequest)).thenReturn(page);

        mockMvc.perform(post("/v1/quote/search")
                        .content(asJsonString(searchRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testLikeQuote() throws Exception {
        Long quoteId = 1L;
        Long userId = 2L;

        mockMvc.perform(get("/v1/quote/like/{id}", quoteId)
                        .header("user_id", userId))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}