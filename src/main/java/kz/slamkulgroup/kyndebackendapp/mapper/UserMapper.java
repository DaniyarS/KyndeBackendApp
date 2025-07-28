package kz.slamkulgroup.kyndebackendapp.mapper;

import kz.slamkulgroup.kyndebackendapp.dto.RegisterRequest;
import kz.slamkulgroup.kyndebackendapp.dto.UserResponse;
import kz.slamkulgroup.kyndebackendapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "habits", ignore = true)
    @Mapping(target = "streak", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(RegisterRequest registerRequest);
    
    UserResponse toDto(User user);
}