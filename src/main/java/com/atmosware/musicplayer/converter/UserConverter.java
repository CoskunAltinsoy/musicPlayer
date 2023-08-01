package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.UserRequest;
import com.atmosware.musicplayer.dto.response.UserResponse;
import com.atmosware.musicplayer.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class UserConverter {

    public User convertToEntity(UserRequest request){
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .nationalIdentity(request.getNationalIdentity())
                .dateOfBirth(request.getDateOfBirth())
                .build();
    }

    public UserResponse convertToResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .nationalIdentity(user.getNationalIdentity())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }
}
