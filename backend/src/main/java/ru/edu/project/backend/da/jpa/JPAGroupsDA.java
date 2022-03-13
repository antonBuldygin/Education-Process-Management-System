package ru.edu.project.backend.da.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.RecordSearch;
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.da.GroupsDALayer;
import ru.edu.project.backend.da.jpa.converter.GroupInfoMapper;
import ru.edu.project.backend.da.jpa.converter.TeacherLinkMapper;
import ru.edu.project.backend.da.jpa.entity.GroupsEntity;
import ru.edu.project.backend.da.jpa.entity.TeacherLinkEntity;
import ru.edu.project.backend.da.jpa.repository.GroupsEntityRepository;
import ru.edu.project.backend.da.jpa.repository.TeacherLinkEntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Profile("SPRING_DATA")
public class JPAGroupsDA implements GroupsDALayer {

    /**
     * Зависимость на репозиторий.
     */
    @Autowired
    private GroupsEntityRepository repo;

    /**
     * Зависимость на маппер.
     */
    @Autowired
    private GroupInfoMapper mapper;

    /**
     * Зависимость на маппер.
     */
    @Autowired
    private TeacherLinkMapper teacherLinkMapper;

    /**
     * Зависимость на репозиторий.
     */
    @Autowired
    private TeacherLinkEntityRepository linkRepo;

    /**
     * Получение списка заявок по clientId.
     */
    @Override
    public List<GroupInfo> getAllGroupsInfo() {
        return mapper.mapList(repo.findAll());
    }


    /**
     * Получение заявки по id.
     *
     * @param id
     * @return group
     */
    @Override
    public GroupInfo getById(final long id) {
        Optional<GroupsEntity> entity = repo.findById(id);
        return entity.map(groupsEntity -> mapper.map(groupsEntity)).orElse(null);
    }

    /**
     * Получение заявки по teacher_id.
     *
     * @param jobId
     * @return groupInfo
     */
    @Override
    public List<GroupInfo> getByTeacherId(final long jobId) {
        List<TeacherLinkEntity> teachEntities = linkRepo.findAllByPkTeacherId(jobId);
        List<GroupInfo> listOfGroupsForTeacher = new ArrayList<>();
        for (TeacherLinkEntity tchEntity : teachEntities) {

            Optional<GroupsEntity> entity = repo.findById(tchEntity.getGroupg().getId());

            listOfGroupsForTeacher.add(entity.map(groupsEntity -> mapper.map(groupsEntity)).orElse(null));


        }
        return listOfGroupsForTeacher;
    }

    /**
     * Сохранение (создание/обновление) заявки.
     *
     * @param draft
     * @return group
     */
    @Override
    public GroupInfo save(final GroupInfo draft) {
        GroupsEntity entity = mapper.map(draft);

        GroupsEntity saved = repo.save(entity);
        draft.setId(saved.getId());
        return mapper.map(saved);
    }


    /**
     * Поиск заявок.
     *
     * @param recordSearch
     * @return list
     */
    @Override
    public PagedView<GroupInfo> search(final RecordSearch recordSearch) {
        Sort.Direction direction = recordSearch.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(recordSearch.getPage(), recordSearch.getPerPage(), Sort.by(direction, recordSearch.getOrderBy()));

        Page<GroupsEntity> page = repo.findAll(pageRequest);

        return PagedView.<GroupInfo>builder()
                .elements(mapper.mapList(page.get().collect(Collectors.toList())))
                .page(recordSearch.getPage())
                .perPage(recordSearch.getPerPage())
                .total(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    /**
     * удаление группы.
     *
     * @param groupId
     */
    public void deleteGroup(final long groupId) {
        Optional<GroupsEntity> entity = repo.findById(groupId);
       List<TeacherLinkEntity> allByPkRequestId = linkRepo.findAllByPkRequestId(groupId);
        repo.delete(mapper.map(entity.map(groupsEntity -> mapper.map(groupsEntity)).orElse(null)));

        allByPkRequestId.forEach(t -> linkRepo.delete(t));
            }
}
