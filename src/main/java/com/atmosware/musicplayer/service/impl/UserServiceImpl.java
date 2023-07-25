package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.RoleConverter;
import com.atmosware.musicplayer.converter.UserConverter;
import com.atmosware.musicplayer.dto.request.*;
import com.atmosware.musicplayer.dto.response.AuthResponse;
import com.atmosware.musicplayer.dto.response.RoleResponse;
import com.atmosware.musicplayer.dto.response.TokenResetResponse;
import com.atmosware.musicplayer.dto.response.UserResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Role;
import com.atmosware.musicplayer.model.entity.User;
import com.atmosware.musicplayer.repository.UserRepository;
import com.atmosware.musicplayer.security.CustomUserDetail;
import com.atmosware.musicplayer.security.JwtUtils;
import com.atmosware.musicplayer.service.RoleService;
import com.atmosware.musicplayer.service.UserService;
import com.atmosware.musicplayer.util.token.PasswordTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserConverter converter;
    private final PasswordTokenGenerator generator;
    private final RoleService roleService;
    private final RoleConverter roleConverter;

    //private final EmailService emailService;
    @Override
    public AuthResponse login(AuthRequest request) {
        Authentication authentication =
                authenticationManager.authenticate
                        (new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        Set<String> roles = customUserDetail.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return new AuthResponse(jwt);
    }

    @Override
    public void register(UserRequest request) {
        User user = converter.convertToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = setRoleIds(request);
        user.setRoles(roles);
        repository.save(user);
    }

    @Override
    public UserResponse getById(Long id) {
        User user = repository.findById(id).orElseThrow();
        Set<RoleResponse> roleResponses = roleConverter.convertToResponseList(user.getRoles());
        UserResponse userResponse = converter.convertToResponse(user);
        userResponse.setRoles(roleResponses);
        return userResponse;
    }

    @Override
    public List<UserResponse> getAll() {
        List<User> users = repository.findAll();
        return users
                .stream()
                .map(user -> {
                    Set<RoleResponse> roleResponses = roleConverter.convertToResponseList(user.getRoles());
                    UserResponse userResponse = converter.convertToResponse(user);
                    userResponse.setRoles(roleResponses);
                    return userResponse;
                })
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void changePassword(PasswordRequest request) {
        checkIfPasswordMatches(request.getEmail(), request.getNewPassword(), request.getNewPassword());
        User user = repository.findByEmail(request.getEmail()).orElseThrow();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedNewPassword = bCryptPasswordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedNewPassword);
        repository.save(user);
    }

    @Override
    public void resetPassword(String token, TokenPasswordRequest request) {
        User user = repository.findByResetPasswordToken(token).orElseThrow();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setResetPasswordToken(null);
        repository.save(user);
    }

    @Override
    public TokenResetResponse forgotPassword(ResetPasswordRequest request) {
        User user = repository.findByEmail(request.getEmail()).orElseThrow();
        user.setResetPasswordToken(generator.generateToken());
        repository.save(user);
        TokenResetResponse tokenResetResponse = new TokenResetResponse();
        tokenResetResponse.setUrlWithToken("http://localhost:8080/api/customers/resetPassword?token=" +
                user.getResetPasswordToken());

        //Todo Email service will add.

        return tokenResetResponse;
    }

    private Set<Role> setRoleIds(UserRequest request) {
        return request.getRoleIds().stream()
                .map(roleId -> {
                    return roleService.findById(roleId);
                })
                .collect(Collectors.toSet());
    }

    private void checkIfPasswordMatches(String email, String oldPassword, String newPassword) {
        User customer = repository.findByEmail(email).orElseThrow();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(oldPassword, customer.getPassword())) {
            throw new BusinessException("Incorrect Password");
        }
    }
}
