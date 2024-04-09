package com.example.workoutlog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class WorkoutInput {
    private String exerciseName;
    private String duration;
    private String content;
}