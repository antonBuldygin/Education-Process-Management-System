package ru.edu.project.backend.da.jpa.converter;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.da.jpa.entity.SolutionActionEntity;


import java.util.List;

@Mapper(componentModel = "spring")
public interface SolutionActionMapper {

    /**
     * SolutionInfo -> SolutionActionEntity.
     *
     * @param solutionInfo
     * @return SolutionActionEntity
     */
    @Mapping(source = "id", target = "pk.solutionId")
    @Mapping(expression = "java(solutionInfo.getStatus().getCode())", target = "pk.actionTypeId")
    @Mapping(source = "lastActionTime", target = "actionTime")
    SolutionActionEntity map(SolutionInfo solutionInfo);

    /**
     * SolutionActionEntity -> Action.
     *
     * @param solutionActionEntity
     * @return Action
     */
    @Mapping(source = "comment", target = "comment")
    @Mapping(expression = "java(ru.edu.project.backend.model.SolutionStatus.byCode(solutionActionEntity.getPk().getActionTypeId()))", target = "status")
    @Mapping(source = "actionTime", target = "time")
    Action map(SolutionActionEntity solutionActionEntity);

    /**
     * Mapping List<Entity> -> List<Action>.
     *
     * @param listEntity
     * @return list Action
     */
    List<Action> mapList(List<SolutionActionEntity> listEntity);
}
