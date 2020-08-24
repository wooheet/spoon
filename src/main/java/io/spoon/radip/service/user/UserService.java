package io.spoon.radip.service.user;

import io.spoon.radip.api.dto.UserDto;
import io.spoon.radip.common.type.UserRoleType;
import io.spoon.radip.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

  User signUp(String email, String password, List<UserRoleType> roles);

  User getUserByCredential(String email, String password);

  User getAuthenticationUser();

  UserDto.Response getUser(User reqUser, long id);

  UserDto.Response getUser(User reqUser);

  void cacheRefresh();
}
