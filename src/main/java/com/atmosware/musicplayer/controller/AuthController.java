package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.AuthRequest;
import com.atmosware.musicplayer.dto.request.RefreshTokenRequest;
import com.atmosware.musicplayer.dto.request.UserRequest;
import com.atmosware.musicplayer.dto.response.AuthResponse;
import com.atmosware.musicplayer.dto.response.RefreshTokenResponse;
import com.atmosware.musicplayer.service.AuthService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/refresh-token/{refresh-token}")
    public DataResult<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return service.refreshToken(request);
    }
    @PostMapping("/logout/{logout}")
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        return service.logout(request, response);
    }
}
