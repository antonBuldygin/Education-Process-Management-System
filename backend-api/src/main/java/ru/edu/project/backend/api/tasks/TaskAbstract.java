package ru.edu.project.backend.api.tasks;

public interface TaskAbstract {

        /**
         * Task id.
         *
         * @return Long
         */
        Long getId();

        /**
         * Group id.
         *
         * @return Long
         */
        Long getGroupId();

        /** Task number.
         *
         * @return Integer
         */
        Integer getNum();

        /**
         * Task title.
         *
         * @return String
         */
        String getTitle();

        /**
         * Task text.
         *
         * @return String
         */
        String getText();

        /**
         * Start date of task.
         *
         * @return Timestamp
         */
        java.sql.Timestamp getStartDate();

        /**
         * End date of task.
         *
         * @return Timestamp
         */
        java.sql.Timestamp getEndDate();

}
