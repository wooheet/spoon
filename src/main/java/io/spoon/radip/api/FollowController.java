package io.spoon.radip.api;

import io.spoon.radip.api.dto.FollowDto;
import io.spoon.radip.domain.user.User;
import io.spoon.radip.common.CommonResult;
import io.spoon.radip.service.ResponseService;
import io.spoon.radip.service.follow.FollowService;
import io.spoon.radip.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = {"3. Follow"})
@RequestMapping(value = "/v1")
public class FollowController {

    private final UserService userService;
    private final FollowService followService;
    private final ResponseService responseService;

    @ApiOperation(value = "Fan 신청", notes = "Fan 신청")
    @PostMapping("following")
    public CommonResult following(@ApiParam(value = "following", required = true) @RequestBody FollowDto.Following request) {
        User me = userService.getAuthenticationUser();
        followService.following(me, request.getUserId());
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "차단", notes = "회원 차단")
    @PostMapping("block")
    public CommonResult block(@ApiParam(value = "block", required = true) @RequestBody FollowDto.Block request) {
        User me = userService.getAuthenticationUser();
        followService.block(me, request.getUserId());
        return responseService.getSuccessResult();
    }

}
