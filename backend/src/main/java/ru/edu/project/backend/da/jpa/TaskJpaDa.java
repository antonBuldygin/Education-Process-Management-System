package ru.edu.project.backend.da.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.da.TaskDALayer;
import ru.edu.project.backend.da.jpa.converter.TaskInfoMapper;
import ru.edu.project.backend.da.jpa.entity.TaskEntity;
import ru.edu.project.backend.da.jpa.repository.TaskEntityRepository;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Profile("SPRING_DATA")
public class TaskJpaDa implements TaskDALayer {

    /**
     * Repository for tasks.
     */
    @Autowired
    private TaskEntityRepository repo;

    /**
     * Mapper for tasks.
     */
    @Autowired
    private TaskInfoMapper mapper;

    /**
     * Getting available tasks on current date.
     *
     * @param date
     * @param groupId
     * @return List<TaskInfo>
     */
    @Override
    public List<TaskInfo> getAvailable(final Timestamp date, final long groupId) {
        //return mapper.map(repo.getAvailable(date, groupId));
        return null;
    }

    /**
     * Saving task.
     *
     * @param draft
     * @return TaskInfo
     */
    @Override
    public TaskInfo save(final TaskInfo draft) {
        TaskEntity entity = mapper.map(draft);

        TaskEntity saved = repo.save(entity);

        draft.setId(saved.getId());

        return mapper.map(saved);
    }

    /**
     * Getting all tasks by group id.
     *
     * @param groupId
     * @return list of tasks
     */
    @Override
    public List<TaskInfo> getTasksByGroup(final long groupId) {
        return mapper.map(repo.findByGroupId(groupId));
    }

    /**
     * Getting all tasks.
     *
     * @return list of tasks
     */
    @Override
    public List<TaskInfo> getAllTasks() {
        return mapper.map(repo.findAll());
    }

    /**
     * Get task by id.
     *
     * @param id
     * @return список
     */
    @Override
    public TaskInfo getById(final long id) {
        Optional<TaskEntity> entity = repo.findById(id);

        return entity.map(taskEntity -> mapper.map(taskEntity)).orElse(null);
    }

    /**
     * Delete task by id.
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
