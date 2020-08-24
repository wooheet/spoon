package io.spoon.radip.service.follow;

import io.spoon.radip.domain.user.User;
import io.spoon.radip.exception.FanExistException;
import io.spoon.radip.exception.UserNotFoundException;
import io.spoon.radip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

      private final UserRepository userRepository;

      @Override
      public User following(User me, long id) {
            User user = findUser(id);

            if (user.getBlocks() != null
                    && user.getBlocks().stream()
                    .anyMatch(f -> f.getBlockUser() == me.getId()))
                  throw new FanExistException("차단 되어 Fan이 불가능 합니다.");

            if (user.getFans() != null
                  && user.getFans().stream()
                    .anyMatch(f -> f.getFanUser() == me.getId()))
                  throw new FanExistException("이미 Fan인 사용자 입니다.");

            user.addFan(me.getId());

            return user;
      }

      @Override
      public User block(User me, long id) {
            User user = findUser(me.getId());

            if (user.getBlocks() != null
                    && user.getBlocks().stream()
                    .anyMatch(b -> b.getBlockUser() == id))
                  throw new FanExistException("이미 차단한 사용자 입니다.");

            user.addBlock(id);

            if (user.getFans() != null)
                  user.getFans().removeIf(u -> u.getFanUser() == id);

            return user;
      }

      private User findUser(long id) {
            return userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
      }
}
