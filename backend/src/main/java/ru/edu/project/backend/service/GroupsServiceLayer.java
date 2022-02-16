package ru.edu.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.groups.GroupForm;
import ru.edu.project.backend.da.GroupsDALayer;
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
     * Получение заказов клиента.
     *
     * @return список
     */
    @Override
    public List<GroupInfo> getAllGroupsInfo() {
        return daLayer.getAllGroupsInfo();
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
                .status(GroupStatus.CREATED)
                .build();

        daLayer.save(draft);

        teacherService.link(draft.getId(), groupForm.getSelectedTeachers());

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
}
