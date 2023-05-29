package com.copaco.assignment.domian.repo;

import com.copaco.assignment.domian.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuoteRepository extends JpaRepository<Quote, Long>,
        JpaSpecificationExecutor<Quote> {
}

