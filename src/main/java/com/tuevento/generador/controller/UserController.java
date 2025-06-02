package com.tuevento.generador.controller;

import com.tuevento.generador.domain.model.AppUser;
import com.tuevento.generador.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<AppUser> me(@AuthenticationPrincipal UserDetails userDetails) {
        AppUser user = userService.getUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }
}
