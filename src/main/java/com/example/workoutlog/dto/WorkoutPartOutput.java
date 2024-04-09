package com.example.workoutlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class WorkoutPartOutput {
    List<WorkoutOutput> workoutLogs;
    int maxPage;
}
