package kz.slamkulgroup.kyndebackendapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.slamkulgroup.kyndebackendapp.dto.HabitRequest;
import kz.slamkulgroup.kyndebackendapp.dto.HabitResponse;
import kz.slamkulgroup.kyndebackendapp.security.UserDetailsImpl;
import kz.slamkulgroup.kyndebackendapp.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/habits")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Habit Management", description = "Habit CRUD operations APIs")
public class HabitController {
    
    @Autowired
    private HabitService habitService;
    
    @Operation(summary = "Get user habits", description = "Get paginated list of user's habits")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Habits retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<Page<HabitResponse>> getUserHabits(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<HabitResponse> habits = habitService.getUserHabits(userDetails.getId(), pageable);
        return ResponseEntity.ok(habits);
    }
    
    @Operation(summary = "Get habit by ID", description = "Get a specific habit by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Habit retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Habit not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HabitResponse> getHabit(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @PathVariable Long id) {
        HabitResponse habit = habitService.getHabit(userDetails.getId(), id);
        return ResponseEntity.ok(habit);
    }
    
    @Operation(summary = "Create new habit", description = "Create a new habit with schedule configuration")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Habit created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "409", description = "Habit name already exists")
    })
    @PostMapping
    public ResponseEntity<HabitResponse> createHabit(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @Valid @RequestBody HabitRequest habitRequest) {
        HabitResponse habit = habitService.createHabit(userDetails.getId(), habitRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(habit);
    }
    
    @Operation(summary = "Update habit", description = "Update an existing habit")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Habit updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Habit not found"),
        @ApiResponse(responseCode = "409", description = "Habit name already exists")
    })
    @PutMapping("/{id}")
    public ResponseEntity<HabitResponse> updateHabit(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable Long id,
                                                    @Valid @RequestBody HabitRequest habitRequest) {
        HabitResponse habit = habitService.updateHabit(userDetails.getId(), id, habitRequest);
        return ResponseEntity.ok(habit);
    }
    
    @Operation(summary = "Delete habit", description = "Delete a habit and all its associated tasks")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Habit deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Habit not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @PathVariable Long id) {
        habitService.deleteHabit(userDetails.getId(), id);
        return ResponseEntity.noContent().build();
    }
}