package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.UserConverter;
import com.atmosware.musicplayer.dto.request.AuthRequest;
import com.atmosware.musicplayer.dto.request.RefreshTokenRequest;
import com.atmosware.musicplayer.dto.request.UserRequest;
import com.atmosware.musicplayer.dto.response.AuthResponse;
import com.atmosware.musicplayer.dto.response.RefreshTokenResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.RefreshToken;
import com.atmosware.musicplayer.model.entity.Role;
import com.atmosware.musicplayer.model.entity.User;
import com.atmosware.musicplayer.security.CustomUserDetail;
import com.atmosware.musicplayer.security.JwtUtils;
import com.atmosware.musicplayer.service.AuthService;
import com.atmosware.musicplayer.service.RoleService;
import com.atmosware.musicplayer.service.RefreshTokenService;
import com.atmosware.musicplayer.service.UserService;
import com.atmosware.musicplayer.util.TimeUtil;
import com.atmosware.musicplayer.util.constant.Message;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final RoleService roleService;
    private final UserConverter converter;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final TimeUtil timeUtils;
    @Override
    public DataResult<AuthResponse> login(AuthRequest request) {
        //checkIfUserExistsByEmail(request.getEmail());
        Authentication authentication =
                authenticationManager.authenticate
                        (new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        var roles = customUserDetail.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        RefreshToken refreshToken = refreshTokenService.create(customUserDetail.getId());
        return new DataResult<>(Message.User.SUCCESSFUL,
                new AuthResponse(jwt, refreshToken.getToken(), customUserDetail.getUsername(), roles));
    }

    @Override
    public Result register(UserRequest request) {
        User user = converter.convertToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = setRoleIdsFromRequest(request);
        user.setCreatedDate(timeUtils.getLocalDateTimeNow());
        user.setDemandArtist(false);
        user.setApproveArtist(false);
        user.setRoles(roles);
        userService.save(user);
        return new Result(Message.User.SUCCESSFUL);
    }

    @Override
    public DataResult<RefreshTokenResponse> refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        var findToken = refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getEmail());
                    return new RefreshTokenResponse(token, refreshToken);
                })
                .orElseThrow(() -> new BusinessException("Refresh token is not in database!"));

        return new DataResult<>(Message.User.SUCCESSFUL, findToken);
    }

    @Override
    public Result logout() {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return new Result(Message.User.SUCCESSFUL);
    }

    private Set<Role> setRoleIdsFromRequest(UserRequest request) {
        return request.getRoleIds().stream()
                .map(roleService::findById)
                .collect(Collectors.toSet());
    }
}
