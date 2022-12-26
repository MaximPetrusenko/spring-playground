package com.maxim.springbootfeatures.service;

import com.maxim.springbootfeatures.dao.FakeDataDao;
import com.maxim.springbootfeatures.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


class UserServiceTest {

    @Mock
    private FakeDataDao fakeDataDao;

    private UserService userService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(fakeDataDao);
    }

    @Test
    void shoudldGetAllUsers() {

        UUID annaUserUid = UUID.randomUUID();
        User anna = new User(annaUserUid, "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        List<User> users = new ArrayList<>();
        users.add(anna);
        given(fakeDataDao.selectAllUsers()).willReturn(users);
        List<User> allUsers = userService.getAllUsers(Optional.empty());

        assertThat(allUsers).hasSize(1);

        User user = allUsers.get(0);
        assertUserFields(user);

    }


    @Test
    void shouldGetAllUsersByGender() {

        UUID annaUserUid = UUID.randomUUID();
        User anna = new User(annaUserUid, "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        UUID joeUserUid = UUID.randomUUID();
        User joe = new User(annaUserUid, "joe", "jones", User.Gender.MALE, 30, "joe.jones@gmail.com");

        List<User> users = new ArrayList<>();
        users.add(anna);
        given(fakeDataDao.selectAllUsers()).willReturn(users);

        List<User> filteredUsers = userService.getAllUsers(Optional.of("female"));
        assertThat(filteredUsers).hasSize(1);
        assertUserFields(filteredUsers.get(0));
    }

    @Test
    void shouldThrowExceptionWhenGenderIsInvalid() throws Exception {
        assertThatThrownBy(() -> userService.getAllUsers(Optional.of("swdqwfwqfwq")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Invalid gender");
    }

    @Test
    void shouldGetUser() {
        UUID anndUid = UUID.randomUUID();
        User anna = new User(anndUid, "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        given(fakeDataDao.selectUserByUserUid(anndUid)).willReturn(Optional.of(anna));

        Optional<User> userOptional = userService.getUser(anndUid);

        assertThat(userOptional.isPresent()).isTrue();

        User user = userOptional.get();

        assertUserFields(user);

        ;

    }

    @Test
    void shouldUpdateUser() {
        UUID anndUid = UUID.randomUUID();
        User anna = new User(anndUid, "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        given(fakeDataDao.selectUserByUserUid(anndUid)).willReturn(Optional.of(anna));
        given(fakeDataDao.updateUser(anna)).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int updateResult = userService.updateUser(anna);

        verify(fakeDataDao).selectUserByUserUid(anndUid);
        verify(fakeDataDao).updateUser(captor.capture());

        User user = captor.getValue();
        assertUserFields(user);

        assertThat(updateResult).isEqualTo(1);

    }

    @Test
    void shouldRemoveUser() {

        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid, "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(anna));
        given(fakeDataDao.deleteUserByUserUid(annaUid)).willReturn(1);

        int deleteResult = userService.removeUser(annaUid);

        verify(fakeDataDao).selectUserByUserUid(annaUid);
        verify(fakeDataDao).deleteUserByUserUid(annaUid);

        assertThat(deleteResult).isEqualTo(1);
    }

    @Test
    void shoudlInsertUser() {

        UUID userUid = UUID.randomUUID();
        User anna = new User(userUid, "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        given(fakeDataDao.insertUser(any(UUID.class), any(User.class))).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int insertResult = userService.insertUser(anna);

        verify(fakeDataDao).insertUser(eq(userUid), captor.capture());

        User user = captor.getValue();

        assertUserFields(user);

        assertThat(insertResult).isEqualTo(1);

    }

    private static void assertUserFields(User user) {
        assertThat(user.getAge()).isEqualTo(30);
        assertThat(user.getFirstName()).isEqualTo("anna");
        assertThat(user.getLastName()).isEqualTo("montana");
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getEmail()).isEqualTo("anna@gmail.com");
        assertThat(user.getUserUid()).isNotNull();
    }
}