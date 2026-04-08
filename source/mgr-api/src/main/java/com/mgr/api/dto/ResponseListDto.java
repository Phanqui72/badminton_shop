package com.mgr.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ResponseListDto<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<T> content;
    private Long totalElements;
    private Integer totalPages;
}
