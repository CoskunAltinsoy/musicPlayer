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
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create-demand-artist/{id}")
    public Result createDemandArtist(@PathVariable Long id) {
        return service.createDemandArtist(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create-approval-artist/{id}")
    public Result createApprovalArtist(@PathVariable Long id) {
        return service.createApprovalArtist(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/id/{id}")
    public DataResult<UserResponse> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-all")
    public DataResult<List<UserResponse>> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return service.delete(id);
    }
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping("/change-password")
    public Result changePassword(@RequestBody PasswordRequest request) {
        return service.changePassword(request);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping("/forgot-password")
    public DataResult<TokenResetResponse> forgotPassword(@RequestBody ResetPasswordRequest request) {
        return service.forgotPassword(request);
    }
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
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
}
