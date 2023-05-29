package com.copaco.assignment.domian.query;

import com.copaco.assignment.domian.enumeration.Operator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterRequest implements Serializable {
    private String key;

    private Operator operator;

    private String value;

    private List<String> values;

}