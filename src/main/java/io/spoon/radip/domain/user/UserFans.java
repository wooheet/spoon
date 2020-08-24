package io.spoon.radip.domain.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFans {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Getter(AccessLevel.NONE)
    private User user;

    private Long fanUser;

    @Builder
    private UserFans(User user, long fanId) {
        this.user = user;
        this.fanUser = fanId;
    }

}
