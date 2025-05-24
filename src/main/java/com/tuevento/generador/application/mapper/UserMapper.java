package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.user.UserRegisterDTO;
import com.tuevento.generador.application.dto.user.UserResponseDTO;
import com.tuevento.generador.domain.model.User;

public class UserMapper {

    public static User toEntity(UserRegisterDTO dto) {
        return User.builder()
                .username(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword()) // Encriptar luego en el servicio
                .build();
    }

    public static UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
