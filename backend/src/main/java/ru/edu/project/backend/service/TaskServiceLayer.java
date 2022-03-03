package ru.edu.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.tasks.TaskForm;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.api.tasks.TaskService;
import ru.edu.project.backend.da.TaskDALayer;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Profile("!STUB")
@Qualifier("TaskServiceLayer")
public class TaskServiceLayer implements TaskService {

    /**
     * Data access layer.
     */
    @Autowired
    private TaskDALayer daLayer;


    /**
     * Geting available tasks on current date.
     *
     * @param groupId
     * @return
     */
    @Override
    public List<TaskInfo> getAvailable(final long groupId) {

        return daLayer.getAvailable(new Timestamp(new Date().getTime()), groupId);
    }

    /**
     * Create new task.
     *
     * @param taskForm
     * @return TaskInfo
     */
    @Override
    public TaskInfo createTask(final TaskForm taskForm) {
        TaskInfo draft = TaskInfo.builder()
                .groupId(taskForm.getGroupId())
                .num(taskForm.getNum())
                .title(taskForm.getTitle())
                .text(taskForm.getText())
                .startDate(taskForm.getStartDate())
                .endDate(taskForm.getEndDate())
                .build();

        daLayer.save(draft);

        return draft;
    }

    /**
     * Edit task.
     *
     * @param taskForm
     * @return TaskInfo
     */
    @Override
    public TaskInfo editTask(final TaskForm taskForm) {
        TaskInfo draft = TaskInfo.builder()
                .id(taskForm.getId())
                .groupId(taskForm.getGroupId())
                .num(taskForm.getNum())
                .title(taskForm.getTitle())
                .text(taskForm.getText())
                .startDate(taskForm.getStartDate())
                .endDate(taskForm.getEndDate())
                .build();

        daLayer.save(draft);

        return draft;
    }

    /**
     * Getting all tasks by group id.
     *
     * @param groupId
     * @return list of tasks
     */
    @Override
    public List<TaskInfo> getTasksByGroup(final long groupId) {
        return daLayer.getTasksByGroup(groupId);
    }

    /**
     * Get task by id.
     *
     * @param id
     * @return список
     */
    @Override
    public TaskInfo getById(final long id) {
        return daLayer.getById(id);
    }

    /**
     * Delete task by id.
     *
     * @param id
     * @return TaskInfo
     */
    @Override
    public int deleteById(final long id) {
        return daLayer.deleteById(id);
    }
}
