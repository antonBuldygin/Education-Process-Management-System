package ru.edu.project.backend.da.jpa.converter;

import org.mapstruct.Mapper;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.da.jpa.entity.SolutionEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SolutionInfoMapper {

    /**
     * SolutionInfo -> SolutionEntity.
     *
     * @param solutionInfo
     * @return entity
     */
    SolutionEntity map(SolutionInfo solutionInfo);

    /**
     * SolutionEntity -> SolutionInfo.
     *
     * @param entity
     * @return solutionInfo
     */
    SolutionInfo map(SolutionEntity entity);

    /**
     * List<SolutionEntity> -> List<SolutionInfo>.
     *
     * @param listEntity
     * @return list solutionInfo
     */
    List<SolutionInfo> mapList(List<SolutionEntity> listEntity);

    /**
     * Маппер List<SolutionEntity> -> List<SolutionInfo>.
     * @param ids
     * @return list job
     */
    List<SolutionInfo> map(Iterable<SolutionEntity> ids);


}
