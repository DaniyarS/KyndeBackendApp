package kz.slamkulgroup.kyndebackendapp.dto;

import kz.slamkulgroup.kyndebackendapp.enums.AvatarState;

import java.time.LocalDateTime;

public class UserResponse {
    
    private Long id;
    private String email;
    private String phone;
    private AvatarState avatar;
    private LocalDateTime createdAt;
    
    public UserResponse() {}
    
    public UserResponse(Long id, String email, String phone, AvatarState avatar, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.createdAt = createdAt;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public AvatarState getAvatar() {
        return avatar;
    }
    
    public void setAvatar(AvatarState avatar) {
        this.avatar = avatar;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}