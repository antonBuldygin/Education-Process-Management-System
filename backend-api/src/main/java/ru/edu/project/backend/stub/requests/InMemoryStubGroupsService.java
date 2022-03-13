package ru.edu.project.backend.stub.requests;


import ru.edu.project.backend.api.actiongroups.SimpleAction;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.RecordSearch;
import ru.edu.project.backend.api.common.StatusImpl;
import ru.edu.project.backend.api.groups.GroupForm;
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.groups.UpdateStatusRequest;
import ru.edu.project.backend.api.teachers.Teacher;
import ru.edu.project.backend.api.teachers.TeacherService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static java.util.Arrays.asList;

public class InMemoryStubGroupsService implements GroupsService {

    /**
     * Ссылка на сервис услуг.
     */
    private TeacherService servicesService;

    /**
     * Локальное хранилище заявок в RAM.
     */
    private List<GroupInfo> db = new CopyOnWriteArrayList();

    /**
     * Локальный счетчик заявок.
     */
    private AtomicLong idCount = new AtomicLong(0);


    /**
     * Конструктор с зависимостью.
     *
     * @param bean
     */
    public InMemoryStubGroupsService(final TeacherService bean) {
        servicesService = bean;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<GroupInfo> getAllGroupsInfo() {
        if (db.isEmpty()) {
            return Collections.emptyList();
        }
        return db;
    }

    /**
     * Получение списка групп для учителя.
     *
     * @return список
     */

    @Override
    public List<GroupInfo> getAllGroupsByTeacher(final long jobId) {
        List<GroupInfo> listOfGroupsForTeacher = new ArrayList<>();
        for (GroupInfo gip
                : db) {
            for (Teacher tch
                    : gip.getTeachers()) {
                if (tch.getId() == jobId) {
                    listOfGroupsForTeacher.add(gip);

                } else {
                    continue;
                }

            }
        }
        return listOfGroupsForTeacher;
    }


    /**
     * @inheritDoc
     */
    @Override
    public GroupInfo getDetailedInfo(final long id) {
        for (GroupInfo gdt : db) {
            if (gdt.getId() != id) {
                continue;
            } else {
                return gdt;
            }
        }
        return null;
    }


    /**
     * @inheritDoc
     */
    @Override
    public GroupInfo createGroup(final GroupForm groupForm) {

        GroupInfo info = GroupInfo.builder()
                .id(idCount.addAndGet(1))
                .currentDate(new Timestamp(new Date().getTime()))
                .createdAt(groupForm.getDateCreatedRf())
                .status(StatusImpl.builder()
                        .code(1L)
                        .message("Создана")
                        .build())
                .comment(groupForm.getComment())
                .teachers(getJobsById(groupForm))
                .actionHistory(asList(SimpleAction.builder()
                        .time(new Timestamp(new Date().getTime()))
                        .typeCode(1L)
                        .typeMessage("Создание")
                        .message("Группа создана")
                        .build()))
                .build();


        db.add(info);

        return info;
    }

    /**
     * удаление группы.
     *
     * @param id
     */
    @Override
    public void deleteGroup(final long id) {

        Iterator<GroupInfo> it = db.iterator();

        while (it.hasNext()) {

            GroupInfo str = it.next();
            if (str.getId() == id) {
                db.remove(str);
            }
        }
    }

    /**
     * Метод для поиска заявок.
     *
     * @param recordSearch
     * @return list
     */
    @Override
    public PagedView<GroupInfo> searchRequests(final RecordSearch recordSearch) {

        Stream<GroupInfo> searchStream = db.stream();


        if ("createdAt".equals(recordSearch.getOrderBy())) {
            searchStream = searchStream.sorted((r1, r2) -> (recordSearch.isAsc() ? 1 : -1) * r1.getCreatedAt().compareTo(r2.getCreatedAt()));
        }

        if (recordSearch.getPage() > 1) {
            searchStream = searchStream.skip(recordSearch.getPerPage() * recordSearch.getPerPage() - 1);
        }

        return PagedView.<GroupInfo>builder()
                .page(recordSearch.getPage())
                .perPage(recordSearch.getPerPage())
                .totalPages(Long.valueOf(idCount.get() / recordSearch.getPerPage()).intValue())//
                .total(idCount.get())
                .elements(
                        searchStream
                                .limit(recordSearch.getPerPage())
                                .collect(Collectors.toList())
                )
                .build();
    }

    private List<Teacher> getJobsById(final GroupForm groupForm) {
        return servicesService.getByIds(groupForm.getSelectedTeachers());
    }

    /**
     * Изменение в группе.
     *
     * @param updateStatusRequest
     * @return boolean
     */
    @Override
    public boolean updateStatus(final UpdateStatusRequest updateStatusRequest) {

        GroupInfo groupInfo = getDetailedInfo(updateStatusRequest.getGroupId());

        if (groupInfo == null) {
            return false;
        }

        groupInfo.setStatus(updateStatusRequest.getStatus());

        return true;
    }
}


