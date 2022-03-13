package ru.edu.project.backend.da;

import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.RecordSearch;
import ru.edu.project.backend.api.groups.GroupInfo;

import java.util.List;


public interface GroupsDALayer {

    /**
     * Получение списка заявок по clientId.
     *
     * @return list request
     */
    List<GroupInfo> getAllGroupsInfo();

    /**
     * Получение заявки по id.
     *
     * @param id
     * @return request
     */
    GroupInfo getById(long id);

    /**
     * Сохранение (создание/обновление) заявки.
     *
     * @param draft
     * @return request
     */
    GroupInfo save(GroupInfo draft);
    /**
     *удаление группы по id.
     *@param id
     */
    void deleteGroup(long id);
    /**
     * Поиск заявок.
     *
     * @param recordSearch
     * @return list
     */
    PagedView<GroupInfo> search(RecordSearch recordSearch);

    /**
     * Получение заявки по teacher_id.
     *
     * @param jobId
     * @return groupInfo
     */

    List<GroupInfo> getByTeacherId(long jobId);


}

