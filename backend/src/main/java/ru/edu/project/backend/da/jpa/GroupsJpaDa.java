package ru.edu.project.backend.da.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.da.GroupsDALayer;
import ru.edu.project.backend.model.GroupStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("SPRING_DATA")
public class GroupsJpaDa implements GroupsDALayer {
    /**
     * Получение списка заявок по clientId.
     *
     * @return list request
     */
    @Override
    public List<GroupInfo> getAllGroupsInfo() {
        List<GroupInfo> groups = new ArrayList<>();
        groups.add(GroupInfo.builder()
                        .id(1L)
                        .status(GroupStatus.byCode(1L))
                        .build());
        groups.add(GroupInfo.builder()
                .id(2L)
                .status(GroupStatus.byCode(2L))
                .build());
        return groups;
    }

    /**
     * Получение заявки по id.
     *
     * @param id
     * @return request
     */
    @Override
    public GroupInfo getById(final long id) {
        return GroupInfo.builder()
                .id(1L)
                .build();
    }

    /**
     * Сохранение (создание/обновление) заявки.
     *
     * @param draft
     * @return request
     */
    @Override
    public GroupInfo save(final GroupInfo draft) {
        return null;
    }

    /**
     * удаление группы по id.
     *
     * @param id
     */
    @Override
    public void deleteGroup(final long id) {

    }
}
