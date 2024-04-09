package com.example.workoutlog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WorkoutOutput {
    public Long id;
    public String exerciseName;
    public String duration;
    public LocalDateTime date;
    public String content;
}