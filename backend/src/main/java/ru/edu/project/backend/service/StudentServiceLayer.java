package ru.edu.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.students.StudentForm;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.students.StudentService;
import ru.edu.project.backend.da.StudentDALayer;

import java.util.List;

@Service
@Profile("!STUB")
@Qualifier("StudentServiceLayer")
public class StudentServiceLayer implements StudentService {

    /**
     * Data access layer.
     */
    @Autowired
    private StudentDALayer daLayer;


    /**
     * Create new student.
     *
     * @param studentForm
     * @return StudentInfo
     */
    @Override
    public StudentInfo createStudent(final StudentForm studentForm) {
        StudentInfo draft = StudentInfo.builder()
                .groupId(studentForm.getGroupId())
                .firstName(studentForm.getFirstName())
                .lastName(studentForm.getLastName())
                .birthday(studentForm.getBirthday())
                .email(studentForm.getEmail())
                .phone(studentForm.getPhone())
                .build();

        daLayer.save(draft);

        return draft;
    }

    /**
     * Edit student.
     *
     * @param studentForm
     * @return StudentInfo
     */
    @Override
    public StudentInfo editStudent(final StudentForm studentForm) {
        StudentInfo draft = StudentInfo.builder()
                .id(studentForm.getId())
                .groupId(studentForm.getGroupId())
                .firstName(studentForm.getFirstName())
                .lastName(studentForm.getLastName())
                .birthday(studentForm.getBirthday())
                .email(studentForm.getEmail())
                .phone(studentForm.getPhone())
                .build();

        daLayer.save(draft);

        return draft;
    }

    /**
     * Get student by id.
     *
     * @param id
     * @return список
     */
    @Override
    public StudentInfo getById(final long id) {
        return daLayer.getById(id);
    }

    /**
     * Delete student by id.
     *
     * @param id
     * @return StudentInfo
     */
    @Override
    public int deleteById(final long id) {
        return daLayer.deleteById(id);
    }

    /**
     * Getting all students.
     *
     * @return list of tasks
     */
    @Override
    public List<StudentInfo> getAllStudents() {
        return daLayer.getAllStudents();
    }
}

