package com.maxim.springbootfeatures;

import com.maxim.springbootfeatures.clientproxy.UserResourceV1;
import com.maxim.springbootfeatures.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class SpringBootFeaturesApplicationTests {

	@Autowired
	private UserResourceV1 userResourceV1;
	@Test
	void shouldInsertUser() throws Exception{
		//Given
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "Joe", "Jones", User.Gender.MALE, 22, "joe.johnes@gmail.com");

		//When
		userResourceV1.insertNewUser(user);

		//Then
		User joe = userResourceV1.fetchUser(userUid);
		assertThat(joe).isEqualToComparingFieldByField(user);
	}

	@Test
	void shouldDeleteUser() {
		//Given
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "Joe", "Jones", User.Gender.MALE, 22, "joe.johnes@gmail.com");

		//When
		userResourceV1.insertNewUser(user);

		//Then
		User joe = userResourceV1.fetchUser(userUid);
		assertThat(joe).isEqualToComparingFieldByField(user);

		//When
		userResourceV1.deleteUser(userUid);

		//Then
		assertThatThrownBy(() -> userResourceV1.fetchUser(userUid))
				.isInstanceOf(NotFoundException.class);
	}

	@Test
	void shouldUpdateUser() throws Exception {
		//Given
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "Joe", "Jones", User.Gender.MALE, 22, "joe.johnes@gmail.com");

		//When
		userResourceV1.insertNewUser(user);

		User updatedUser = new User(userUid, "Alex", "Jones", User.Gender.MALE, 55, "alex.johnes@gmail.com");

		userResourceV1.updateUser(updatedUser);

		User alex = userResourceV1.fetchUser(userUid);
		assertThat(alex).isEqualToComparingFieldByField(updatedUser);
	}

	@Test
	void shouldFetchUsersByGender() throws Exception {
		//Given
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "Joe", "Jones", User.Gender.MALE, 22, "joe.johnes@gmail.com");

		//When
		userResourceV1.insertNewUser(user);

		List<User> females = userResourceV1.fetchUsers("female");
		List<User> males = userResourceV1.fetchUsers("male");

		assertThat(females).extracting("userUid").doesNotHaveToString(userUid.toString());

	}
}
