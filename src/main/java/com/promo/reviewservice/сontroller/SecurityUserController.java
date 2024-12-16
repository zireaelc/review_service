package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.service.AuthenticationService;
import com.promo.reviewservice.сontroller.domain.SignInRequest;
import com.promo.reviewservice.сontroller.domain.SignUpRequest;
import com.promo.reviewservice.сontroller.domain.TokenResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Аутентификация")
@RestController
@RequiredArgsConstructor
public class SecurityUserController {
    private final AuthenticationService authenticationService;

    //Регистрация пользователя
    @PostMapping("/api/v1/auth/sign-up")
    public TokenResponse signUp(@RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    //Авторизация пользователя
    @PostMapping("/api/v1/auth/sign-in")
    public TokenResponse signIn(@RequestBody SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
