package com.promo.reviewservice.сontroller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    //Под авторизацией всем
    @GetMapping("/promo/all-auth")
    public String getInfo(@AuthenticationPrincipal UserDetails user) {
        return user.getUsername();
    }

    //Только админу
    @GetMapping("/promo/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getInfoForAdmin(@AuthenticationPrincipal UserDetails user) {
        return user.getUsername();
    }
}
