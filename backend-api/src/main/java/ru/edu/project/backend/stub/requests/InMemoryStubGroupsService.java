package ru.edu.project.backend.stub.requests;

import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.StatusImpl;
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.api.groups.GroupForm;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.teachers.Teacher;
import ru.edu.project.backend.api.teachers.TeacherService;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

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
                .actionHistory(asList(Action.builder()
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

//        for (GroupInfo gi: db
//             ) {
//            if (gi.getId() == id) {
//                db.remove(gi);
//                Action action = Action.builder()
//                        .time(new Timestamp(new Date().getTime()))
//                        .typeCode(2L)
//                        .typeMessage("Удаление")
//                        .message("Группа удалена")
//                        .build();
//
//                gi.getActionHistory().add(action);





    private List<Teacher> getJobsById(final GroupForm groupForm) {
        return servicesService.getByIds(groupForm.getSelectedTeachers());
    }

}
