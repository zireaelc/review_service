package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.service.AuthenticationService;
import com.promo.reviewservice.сontroller.domain.SignInRequest;
import com.promo.reviewservice.сontroller.domain.SignUpRequest;
import com.promo.reviewservice.сontroller.domain.TokenResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Security")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SecurityUserController {
    private final AuthenticationService authenticationService;

    //Регистрация пользователя
    @PostMapping("/auth/sign-up")
    public TokenResponse signUp(@RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    //Авторизация пользователя
    @PostMapping("/auth/sign-in")
    public TokenResponse signIn(@RequestBody SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
