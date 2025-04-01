package com.uranium.drilling.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {

    public Pageable createPageRequest(int page, int size, List<String> sortParams) {
        Sort sort = Sort.by("id"); // По умолчанию сортировка по ID

        if (sortParams != null && !sortParams.isEmpty()) {
            List<Sort.Order> orders = sortParams.stream()
                    .map(param -> {
                        String[] parts = param.split(":");
                        String field = parts[0];
                        Sort.Direction direction = parts.length > 1 && "desc".equalsIgnoreCase(parts[1])
                                ? Sort.Direction.DESC
                                : Sort.Direction.ASC;
                        return new Sort.Order(direction, field);
                    })
                    .toList();
            sort = Sort.by(orders);
        }

        return PageRequest.of(page, size, sort);
    }
}
