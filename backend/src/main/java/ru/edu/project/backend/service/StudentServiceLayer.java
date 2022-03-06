package ru.edu.project.backend.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.students.StudentService;


@Service
@Profile("!STUB")
@Qualifier("StudentServiceLayer")
public class StudentServiceLayer implements StudentService {

    /**
     * Get student by id.
     *
     * @param id
     * @return StudentInfo
     */
    @Override
    public StudentInfo getById(final long id) {
        return StudentInfo.builder()
                .firstName("Иван")
                .secondName("Иванов")
                .middleName("Иванович")
                .groupId(1L)
                .build();
    }
}
