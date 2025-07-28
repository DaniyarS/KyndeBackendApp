package kz.slamkulgroup.kyndebackendapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.slamkulgroup.kyndebackendapp.dto.FcmTokenRequest;
import kz.slamkulgroup.kyndebackendapp.dto.UserResponse;
import kz.slamkulgroup.kyndebackendapp.dto.UserStatsResponse;
import kz.slamkulgroup.kyndebackendapp.entity.User;
import kz.slamkulgroup.kyndebackendapp.mapper.UserMapper;
import kz.slamkulgroup.kyndebackendapp.security.UserDetailsImpl;
import kz.slamkulgroup.kyndebackendapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User Management", description = "User profile and statistics APIs")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMapper userMapper;
    
    @Operation(summary = "Get current user profile", description = "Get the authenticated user's profile information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User profile retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserResponse userResponse = userMapper.toDto(user);
        return ResponseEntity.ok(userResponse);
    }
    
    @Operation(summary = "Get user statistics", description = "Get user's habit completion statistics and streak information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/stats")
    public ResponseEntity<UserStatsResponse> getUserStats(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserStatsResponse stats = userService.getUserStats(userDetails.getId());
        return ResponseEntity.ok(stats);
    }
    
    @Operation(summary = "Update FCM token", description = "Update the user's Firebase Cloud Messaging token for push notifications")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "FCM token updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid token"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/fcm-token")
    public ResponseEntity<?> updateFcmToken(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @Valid @RequestBody FcmTokenRequest fcmTokenRequest) {
        userService.updateFcmToken(userDetails.getId(), fcmTokenRequest.getToken());
        return ResponseEntity.ok().body("{\"message\": \"FCM token updated successfully!\"}");
    }
}