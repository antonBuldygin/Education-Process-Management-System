package ru.edu.project.backend.api.groups;


import ru.edu.project.backend.api.common.AcceptorArgument;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.RecordSearch;

import java.util.List;


public interface GroupsService {

    /**
     * Получение списка групп.
     *
     * @return список
     */
    List<GroupInfo> getAllGroupsInfo();

    /**
     * Получение списка групп для учителя.
     * @param jobId
     * @return список
     */
    List<GroupInfo> getAllGroupsByTeacher(long jobId);

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


    /**
     * Метод для поиска заявок.
     *
     * @param recordSearch
     * @return list
     */
    @AcceptorArgument
    PagedView<GroupInfo> searchRequests(RecordSearch recordSearch);

    /**
     * Изменение в группе.
     *
     * @param updateStatusRequest
     * @return boolean
     */
    @AcceptorArgument
    boolean updateStatus(UpdateStatusRequest updateStatusRequest);
}
