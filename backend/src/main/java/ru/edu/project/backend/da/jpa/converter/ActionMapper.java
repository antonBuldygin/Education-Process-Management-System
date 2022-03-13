package ru.edu.project.backend.da.jpa.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.edu.project.backend.api.actiongroups.ActionInterface;
import ru.edu.project.backend.api.actiongroups.SimpleAction;
import ru.edu.project.backend.da.jpa.entity.ActionEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActionMapper {

    /**
     * Маппинг List<Entity> -> List<ActionInterface>.
     *
     * @param list
     * @return list
     */
    List<ActionInterface> map(List<ActionEntity> list);

    /**
     * Маппинг Entity -> ActionInterface.
     *
     * @param entity
     * @return action
     */
    @Mapping(source = "pk.actionId", target = "typeCode")
    @Mapping(expression = "java(ru.edu.project.backend.model.ActionType.messageByCode(entity.getPk().getActionId()))", target = "typeMessage")
    @Mapping(source = "pk.time", target = "time")
    SimpleAction map(ActionEntity entity);

    /**
     * Маппинг ActionInterface -> Entity.
     *
     * @param action
     * @return entity
     */
    @Mapping(source = "typeCode", target = "pk.actionId")
    @Mapping(source = "time", target = "pk.time")
    ActionEntity map(ActionInterface action);
}
