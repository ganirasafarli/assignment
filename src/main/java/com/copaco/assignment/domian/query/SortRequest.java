package com.copaco.assignment.domian.query;

import com.copaco.assignment.domian.enumeration.SortDirection;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SortRequest implements Serializable {
    private String key;
    private SortDirection direction;
}