package com.nduyhai.rsexample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResponse<T> {
    private T data;
    private boolean hasNext;
    private Long nextCursor;
}