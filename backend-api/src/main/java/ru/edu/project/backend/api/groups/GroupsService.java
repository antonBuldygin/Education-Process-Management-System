package ru.edu.project.backend.api.groups;


import ru.edu.project.backend.api.common.AcceptorArgument;

import java.util.List;


public interface GroupsService {

    /**
     * Получение списка групп.
     *
     * @return список
     */
    List<GroupInfo> getAllGroupsInfo();

    /**
     * Получение детальной информации по группе.
     *
     * @param id
     * @return запись
     */
    GroupInfo getDetailedInfo(long id);

    /**
     * Создание новой группы.
     *
     * @param groupForm
     * @return запись
     */
    @AcceptorArgument
    GroupInfo createGroup(GroupForm groupForm);


    /**
     * удаление группы.
     *
     * @param id
     */

    void deleteGroup(long id);
}
