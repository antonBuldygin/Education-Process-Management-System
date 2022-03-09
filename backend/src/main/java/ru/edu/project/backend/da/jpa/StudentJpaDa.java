package ru.edu.project.backend.da.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.da.StudentDALayer;
import ru.edu.project.backend.da.jpa.converter.StudentInfoMapper;
import ru.edu.project.backend.da.jpa.entity.StudentEntity;
import ru.edu.project.backend.da.jpa.repository.StudentEntityRepository;

import java.util.List;
import java.util.Optional;

@Service
@Profile("SPRING_DATA")
public class StudentJpaDa implements StudentDALayer {

    /**
     * Repository for students.
     */
    @Autowired
    private StudentEntityRepository repo;

    /**
     * Mapper for students.
     */
    @Autowired
    private StudentInfoMapper mapper;

    /**
     * Saving student.
     *
     * @param draft
     * @return StudentInfo
     */
    @Override
    public StudentInfo save(final StudentInfo draft) {
        StudentEntity entity = mapper.map(draft);

        StudentEntity saved = repo.save(entity);

        draft.setId(saved.getId());

        return mapper.map(saved);
    }


    /**
     * Getting all students.
     *
     * @return list of students
     */
    @Override
    public List<StudentInfo> getAllStudents() {
        return mapper.map(repo.findAll());
    }

    /**
     * Get student by id.
     *
     * @param id
     * @return список
     */
    @Override
    public StudentInfo getById(final long id) {
        Optional<StudentEntity> entity = repo.findById(id);

        return entity.map(studentEntity -> mapper.map(studentEntity)).orElse(null);
    }

    /**
     * Delete student by id.
     *
     * @param id
     * @return int
     */
    @Override
    public int deleteById(final long id) {
        repo.deleteById(id);
        return 1;
    }
}
