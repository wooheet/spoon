package io.spoon.radip.service.follow;

import io.spoon.radip.domain.user.User;
import org.springframework.stereotype.Service;

@Service
public interface FollowService {

  User following(User me, long id);

  User block(User me, long id);
}
