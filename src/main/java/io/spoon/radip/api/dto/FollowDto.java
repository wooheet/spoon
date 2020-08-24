package io.spoon.radip.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FollowDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Following {
        @NotBlank
        private long userId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Follower {
        @NotBlank
        private long userId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Block {
        @NotBlank
        private long userId;
    }
}
