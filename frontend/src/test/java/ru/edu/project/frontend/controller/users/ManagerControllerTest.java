package ru.edu.project.frontend.controller.users;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.api.groups.GroupForm;
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.teachers.Teacher;
import ru.edu.project.backend.api.teachers.TeacherService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ManagerControllerTest {


    @Mock
    private Status status;

    @Mock
    private GroupsService groupsService;



    @Mock
    private Model modelMock;

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private ManagerController managerController;

    @Before
    public void setUp() throws Exception {
        openMocks(this);

    }

//    @Test
//    public void view() {
//        long requestId = 333L;
//
//        GroupInfo expectedGroupInfo = GroupInfo.builder().build();
//        when(groupsService.getDetailedInfo(requestId)).thenReturn(expectedGroupInfo);
//
//        when(expectedGroupInfo.getStatus()).thenReturn(status);
//
//        ModelAndView view = managerController.view(requestId);
//
//        assertEquals("client/view", view.getViewName());
//
//        assertEquals(expectedGroupInfo, view.getModel().get("record"));
//    }

    @Test
    public void createForm() {
        ArrayList<Teacher> expectedTeacherList = new ArrayList<>();
        when(teacherService.getAvailable()).thenReturn(expectedTeacherList);

        String viewName = managerController.createForm(modelMock);

        assertEquals("manager/create", viewName);
        verify(modelMock).addAttribute("teachers", expectedTeacherList);
    }

    @Test
    public void createFormProcessing() {
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(false);

        GroupInfo expectedInfo = GroupInfo.builder().id(444L).build();

        ManagerController.CreateForm createForm = new ManagerController.CreateForm();
        createForm.setDateCreatedForm("2020-12-01T23:00");
        createForm.setComment("commentStr");


        when(groupsService.createGroup(any(GroupForm.class))).thenAnswer(invocation -> {
            GroupForm form = invocation.getArgument(0, GroupForm.class);
            assertEquals(createForm.getDateCreated(), form.getDateCreatedRf());
            assertEquals(createForm.getComment(), form.getComment());
            return expectedInfo;
        });


        String viewName = managerController.createFormProcessing(createForm, bindingResultMock, modelMock);

        assertEquals("redirect:/manager/?created=" + expectedInfo.getId(), viewName);

        verify(modelMock, times(0)).addAttribute(anyString(), any());
        verify(groupsService).createGroup(any(GroupForm.class));
    }

    @Test
    public void createFormProcessingHasErrors() {
        List<ObjectError> mockErrors = new ArrayList<>();
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(true);
        when(bindingResultMock.getAllErrors()).thenReturn(mockErrors);

        ArrayList<Teacher> expectedJobList = new ArrayList<>();
        when(teacherService.getAvailable()).thenReturn(expectedJobList);

        String viewName = managerController.createFormProcessing(
                new ManagerController.CreateForm(),
                bindingResultMock,
                modelMock
        );

        assertEquals("manager/create", viewName);
        verify(modelMock).addAttribute("teachers", expectedJobList);
        verify(modelMock).addAttribute("errorsList", mockErrors);


    }
}