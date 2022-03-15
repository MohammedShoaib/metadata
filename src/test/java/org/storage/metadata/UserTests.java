package org.storage.metadata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.storage.metadata.repository.UserRepository;
import org.storage.metadata.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("rketan@uci.edu");
        user.setUsername("rketan");
        user.setPassword("r123!");

        User savedUser = userRepository.save(user);
        User existUser = testEntityManager.find(User.class, savedUser.getUser_id());

        assertThat(existUser.getEmail().equals(user.getEmail()));
    }
}
