package ru.edu.project.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.RecordSearch;
import ru.edu.project.backend.api.groups.GroupForm;
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.groups.UpdateStatusRequest;

import java.util.List;


@RestController
@RequestMapping("/group")
public class GroupsController implements GroupsService {

    /**
     * Делегат для передачи вызова.
     */
    @Autowired
    private GroupsService delegate;

    /**
     * Получение заказов клиента.
     *
     * @return список
     */
    @Override
    @GetMapping("/getAllGroupsInfo")
    public List<GroupInfo> getAllGroupsInfo() {
        return delegate.getAllGroupsInfo();
    }

    /**
     * Получение списка групп для учителя.
     *
     * @param jobId
     * @return список
     */
    @Override
    @GetMapping("/getAllGroupsByTeacher/{job_Id}")
    public List<GroupInfo> getAllGroupsByTeacher(
            @PathVariable("job_Id") final long jobId) {
        return delegate.getAllGroupsByTeacher(jobId);
    }


    /**
     * Получение детальной информации по заявке.
     *
     * @param clientId
     * @return запись
     */
    @Override
    @GetMapping("/getDetailedInfo/{client_Id}")
    public GroupInfo getDetailedInfo(

            @PathVariable("client_Id") final long clientId
    ) {
        return delegate.getDetailedInfo(clientId);
    }

    /**
     * Регистрация новой заявки.
     *
     * @param groupForm
     * @return запись
     */
    @Override
    @PostMapping("/createGroup")
    public GroupInfo createGroup(@RequestBody final GroupForm groupForm) {
        return delegate.createGroup(groupForm);
    }

    /**
     * удаление группы.
     *
     * @param id
     */
    @Override
    @GetMapping("/deleteGroup/{client_Id}")
    public void deleteGroup(@PathVariable("client_Id") final long id) {
        delegate.deleteGroup(id);
    }

    /**
     * Метод для поиска заявок.
     *
     * @param recordSearch
     * @return list
     */
    @Override
    @PostMapping("/searchRequests")
    public PagedView<GroupInfo> searchRequests(@RequestBody final RecordSearch recordSearch) {
        return delegate.searchRequests(recordSearch);
    }

    /**
     * Изменение в группе.
     *
     * @param updateStatusRequest
     * @return boolean
     */
    @Override
    @PostMapping("/updateStatus")
    public boolean updateStatus(@RequestBody final UpdateStatusRequest updateStatusRequest) {
        return delegate.updateStatus(updateStatusRequest);
    }
}
