package ru.edu.project.backend.da.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Common {

    /** Getting Integer from result set.
     *
     * @param rs
     * @param columnLabel
     * @return Integer
     * @throws SQLException
     */
    public static Integer getInteger(final ResultSet rs, final String columnLabel) throws SQLException {
        return (rs.getString(columnLabel) != null) ? rs.getInt(columnLabel) : null;
    }

}
