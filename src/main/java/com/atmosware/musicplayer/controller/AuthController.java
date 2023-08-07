package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.AuthRequest;
import com.atmosware.musicplayer.dto.request.UserRequest;
import com.atmosware.musicplayer.dto.response.AuthResponse;
import com.atmosware.musicplayer.service.AuthService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;

    @PostMapping("/login/{login}")
    public DataResult<AuthResponse> login(@RequestBody AuthRequest request) {
        return service.login(request);
    }

    @PostMapping("/register/{register}")
    public Result register(@RequestBody UserRequest request) {
        return service.register(request);
    }
}
