package ru.edu.project.backend.da.jpa.converter;

import org.mapstruct.Mapper;
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.da.jpa.entity.GroupsEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupInfoMapper {
    /**
     * Маппер GroupInfo -> GroupsEntity.
     *
     * @param requestInfo
     * @return GroupsEntity
     */
    GroupsEntity map(GroupInfo requestInfo);

    /**
     * Маппер GroupsEntity -> GroupInfo.
     *
     * @param entity
     * @return requestInfo
     */
    GroupInfo map(GroupsEntity entity);

    /**
     * Маппер List<GroupsEntity> -> List<GroupInfo>.
     *
     * @param listEntity
     * @return list GroupInfo
     */
    List<GroupInfo> mapList(List<GroupsEntity> listEntity);


}
