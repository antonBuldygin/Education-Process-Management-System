package ru.edu.project.backend.da.jpa.converter;

import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.model.SolutionStatus;

import javax.persistence.Converter;
import javax.persistence.AttributeConverter;

@Converter
public class SolutionStatusConverter implements AttributeConverter<Status, Long> {

    /**
     * Converting Status -> Long.
     *
     * @param status
     * @return long
     */
    @Override
    public Long convertToDatabaseColumn(final Status status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    /**
     * Converting Long -> SolutionStatus enum.
     *
     * @param longValue
     * @return status enum
     */
    @Override
    public Status convertToEntityAttribute(final Long longValue) {

        return SolutionStatus.byCode(longValue);
    }
}
