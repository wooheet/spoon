package io.spoon.radip.api.dto;

import io.spoon.radip.common.type.UserRoleType;
import io.spoon.radip.domain.user.UserFans;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUp {
        @Email
        @NotBlank
        private String email;
        @NotBlank
        private String password;
        @NotBlank
        private String passwordRe;

        private List<UserRoleType> roles;

        public boolean validate() {
            return password.equals(passwordRe);
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignIn {
        @Email
        @NotBlank
        private String email;
        @NotBlank
        private String password;
    }

    private long count;
    private List<UserFans> list;

    @Data
    public static class Response {
        private long count;
        private List<UserFans> list;
    }
}
