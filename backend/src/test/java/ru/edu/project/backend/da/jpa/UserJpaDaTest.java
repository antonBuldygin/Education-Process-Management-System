package ru.edu.project.backend.da.jpa;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.edu.project.backend.api.user.UserInfo;
import ru.edu.project.backend.da.jpa.converter.UserInfoMapper;
import ru.edu.project.backend.da.jpa.entity.UserEntity;
import ru.edu.project.backend.da.jpa.repository.UserEntityRepostitory;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class UserJpaDaTest {

    @Mock
    UserEntityRepostitory repo;

    @Mock
    UserInfoMapper mapper;

    @InjectMocks
    UserJpaDa userJpaDa;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }


    @Test
    public void register() {

        UserInfo userInfo = mock(UserInfo.class);
        UserEntity userEntity = new UserEntity();

        when(mapper.map(any(UserInfo.class))).thenReturn(userEntity);

        when(repo.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity user = invocation.getArgument(0, UserEntity.class);
            return user;
        });

        when(mapper.map(any(UserEntity.class))).thenAnswer(invocation -> {
                    UserEntity userEnt = invocation.getArgument(0, UserEntity.class);
                    UserInfo user = UserInfo.builder().enabled(userEnt.getEnabled()).build();
                    return user;
                });

        UserInfo result = userJpaDa.register(userInfo);

        assertTrue(result.getEnabled());

        verify(mapper, times(1)).map(any(UserInfo.class));
        verify(mapper, times(1)).map(any(UserEntity.class));
    }

    @Test
    public void findByUsername() {

        String username = "UserName";

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        when(repo.findByUsername(username)).thenReturn(userEntity);

        when(mapper.map(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity userEnt = invocation.getArgument(0, UserEntity.class);
            UserInfo user = UserInfo.builder().username(userEnt.getUsername()).build();
            return user;
        });

        UserInfo result = userJpaDa.findByUsername(username);

        assertEquals(username, result.getUsername());

        verify(repo, times(1)).findByUsername(username);

        verify(mapper, times(1)).map(any(UserEntity.class));

    }
}