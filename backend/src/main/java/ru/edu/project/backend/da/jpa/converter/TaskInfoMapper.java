package ru.edu.project.backend.da.jpa.converter;

import org.mapstruct.Mapper;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.da.jpa.entity.TaskEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskInfoMapper {

    /**
     * Маппер TaskEntity -> TaskInfo.
     *
     * @param entity
     * @return TaskInfo
     */
    TaskInfo map(TaskEntity entity);

    /**
     * Mapper TaskInfo -> TaskEntity.
     *
     * @param taskInfo
     * @return entity
     */
    TaskEntity map(TaskInfo taskInfo);

    /**
     * Маппер List<TaskEntity> -> List<taskInfo>.
     * @param ids
     * @return list job
     */
    List<TaskInfo> map(Iterable<TaskEntity> ids);
}
