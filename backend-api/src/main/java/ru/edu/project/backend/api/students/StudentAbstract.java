package ru.edu.project.backend.api.students;

public interface StudentAbstract {

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

        /** firstName.
         *
         * @return String
         */
        String getFirstName();

        /**
         * lastName.
         *
         * @return String
         */
        String getLastName();

        /**
         * email.
         *
         * @return String
         */
        String getEmail();

        /**
         * birthday.
         *
         * @return Timestamp
         */
        java.sql.Timestamp getBirthday();

        /**
         * phone.
         *
         * @return String
         */
        String getPhone();

}
