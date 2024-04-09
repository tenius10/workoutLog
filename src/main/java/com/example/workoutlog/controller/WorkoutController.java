package com.example.workoutlog.controller;

import com.example.workoutlog.dto.WorkoutInput;
import com.example.workoutlog.dto.WorkoutOutput;
import com.example.workoutlog.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class WorkoutController {
    private final WorkoutService workoutService;

    @PostMapping()
    public ResponseEntity<WorkoutOutput> create(@RequestBody WorkoutInput workoutInput){
        if(workoutInput.getExerciseName().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        WorkoutOutput result = workoutService.create(workoutInput);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<WorkoutOutput>> findAll(){
        return new ResponseEntity<>(workoutService.readAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutOutput> update(@PathVariable(value="id") Long id, @RequestBody WorkoutInput workoutInput){
        if(workoutInput.getExerciseName().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        WorkoutOutput result = workoutService.update(id, workoutInput);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id){
        workoutService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
