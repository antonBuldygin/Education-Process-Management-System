package ru.edu.project.backend.api.common;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class SolutionSearch {

    /**
     * Default perPage.
     */
    public static final int DEFAULT_PER_PAGE = 10;

    /**
     * Default page.
     */
    public static final int DEFAULT_PAGE = 1;

    /**
     * Student id.
     */
    private long studentId;

    /**
     * Field sorting.
     */
    private String orderBy;

    /**
     * Asc sorting.
     */
    private boolean asc;


    /**
     * Page.
     */
    private int page;


    /**
     * Solutions per page.
     */
    private int perPage;

    /**
     * Fabric method.
     *
     * @param studentId
     * @param field
     * @return obj
     */
    public static SolutionSearch by(final long studentId, final String field) {
        return by(studentId, field, true, DEFAULT_PAGE, DEFAULT_PER_PAGE);
    }

    /**
     * Fabric method.
     *
     * @param studentId
     * @param field
     * @param direction
     * @return obj
     */
    public static SolutionSearch by(final long studentId, final String field, final boolean direction) {
        return by(studentId, field, direction, DEFAULT_PAGE, DEFAULT_PER_PAGE);
    }

    /**
     * Fabric method.
     *
     * @param studentId
     * @param field
     * @param direction
     * @param page
     * @return obj
     */
    public static SolutionSearch by(final long studentId, final String field, final boolean direction, final int page) {
        return by(studentId, field, direction, page, DEFAULT_PER_PAGE);
    }

    /**
     * Fabric method.
     *
     * @param studentId
     * @param field
     * @param direction
     * @param page
     * @param perPage
     * @return obj
     */
    public static SolutionSearch by(final long studentId, final String field, final boolean direction, final int page, final int perPage) {

        return SolutionSearch.builder()
                .studentId(studentId)
                .orderBy(field)
                .asc(direction)
                .page(page)
                .perPage(perPage)
                .build();
    }
}
