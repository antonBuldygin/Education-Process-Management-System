package ru.edu.project.backend.da.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.SolutionSearch;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.da.SolutionDALayer;
import ru.edu.project.backend.da.jpa.converter.SolutionActionMapper;
import ru.edu.project.backend.da.jpa.converter.SolutionInfoMapper;
import ru.edu.project.backend.da.jpa.entity.SolutionActionEntity;
import ru.edu.project.backend.da.jpa.entity.SolutionEntity;
import ru.edu.project.backend.da.jpa.repository.SolutionActionEntityRepository;
import ru.edu.project.backend.da.jpa.repository.SolutionEntityRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Profile("SPRING_DATA")
public class SolutionJpaDa implements SolutionDALayer {

    /**
     * Repository for solutions.
     */
    @Autowired
    private SolutionEntityRepository repo;

    /**
     * Mapper for solutions.
     */
    @Autowired
    private SolutionInfoMapper mapper;

    /**
     * Mapper for solution action link.
     */
    @Autowired
    private SolutionActionMapper linkMapper;

    /**
     * Repository for link of solution and action.
     */
    @Autowired
    private SolutionActionEntityRepository linkRepo;

    /**
     * Getting solutions by student id.
     *
     * @param studentId
     * @return SolutionInfo
     */
    @Override
    public List<SolutionInfo> getSolutionsByStudent(final long studentId) {
        return mapper.mapList(repo.findByStudentId(studentId));
    }

    /**
     * Searching for solutions.
     *
     * @param recordSearch
     * @return list
     */
    @Override
    public PagedView<SolutionInfo> search(final SolutionSearch recordSearch) {
        Sort.Direction direction = recordSearch.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(recordSearch.getPage(), recordSearch.getPerPage(), Sort.by(direction, recordSearch.getOrderBy()));

        Page<SolutionEntity> page;

        if (recordSearch.getStudentId() == null) {
            page = repo.findAll(pageRequest);
        } else {
            page = repo.findByStudentId(recordSearch.getStudentId(), pageRequest);
        }
        return PagedView.<SolutionInfo>builder()
                .elements(mapper.mapList(page.get().collect(Collectors.toList())))
                .page(recordSearch.getPage())
                .perPage(recordSearch.getPerPage())
                .total(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    /**
     * Getting student's solution by task id.
     *
     * @param studentId
     * @param taskId
     * @return list of solutions
     */
    @Override
    public SolutionInfo getSolutionByStudentAndTask(final long studentId, final long taskId) {
        return mapper.map(repo.findByStudentIdAndTaskId(studentId, taskId));
    }

    /**
     * Getting solutions by task id.
     *
     * @param taskId
     * @return list of solutions
     */
    @Override
    public List<SolutionInfo> getSolutionsByTask(final long taskId) {
        return mapper.mapList(repo.findByTaskId(taskId));
    }

    /**
     * Getting solution by id.
     *
     * @param id
     * @return SolutionInfo
     */
    @Override
    public SolutionInfo getById(final long id) {
        Optional<SolutionEntity> entity = repo.findById(id);
        return entity.map(solutionEntity -> mapper.map(solutionEntity)).orElse(null);
    }

    /**
     * Inserting/updating solution.
     *
     * @param solutionInfo
     * @return SolutionInfo
     */
    @Override
    public SolutionInfo save(final SolutionInfo solutionInfo) {
        SolutionEntity entity = mapper.map(solutionInfo);

        SolutionEntity saved = repo.save(entity);

        return mapper.map(saved);
    }

    /**
     * Create solution action link.
     *
     * @param solutionInfo
     * @param comment
     */
    @Override
    public void doAction(final SolutionInfo solutionInfo, final String comment) {
        SolutionActionEntity entityDraft = new SolutionActionEntity();
        entityDraft.setPk(SolutionActionEntity.pk(solutionInfo.getId(), solutionInfo.getStatus().getCode()));
        entityDraft.setComment(comment);
        entityDraft.setActionTime(solutionInfo.getLastActionTime());
        linkRepo.save(entityDraft);
    }

    /**
     * Getting list of actions by solution id.
     *
     * @param solutionId
     * @return List<ActionInterface>
     */
    @Override
    public List<Action> getActionsBySolution(final long solutionId) {
         return linkMapper.mapList(linkRepo.findAllByPkSolutionId(solutionId));
    }

    /**
     * Update solution action link.
     *
     * @param solutionInfo
     * @param comment
     */
    @Override
    public void updateAction(final SolutionInfo solutionInfo, final String comment) {

        SolutionActionEntity entity = linkMapper.map(solutionInfo);
        entity.setComment(comment);

        SolutionActionEntity saved = linkRepo.save(entity);

    }

    /**
     * Getting all solutions.
     *
     * @return list of solutions
     */
    @Override
    public List<SolutionInfo> getAllSolutions() {
        return mapper.map(repo.findAll());
    }
}
