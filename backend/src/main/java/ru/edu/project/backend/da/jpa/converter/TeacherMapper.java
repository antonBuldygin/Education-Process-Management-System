package ru.edu.project.backend.da.jpa.converter;

import org.mapstruct.Mapper;
import ru.edu.project.backend.api.teachers.Teacher;
import ru.edu.project.backend.da.jpa.entity.TeacherEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    /**
     * Маппер TeacherEntity -> Teacher.
     *
     * @param entity
     * @return Teacher
     */
    Teacher map(TeacherEntity entity);

    /**
     * Маппер List<TeacherEntity> -> List<Teacher>.
     * @param ids
     * @return list Teacher
     */
    List<Teacher> map(Iterable<TeacherEntity> ids);
}
