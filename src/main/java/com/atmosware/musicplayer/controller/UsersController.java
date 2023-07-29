package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.*;
import com.atmosware.musicplayer.dto.response.AuthResponse;
import com.atmosware.musicplayer.dto.response.TokenResetResponse;
import com.atmosware.musicplayer.dto.response.UserResponse;
import com.atmosware.musicplayer.service.UserService;
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
    public AuthResponse login(@RequestBody AuthRequest request) {
        return service.login(request);
    }

    @PostMapping("/register/{register}")
    public void register(@RequestBody UserRequest request) {
        service.register(request);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create-demand-artist/{createDemandArtist}")
    public void createDemandArtist(@PathVariable Long id) {
        service.createDemandArtist(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create-approval-artist/{createApprovalArtist}")
    public void createApprovalArtist(@PathVariable Long id) {
        service.createApprovalArtist(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/id/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getall")
    public List<UserResponse> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/change-password")
    public void changePassword(@RequestBody PasswordRequest request) {
        service.changePassword(request);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/forgot-password")
    public TokenResetResponse forgotPassword(@RequestBody ResetPasswordRequest request) {
        return service.forgotPassword(request);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/reset-password")
    public void resetPassword(@RequestParam String token, @RequestBody TokenPasswordRequest request) {
        service.resetPassword(token, request);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/follow-user/{followerId}/{followedId}")
    public void followUser(@PathVariable Long followerId, @PathVariable Long followedId) {
        service.followUser(followerId, followedId);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/unfollow-user/{followerId}/{followedId}")
    public void unfollowUser(@PathVariable Long followerId, @PathVariable Long followedId) {
        service.unfollowUser(followerId, followedId);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/follow-artist/{followerId}/{followedArtistId}")
    public void followArtist(@PathVariable Long followerId, @PathVariable Long followedArtistId) {
        service.followArtist(followerId, followedArtistId);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/unfollow-artist/{followerId}/{followedArtistId}")
    public void unfollowArtist(@PathVariable Long followerId, @PathVariable Long followedArtistId) {
        service.unfollowArtist(followerId, followedArtistId);
    }
}
