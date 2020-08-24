package io.spoon.radip;

import com.google.common.collect.Sets;
import io.spoon.radip.api.dto.UserDto;
import io.spoon.radip.common.type.UserRoleType;
import io.spoon.radip.domain.user.User;
import io.spoon.radip.repository.UserRepository;
import io.spoon.radip.service.follow.FollowService;
import io.spoon.radip.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FollowTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowService followService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        Collection<UserRoleType> djRoleTypes = Sets.newHashSet(
                UserRoleType.USER,
                UserRoleType.DJ);

        Collection<UserRoleType> listenerRoleTypes = Sets.newHashSet(
                UserRoleType.USER,
                UserRoleType.LISTENER);


        User savedUser = userRepository.save(User.builder()
                .email("woo@dj.com")
                .password(passwordEncoder.encode("1234567")).build());

        User savedUser2 = userRepository.save(User.builder()
                .email("woo@listener.com")
                .password(passwordEncoder.encode("1234567")).build());

        User savedUser3 = userRepository.save(User.builder()
                .email("woo@listener2.com")
                .password(passwordEncoder.encode("1234567")).build());

        for (UserRoleType roleType : djRoleTypes) {
            savedUser.addRole(roleType);
        }

        for (UserRoleType roleType : listenerRoleTypes) {
            savedUser2.addRole(roleType);
            savedUser3.addRole(roleType);
        }
    }

    @Test
    public void follow() {
        User listener = userRepository.findByEmail("woo@listener.com").orElse(null);
        User dj = userRepository.findByEmail("woo@dj.com").orElse(null);

        assert dj != null;
        User user = followService.following(listener, dj.getId());
        assert user.getFans().size() == 1;
    }

    @Test
    public void block() {
        User listener = userRepository.findByEmail("woo@listener.com").orElse(null);
        User dj = userRepository.findByEmail("woo@dj.com").orElse(null);

        assert listener != null;

        User user = followService.block(dj, listener.getId());
        assert user.getBlocks().size() == 1;
    }

    @Test
    public void blockAndBlock() {
        User listener = userRepository.findByEmail("woo@listener.com").orElse(null);
        User dj = userRepository.findByEmail("woo@dj.com").orElse(null);

        assert listener != null;
        assert dj != null;

        User followingUser = followService.following(listener, dj.getId());
        assert followingUser.getFans().size() == 1;


        User blockUser = followService.block(dj, listener.getId());
        assert blockUser.getFans().size() == 0;
    }


    @Test
    public void getDj() {
        User listener = userRepository.findByEmail("woo@listener.com").orElse(null);
        User listener2 = userRepository.findByEmail("woo@listener2.com").orElse(null);

        User dj = userRepository.findByEmail("woo@dj.com").orElse(null);

        assert listener != null;
        assert dj != null;

        followService.following(listener, dj.getId());
        User followingUser = followService.following(listener2, dj.getId());
        assert followingUser.getFans().size() == 2;

        UserDto.Response user = userService.getUser(dj);

        assert user.getCount() == 2;
    }
}
