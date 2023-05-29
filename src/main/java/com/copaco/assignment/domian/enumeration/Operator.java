package com.copaco.assignment.domian.enumeration;


import com.copaco.assignment.domian.entity.Quote;
import com.copaco.assignment.domian.query.FilterRequest;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public enum Operator {

    EQUAL {
        public Predicate build(Root<Quote> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Expression<?> key = root.get(request.getKey());
            return cb.and(cb.equal(key, request.getValue()), predicate);
        }
    },
    NOT_EQUAL {
        public Predicate build(Root<Quote> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Expression<?> key = root.get(request.getKey());
            return cb.and(cb.notEqual(key, request.getValue()), predicate);
        }
    },
    LIKE {
        public Predicate build(Root<Quote> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Expression<String> key = root.get(request.getKey());
            return cb.and(cb.like(cb.upper(key), "%" + request.getValue().toString().toUpperCase() + "%"), predicate);
        }
    },
    IN {
        public Predicate build(Root<Quote> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            List<String> values = request.getValues();
            CriteriaBuilder.In<Object> inClause = cb.in(root.get(request.getKey()));
            for (String value : values) {
                inClause.value(value);
            }
            return cb.and(inClause, predicate);
        }
    },
    HAS {
        public Predicate build(Root<Quote> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            List<String> values = request.getValues();
            return cb.and(root.join(request.getKey()).in(values), predicate);
        }
    };

    public abstract Predicate build(Root<Quote> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate);

}