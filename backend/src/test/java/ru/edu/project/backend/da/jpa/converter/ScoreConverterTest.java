package ru.edu.project.backend.da.jpa.converter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import ru.edu.project.backend.api.common.Score;
import ru.edu.project.backend.service.SolutionServiceLayer;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ScoreConverterTest {


    public static final Integer INT_VALUE = 50;

    @InjectMocks
    ScoreConverter scoreConverter;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void convertToDatabaseColumn() {
        Score score = new Score(INT_VALUE);

        Integer result = scoreConverter.convertToDatabaseColumn(score);

        assertEquals(INT_VALUE, result);
    }

    @Test
    public void convertToDatabaseColumnNull() {
        Integer result = scoreConverter.convertToDatabaseColumn(null);

        assertNull(result);
    }

    @Test
    public void convertToEntityAttribute() {

        Score result = scoreConverter.convertToEntityAttribute(INT_VALUE);

        assertEquals(INT_VALUE, result.getValue());
    }

}