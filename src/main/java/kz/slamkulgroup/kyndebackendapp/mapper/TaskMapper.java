package kz.slamkulgroup.kyndebackendapp.mapper;

import kz.slamkulgroup.kyndebackendapp.dto.TaskResponse;
import kz.slamkulgroup.kyndebackendapp.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    
    @Mapping(source = "habit.name", target = "habitName")
    TaskResponse toDto(Task task);
}