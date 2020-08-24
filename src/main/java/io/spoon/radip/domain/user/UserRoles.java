package io.spoon.radip.domain.user;

import io.spoon.radip.common.type.UserRoleType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoles {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Getter(AccessLevel.NONE)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private UserRoleType role;

    @Builder
    private UserRoles(User user, UserRoleType roleType) {
        this.user = user;
        this.role = roleType;
    }
}
