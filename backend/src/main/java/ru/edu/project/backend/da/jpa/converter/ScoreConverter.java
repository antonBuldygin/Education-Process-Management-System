package ru.edu.project.backend.da.jpa.converter;

import ru.edu.project.backend.api.common.Score;


import javax.persistence.Converter;
import javax.persistence.AttributeConverter;

@Converter
public class ScoreConverter implements AttributeConverter<Score, Integer> {

    /**
     * Конвертация Score -> Long.
     *
     * @param score
     * @return Integer
     */
    @Override
    public Integer convertToDatabaseColumn(final Score score) {
        if (score == null) {
            return null;
        }
        return score.getValue();
    }

    /**
     * Конвертация Integer -> Score value.
     *
     * @param integerValue
     * @return status enum
     */
    @Override
    public Score convertToEntityAttribute(final Integer integerValue) {
        return new Score(integerValue);
    }
}
