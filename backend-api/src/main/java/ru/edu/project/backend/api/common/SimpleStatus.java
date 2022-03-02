package ru.edu.project.backend.api.common;

public class SimpleStatus implements Status {

    /**
     * String status.
     */
    private String status;

    /**
     * Constructor.
     *
     * @param str
     */
    public SimpleStatus(final String str) {
        this.status = str;
    }

    /**
     * Get status code.
     *
     * @return code
     */
    @Override
    public Long getCode() {
        return null;
    }

    /**
     * Get status message.
     *
     * @return code
     */
    @Override
    public String getMessage() {
        return status;
    }
}
