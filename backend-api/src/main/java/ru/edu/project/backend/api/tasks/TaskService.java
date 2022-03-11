package ru.edu.project.backend.api.tasks;

import ru.edu.project.backend.api.common.AcceptorArgument;

import java.util.List;

public interface TaskService {

    /**
     * Getting available tasks on current date.
     * @param groupId
     * @return List<TaskInfo>
     */
    List<TaskInfo> getAvailable(long groupId);


    /**
     * Create new task.
     *
     * @param taskForm
     * @return TaskInfo
     */
    @AcceptorArgument
    TaskInfo createTask(TaskForm taskForm);

    /**
     * Edit task.
     *
     * @param taskForm
     * @return TaskInfo
     */
    @AcceptorArgument
    TaskInfo editTask(TaskForm taskForm);

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
     * @return TaskInfo
     */
    TaskInfo getById(long id);

    /**
     * Delete task by id.
     *
     * @param id
     * @return TaskInfo
     */
    int deleteById(long id);


}
