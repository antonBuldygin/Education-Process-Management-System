package ru.edu.project.backend.api.common;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Builder
@Jacksonized
public class PagedView<T> {

    /**
     * Elements.
     */
    private List<T> elements;

    /**
     * Total count of elements.
     */
    private long total;

    /**
     * Total count of pages.
     */
    private int totalPages;

    /**
     * Current page.
     */
    private int page;

    /**
     * Elements per page.
     */
    private int perPage;

}
