package com.maxim.springbootfeatures.dao;

import com.maxim.springbootfeatures.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeDataDao implements UserDao {

    private Map<UUID, User> database;

    public FakeDataDao() {
        database = new HashMap<>();
        UUID joeUserUid = UUID.randomUUID();
        database.put(joeUserUid, new User(joeUserUid, "Joe", "Jones", User.Gender.MALE, 22, "joe.johnes@gmail.com"));
    }


    @Override
    public List<User> selectAllUsers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<User> selectUserByUserUid(UUID userUid) {
        return Optional.ofNullable(database.get(userUid));
    }

    @Override
    public int updateUser(User user) {
        database.put(user.getUserUid(), user);
        return 1;
    }

    @Override
    public int deleteUserByUserUid(UUID userUid) {
        database.remove(userUid);
        return 1;
    }

    @Override
    public int insertUser(UUID userUid, User user) {
        database.put(userUid, user);
        return 1;
    }
}
