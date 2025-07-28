package kz.slamkulgroup.kyndebackendapp.embedded;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kz.slamkulgroup.kyndebackendapp.enums.ScheduleType;

import java.time.DayOfWeek;
import java.util.List;

@Embeddable
public class ScheduleConfig {
    
    @Enumerated(EnumType.STRING)
    private ScheduleType type;
    
    private Integer interval;
    
    private List<DayOfWeek> customDays;
    
    public ScheduleConfig() {}
    
    public ScheduleConfig(ScheduleType type, Integer interval, List<DayOfWeek> customDays) {
        this.type = type;
        this.interval = interval;
        this.customDays = customDays;
    }
    
    public ScheduleType getType() {
        return type;
    }
    
    public void setType(ScheduleType type) {
        this.type = type;
    }
    
    public Integer getInterval() {
        return interval;
    }
    
    public void setInterval(Integer interval) {
        this.interval = interval;
    }
    
    public List<DayOfWeek> getCustomDays() {
        return customDays;
    }
    
    public void setCustomDays(List<DayOfWeek> customDays) {
        this.customDays = customDays;
    }
}