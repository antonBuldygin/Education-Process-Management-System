package ru.edu.project.backend.da;

import ru.edu.project.backend.api.actiongroups.ActionInterface;

import java.util.List;

public interface ActionDALayer {
    /**
     * Поиск действий по заявке.
     *
     * @param groupId
     * @return list
     */
    List<ActionInterface> findByRequest(long groupId);

    /**
     * Создание действия по заявке.
     *
     * @param groupId
     * @param build
     * @return action
     */
    ActionInterface save(long groupId, ActionInterface build);
}
