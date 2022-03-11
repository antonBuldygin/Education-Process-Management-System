package ru.edu.project.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.edu.project.backend.api.tasks.TaskForm;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.api.tasks.TaskService;
import ru.edu.project.backend.service.TaskServiceLayer;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController implements TaskService {

    /**
     * Delegate for task service layer.
     */
    @Autowired
    private TaskServiceLayer delegate;


    /**
     * Getting available tasks on current date.
     *
     * @param groupId
     * @return
     */
    @Override
    @GetMapping("/getAvailable/{id}")
    public List<TaskInfo> getAvailable(@PathVariable("id") final long groupId) {
        return delegate.getAvailable(groupId);
    }

    /**
     * Create new task.
     *
     * @param taskForm
     * @return TaskInfo
     */
    @Override
    @PostMapping("/createTask")
    public TaskInfo createTask(@RequestBody final TaskForm taskForm) {
        return delegate.createTask(taskForm);
    }

    /**
     * Edit task.
     *
     * @param taskForm
     * @return TaskInfo
     */
    @Override
    @PostMapping("/editTask")
    public TaskInfo editTask(@RequestBody final TaskForm taskForm) {
        return delegate.editTask(taskForm);
    }


    /**
     * Getting all tasks by group id.
     *
     * @param groupId
     * @return list of tasks
     */
    @Override
    @GetMapping("/getTasksByGroup/{id}")
    public List<TaskInfo> getTasksByGroup(@PathVariable("id") final long groupId) {
        return delegate.getTasksByGroup(groupId);
    }

    /**
     * Getting all tasks.
     *
     * @return list of tasks
     */
    @Override
    @GetMapping("/getAllTasks")
    public List<TaskInfo> getAllTasks() {
        return delegate.getAllTasks();
    }

    /**
     * Get task by id.
     *
     * @param id
     * @return список
     */
    @Override
    @GetMapping("/getById/{id}")
    public TaskInfo getById(@PathVariable("id") final long id) {
        return delegate.getById(id);
    }

    /**
     * Delete task by id.
     *
     * @param id
     * @return TaskInfo
     */
    @Override
    @GetMapping("/deleteById/{id}")
    public int deleteById(@PathVariable("id") final long id) {
        return delegate.deleteById(id);
    }
}
