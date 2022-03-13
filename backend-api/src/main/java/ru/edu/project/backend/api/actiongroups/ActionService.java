package ru.edu.project.backend.api.actiongroups;

import ru.edu.project.backend.api.common.AcceptorArgument;

import java.util.List;

public interface ActionService {

    /**
     * Поиск действий по заявке.
     *
     * @param requestId
     * @return list
     */
    List<ActionInterface> searchByGroup(long requestId);

    /**
     * Создание действия у заявки.
     *
     * @param createActionRequest
     * @return list
     */
    @AcceptorArgument
    ActionInterface createAction(CreateActionRequest createActionRequest);

}
