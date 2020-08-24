package io.spoon.radip.service.user;

import io.spoon.radip.api.dto.UserDto;
import io.spoon.radip.common.type.UserRoleType;
import io.spoon.radip.domain.user.User;
import io.spoon.radip.domain.user.UserFans;
import io.spoon.radip.exception.EmailSigninFailedException;
import io.spoon.radip.exception.EmailSignupFailedException;
import io.spoon.radip.exception.FanExistException;
import io.spoon.radip.exception.UserNotFoundException;
import io.spoon.radip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public User signUp(String email, String password, List<UserRoleType> roleTypes) {
        if (userRepository.findByEmail(email).isPresent()) {
            log.warn("이미 가입한 계정입니다. => {}", email);
            throw new EmailSignupFailedException(email + " already exists");
        } else {
            User savedUser = userRepository.save(User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());

            for (UserRoleType roleType : roleTypes) {
                savedUser.addRole(roleType);
            }

            return savedUser;
        }
    }

    @Override
    public User getUserByCredential(String email, String password) {
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailSigninFailedException("이메일 또는 비밀번호 정보가 일치하지 않습니다."));

        if (!passwordEncoder.matches(password, findUser.getPassword()))
            throw new EmailSigninFailedException("이메일 또는 비밀번호 정보가 일치하지 않습니다.");
        return findUser;
    }

    @Override
    public User getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(
                authentication.getName()).orElseThrow(() -> new UserNotFoundException("인증된 유저를 찾을 수 없습니다."));
    }

    @Override
    @Cacheable(value="getUser")
    public UserDto.Response getUser(User reqUser, long id) {
        if (reqUser.getBlocks().stream()
                .anyMatch(f -> f.getBlockUser() == id)) {
            throw new FanExistException("차단된 사용자의 프로필을 조회할 수 없습니다");
        }

        User user = findUser(id);

        if (user.getBlocks().stream()
                .anyMatch(f -> f.getBlockUser() == reqUser.getId())) {
            throw new FanExistException("조회하려는 사용자에게 차단 되어 프로필을 조회할 수 없습니다");
        }

        List<UserFans> list = user.getFans();

        UserDto.Response response = new UserDto.Response();
        response.setCount(list.size());
        response.setList(list);

        return response;
    }


    @Override
    @Cacheable(value="getUser")
    public UserDto.Response getUser(User reqUser) {
        List<UserFans> list = reqUser.getFans();

        UserDto.Response response = new UserDto.Response();
        response.setCount(list.size());
        response.setList(list);

        return response;
    }


    @Override
    @CacheEvict(value="getUser")
    public void cacheRefresh() {
          log.info("cache clear => get user");
    }

    private User findUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

}
