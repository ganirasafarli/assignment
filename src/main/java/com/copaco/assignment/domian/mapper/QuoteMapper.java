package com.copaco.assignment.domian.mapper;

import com.copaco.assignment.domian.dto.QuoteDTO;
import com.copaco.assignment.domian.entity.Quote;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuoteMapper {

    QuoteDTO toDto(Quote quote);

    Quote toEntity(QuoteDTO quote);

    List<QuoteDTO> toDtoList(List<Quote> quotes);

    List<Quote> toEntityList(List<QuoteDTO> quotes);
}
