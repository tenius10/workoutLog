package com.example.workoutlog.controller;

import com.example.workoutlog.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class WorkoutController {
    private final WorkoutService workoutService;

}
