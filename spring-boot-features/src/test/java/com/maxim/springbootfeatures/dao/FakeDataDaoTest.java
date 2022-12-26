package com.maxim.springbootfeatures.dao;

import com.maxim.springbootfeatures.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FakeDataDaoTest {

    private FakeDataDao fakeDataDao;
    @BeforeEach
    void setUp() {
        fakeDataDao = new FakeDataDao();
    }

    @Test
    void shouldSelectAllUsers() {
        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(1);
        User user = users.get(0);
        assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getFirstName()).isEqualTo("Joe");
        assertThat(user.getLastName()).isEqualTo("Jones");
        assertThat(user.getGender()).isEqualTo(User.Gender.MALE);
        assertThat(user.getEmail()).isEqualTo("joe.johnes@gmail.com");
        assertThat(user.getUserUid()).isNotNull();

    }

    @Test
    void shouldSelectUserByUserUid() {
        UUID anndUserUid = UUID.randomUUID();
        User anna = new User(anndUserUid, "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");
        fakeDataDao.insertUser(anndUserUid, anna);
        assertThat(fakeDataDao.selectAllUsers()).hasSize(2);

        Optional<User> annaOptional = fakeDataDao.selectUserByUserUid(anndUserUid);
        assertThat(annaOptional.isPresent()).isTrue();
        assertThat(annaOptional.get()).isEqualTo(anna);
    }

    @Test
    void shouldUpdateUser() {
        UUID joeUserUid = fakeDataDao.selectAllUsers().get(0).getUserUid();
        User newJoe = new User(joeUserUid, "anna", "montana",  User.Gender.FEMALE,30 , "anna@gmail.com");

        fakeDataDao.updateUser(newJoe);

        Optional<User> user = fakeDataDao.selectUserByUserUid(joeUserUid);
        assertThat(fakeDataDao.selectAllUsers()).hasSize(1);
        assertThat(user.get()).isEqualTo(newJoe);
    }

    @Test
    void deleteUserByUserUid() {
        UUID joeUserUid = fakeDataDao.selectAllUsers().get(0).getUserUid();

        fakeDataDao.deleteUserByUserUid(joeUserUid);

        assertThat(fakeDataDao.selectUserByUserUid(joeUserUid).isPresent()).isFalse();
        assertThat(fakeDataDao.selectAllUsers()).isEmpty();

    }

    @Test
    void insertUser() {
        UUID userUid = UUID.randomUUID();
        User user = new User(userUid, "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        fakeDataDao.insertUser(userUid,user);

        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(2);
        assertThat(fakeDataDao.selectUserByUserUid(userUid).get()).isEqualTo(user);
    }
}