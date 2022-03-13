package ru.edu.project.backend.da.jpa.converter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.model.SolutionStatus;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class SolutionStatusConverterTest {

    public static final Long LONG_VALUE = 2l;
    @InjectMocks
    SolutionStatusConverter solutionStatusConverter;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void convertToDatabaseColumn() {
        SolutionStatus status = SolutionStatus.CHECKED;

        Long result = solutionStatusConverter.convertToDatabaseColumn(status);

        assertEquals(SolutionStatus.CHECKED.getCode(), result);
    }

    @Test
    public void convertToDatabaseColumnNull() {
        Long result = solutionStatusConverter.convertToDatabaseColumn(null);

        assertNull(result);
    }

    @Test
    public void convertToEntityAttribute() {

        Status result = solutionStatusConverter.convertToEntityAttribute(LONG_VALUE);

        assertEquals(LONG_VALUE, result.getCode());
    }
}