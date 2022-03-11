package ru.edu.project.backend.da.jpa.converter;

import org.mapstruct.Mapper;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.da.jpa.entity.StudentEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentInfoMapper {

    /**
     * Маппер StudentEntity -> StudentInfo.
     *
     * @param entity
     * @return StudentInfo
     */
    StudentInfo map(StudentEntity entity);

    /**
     * Mapper StudentInfo -> StudentEntity.
     *
     * @param taskInfo
     * @return entity
     */
    StudentEntity map(StudentInfo taskInfo);

    /**
     * Маппер List<StudentEntity> -> List<taskInfo>.
     * @param ids
     * @return list job
     */
    List<StudentInfo> map(Iterable<StudentEntity> ids);
}
