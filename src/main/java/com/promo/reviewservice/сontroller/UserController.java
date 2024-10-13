package com.promo.reviewservice.сontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
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
