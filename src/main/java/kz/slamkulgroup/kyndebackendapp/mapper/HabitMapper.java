package kz.slamkulgroup.kyndebackendapp.mapper;

import kz.slamkulgroup.kyndebackendapp.dto.HabitRequest;
import kz.slamkulgroup.kyndebackendapp.dto.HabitResponse;
import kz.slamkulgroup.kyndebackendapp.embedded.ScheduleConfig;
import kz.slamkulgroup.kyndebackendapp.entity.Habit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HabitMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "schedule", source = ".")
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Habit toEntity(HabitRequest habitRequest);
    
    @Mapping(source = "schedule.type", target = "scheduleType")
    @Mapping(source = "schedule.interval", target = "interval")
    @Mapping(source = "schedule.customDays", target = "customDays")
    HabitResponse toDto(Habit habit);
    
    default ScheduleConfig mapToScheduleConfig(HabitRequest habitRequest) {
        return new ScheduleConfig(
            habitRequest.getScheduleType(),
            habitRequest.getInterval(),
            habitRequest.getCustomDays()
        );
    }
}