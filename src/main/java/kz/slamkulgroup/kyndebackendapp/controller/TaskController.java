package kz.slamkulgroup.kyndebackendapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.slamkulgroup.kyndebackendapp.dto.TaskResponse;
import kz.slamkulgroup.kyndebackendapp.security.UserDetailsImpl;
import kz.slamkulgroup.kyndebackendapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Task Management", description = "Task completion and tracking APIs")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @Operation(summary = "Get user tasks", description = "Get paginated list of user's tasks")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getUserTasks(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<TaskResponse> tasks = taskService.getUserTasks(userDetails.getId(), pageable);
        return ResponseEntity.ok(tasks);
    }
    
    @Operation(summary = "Get tasks for specific date", description = "Get all tasks for a specific date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "400", description = "Invalid date format")
    })
    @GetMapping("/date/{date}")
    public ResponseEntity<List<TaskResponse>> getTasksForDate(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Parameter(description = "Date in YYYY-MM-DD format") 
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        List<TaskResponse> tasks = taskService.getUserTasksForDate(userDetails.getId(), date);
        return ResponseEntity.ok(tasks);
    }
    
    @Operation(summary = "Complete task", description = "Mark a task as completed")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task completed successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Task not found"),
        @ApiResponse(responseCode = "409", description = "Task is already completed")
    })
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> completeTask(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable Long id) {
        TaskResponse task = taskService.completeTask(userDetails.getId(), id);
        return ResponseEntity.ok(task);
    }
    
    @Operation(summary = "Uncomplete task", description = "Mark a task as not completed")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task uncompleted successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Task not found"),
        @ApiResponse(responseCode = "409", description = "Task is not completed")
    })
    @PatchMapping("/{id}/uncomplete")
    public ResponseEntity<TaskResponse> uncompleteTask(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable Long id) {
        TaskResponse task = taskService.uncompleteTask(userDetails.getId(), id);
        return ResponseEntity.ok(task);
    }
}