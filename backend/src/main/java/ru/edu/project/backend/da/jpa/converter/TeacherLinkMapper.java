package ru.edu.project.backend.da.jpa.converter;

import org.mapstruct.Mapper;
import ru.edu.project.backend.api.teachers.TeacherLink;
import ru.edu.project.backend.da.jpa.entity.TeacherLinkEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherLinkMapper {

    /**
     * Маппер TeacherLink -> TeacherLinkEntity.
     *
     * @param teacherLink
     * @return TeacherLinkEntity
     */
    TeacherLinkEntity map(TeacherLink teacherLink);

    /**
     * Маппер TeacherLinkEntity -> TeacherLink.
     *
     * @param entity
     * @return TeacherLink
     */
    TeacherLink map(TeacherLinkEntity entity);

    /**
     * Маппер List<TeacherLinkEntity> -> List<TeacherLink>.
     *
     * @param listEntity
     * @return list GroupInfo
     */
    List<TeacherLink> mapList(List<TeacherLinkEntity> listEntity);


}
