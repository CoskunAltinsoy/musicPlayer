package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.*;
import com.atmosware.musicplayer.dto.response.AuthResponse;
import com.atmosware.musicplayer.dto.response.TokenResetResponse;
import com.atmosware.musicplayer.dto.response.UserResponse;
import com.atmosware.musicplayer.service.UserService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {
    private final UserService service;

    @PostMapping("/login/{login}")
    public DataResult<AuthResponse> login(@RequestBody AuthRequest request) {
        return service.login(request);
    }

    @PostMapping("/register/{register}")
    public Result register(@RequestBody UserRequest request) {
        return service.register(request);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create-demand-artist/{createDemandArtist}")
    public Result createDemandArtist(@PathVariable Long id) {
        return service.createDemandArtist(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create-approval-artist/{createApprovalArtist}")
    public Result createApprovalArtist(@PathVariable Long id) {
        return service.createApprovalArtist(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/id/{id}")
    public DataResult<UserResponse> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getall")
    public DataResult<List<UserResponse>> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return service.delete(id);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/change-password")
    public Result changePassword(@RequestBody PasswordRequest request) {
        return service.changePassword(request);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/forgot-password")
    public DataResult<TokenResetResponse> forgotPassword(@RequestBody ResetPasswordRequest request) {
        return service.forgotPassword(request);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/reset-password")
    public Result resetPassword(@RequestParam String token, @RequestBody TokenPasswordRequest request) {
        return service.resetPassword(token, request);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/follow-user/{followerId}/{followedId}")
    public Result followUser(@PathVariable Long followerId, @PathVariable Long followedId) {
        return service.followUser(followerId, followedId);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/unfollow-user/{followerId}/{followedId}")
    public Result unfollowUser(@PathVariable Long followerId, @PathVariable Long followedId) {
        return service.unfollowUser(followerId, followedId);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/follow-artist/{followerId}/{followedArtistId}")
    public Result followArtist(@PathVariable Long followerId, @PathVariable Long followedArtistId) {
        return service.followArtist(followerId, followedArtistId);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/unfollow-artist/{followerId}/{followedArtistId}")
    public Result unfollowArtist(@PathVariable Long followerId, @PathVariable Long followedArtistId) {
        return service.unfollowArtist(followerId, followedArtistId);
    }
}
