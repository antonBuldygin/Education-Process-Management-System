package ru.edu.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.actiongroups.CreateActionRequest;
import ru.edu.project.backend.api.actiongroups.SimpleAction;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.RecordSearch;
import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.groups.GroupForm;
import ru.edu.project.backend.api.groups.UpdateStatusRequest;
import ru.edu.project.backend.da.GroupsDALayer;
import ru.edu.project.backend.model.ActionType;
import ru.edu.project.backend.model.GroupStatus;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Profile("!STUB")
@Qualifier("GroupsServiceLayer")
public class GroupsServiceLayer implements GroupsService {

    /**
     * Зависимость для слоя доступа к данным заявок.
     */
    @Autowired
    private GroupsDALayer daLayer;

    /**
     * Зависимость для сервиса услуг.
     */
    @Autowired
    private TeacherServiceLayer teacherService;

    /**
     * Зависимость для сервиса действий.
     */
    @Autowired
    private ActionServiceLayer actionServiceLayer;

    /**
     * Получение заказов клиента.
     *
     * @return список
     */
    @Override
    public List<GroupInfo> getAllGroupsInfo() {
        return daLayer.getAllGroupsInfo();
    }


    /**
     * Получение списка групп для учителя.
     *
     * @return список
     */

    @Override
    public List<GroupInfo> getAllGroupsByTeacher(final long jobId) {
        List<GroupInfo> groupInfo = daLayer.getByTeacherId(jobId);

        if (groupInfo == null) {
            throw new IllegalArgumentException("request for client not found");
        }

        return groupInfo;

    }

    /**
     * Получение детальной информации по заявке.
     *
     * @param id
     * @return запись
     */
    @Override
    public GroupInfo getDetailedInfo(final long id) {
        GroupInfo groupInfo = daLayer.getById(id);

        if (groupInfo == null) {
            throw new IllegalArgumentException("request for client not found");
        }

        groupInfo.setTeachers(teacherService.getByLink(id));
        groupInfo.setActionHistory(actionServiceLayer.searchByGroup(id));

        //подгрузка истории

        return groupInfo;
    }

    /**
     * Регистрация новой заявки.
     *
     * @param groupForm
     * @return запись
     */
    @Override
    public GroupInfo createGroup(final GroupForm groupForm) {

        GroupInfo draft = GroupInfo.builder()
                .currentDate(new Timestamp(new Date().getTime()))
                .createdAt(groupForm.getDateCreatedRf())
                .comment(groupForm.getComment())
                .lastActionAt(new Timestamp(new Date().getTime()))
                .status(GroupStatus.CREATED)
                .build();

        daLayer.save(draft);

        teacherService.link(draft.getId(), groupForm.getSelectedTeachers());

        actionServiceLayer.createAction(CreateActionRequest.builder()
                .requestId(draft.getId())
                .action(SimpleAction.builder()
                        .typeCode(ActionType.CREATED.getTypeCode())
                        .build())
                .build());

        draft.setTeachers(teacherService.getByIds(groupForm.getSelectedTeachers()));

        //создать действие "Создана заявка"

        return draft;
    }

    /**
     * удаление группы.
     *
     * @param id
     */
    @Override
    public void deleteGroup(final long id) {
         daLayer.deleteGroup(id);
    }

    /**
     * Метод для поиска заявок.
     *
     * @param recordSearch
     * @return list
     */
    @Override
    public PagedView<GroupInfo> searchRequests(final RecordSearch recordSearch) {
        return daLayer.search(recordSearch);
    }

    /**
     * Изменение в группе.
     *
     * @param updateStatusRequest
     * @return boolean
     */
    @Override
    public boolean updateStatus(final UpdateStatusRequest updateStatusRequest) {
        GroupInfo req = daLayer.getById(updateStatusRequest.getGroupId());

        //Синхронизируем статусы
        Status status = GroupStatus.byCode(updateStatusRequest.getStatus().getCode());

        if (req == null || status == null) {
            return false;
        }

        /*
         * проверяем условия перехода при необходимости
         */

        Long oldCode = req.getStatus().getCode();

        req.setStatus(status);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        req.setLastActionAt(timestamp);
        daLayer.save(req);

        actionServiceLayer.createAction(CreateActionRequest.builder()
                .requestId(req.getId())
                .action(SimpleAction.builder()
                        .typeCode(ActionType.STATUS_CHANGED.getTypeCode())
                        .time(timestamp)
                        .message(oldCode + " > " + status.getCode() + req.getStatus().getMessage())
                        .build())
                .build());

        return true;
    }
}
