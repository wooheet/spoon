package io.spoon.radip.api;

import io.spoon.radip.api.dto.UserDto;
import io.spoon.radip.common.CommonResult;
import io.spoon.radip.common.ListResult;
import io.spoon.radip.config.security.JwtTokenProvider;
import io.spoon.radip.domain.user.User;
import io.spoon.radip.repository.UserRepository;
import io.spoon.radip.service.ResponseService;
import io.spoon.radip.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "가입", notes = "회원가입")
    @PostMapping("signup")
    public CommonResult signup(@ApiParam(value = "sign up", required = true) @RequestBody @Valid UserDto.SignUp request) {
        User user = userService.signUp(request.getEmail(), request.getPassword(), request.getRoles());
        return responseService.getSingleResult(
                jwtTokenProvider.createToken(user.getEmail(), user.getRoles()));
    }

    @ApiOperation(value = "로그인", notes = "회원 로그인")
    @PostMapping("signin")
    public CommonResult signin(@ApiParam(value = "sign in", required = true) @RequestBody UserDto.SignUp request) {
        User user = userService.getUserByCredential(request.getEmail(), request.getPassword());
        return responseService.getSingleResult(
                jwtTokenProvider.createToken(user.getEmail(), user.getRoles()));
    }

    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        return responseService.getListResult(userRepository.findAll());
    }

    @ApiOperation(value = "User 조회", notes = "User의 프로필을 조회")
    @GetMapping(value = "/user/{id}")
    public CommonResult findUser(@PathVariable long id) {
        UserDto.Response response;
        User user = userService.getAuthenticationUser();

        if (user.getId() == id) response = userService.getUser(user);
        else response = userService.getUser(user, id);

        return responseService.getSingleResult(response);
    }

    @PutMapping("refresh")
    public ResponseEntity<?> refresh() {
        userService.cacheRefresh();
        return ResponseEntity.ok().build();
    }
}