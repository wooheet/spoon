package io.spoon.radip.domain.user;

import io.spoon.radip.common.type.UserRoleType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private long id;

  private String email;

  private String password;

  @Builder.Default
  private final boolean enabled = true;

  @OneToMany(mappedBy = "user"
          , fetch = FetchType.LAZY
          , cascade = CascadeType.ALL)
  private List<UserRoles> roles;

  @OneToMany(mappedBy = "user"
          , fetch = FetchType.LAZY
          , cascade = CascadeType.ALL
          , orphanRemoval = true)
  private List<UserFans> fans;

  @OneToMany(mappedBy = "user"
          , fetch = FetchType.LAZY
          , cascade = CascadeType.ALL
          , orphanRemoval = true)
  private List<UserBlocks> blocks;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public void addRole(UserRoleType roleType) {
    if (roles == null) roles = new ArrayList<>();

    this.roles.add(UserRoles.builder()
            .user(this)
            .roleType(roleType)
            .build());
  }

  public void addFan(long id) {
    if (fans == null) fans = new ArrayList<>();

    this.fans.add(UserFans.builder()
            .user(this)
            .fanId(id)
            .build());
  }

  public void addBlock(long id) {
    if (blocks == null) blocks = new ArrayList<>();

    this.blocks.add(UserBlocks.builder()
            .user(this)
            .blockId(id)
            .build());
  }
}
