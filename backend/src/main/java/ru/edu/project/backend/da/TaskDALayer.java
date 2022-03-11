package ru.edu.project.backend.da;

import ru.edu.project.backend.api.tasks.TaskInfo;

import java.sql.Timestamp;
import java.util.List;

public interface TaskDALayer {

    /**
     * Getting available tasks on current date.
     *
     * @param date
     * @param groupId
     * @return List<TaskInfo>
     */
    List<TaskInfo> getAvailable(Timestamp date, long groupId);

    /**
     * Saving task.
     *
     * @param draft
     * @return TaskInfo
     */
    TaskInfo save(TaskInfo draft);

    /**
     * Getting all tasks by group id.
     *
     * @param groupId
     * @return list of tasks
     */
    List<TaskInfo> getTasksByGroup(long groupId);

    /**
     * Getting all tasks.
     *
     * @return list of tasks
     */
    List<TaskInfo> getAllTasks();

    /**
     * Get task by id.
     *
     * @param id
     * @return список
     */
    TaskInfo getById(long id);

    /**
     * Delete task by id.
     *
     * @param id
     * @return int
     */
    int deleteById(long id);

}
