package com.example.workoutlog.service;

import com.example.workoutlog.dto.WorkoutInput;
import com.example.workoutlog.dto.WorkoutOutput;
import com.example.workoutlog.dto.WorkoutPartOutput;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkoutService {

    private static final List<Workout> workoutsRepository = new ArrayList<>();  // 임시 데이터베이스

    /**
     * 운동기록 저장
     * @param workoutInput 저장할 데이터 정보
     * @return WorkoutOuput: 저장한 workout 기록을 반환
     */
    public WorkoutOutput create(WorkoutInput workoutInput){
        // workoutInput(유저에게 받은 데이터 객체) -> workout[newData](데이터베이스에 저장될 객체)
        Workout newData = new Workout();
        newData.id = getNextId();
        newData.date = LocalDateTime.now();
        newData.exerciseName = workoutInput.getExerciseName();
        newData.duration = workoutInput.getDuration();
        newData.content = workoutInput.getContent();

        // newData 저장 및 저장된 객체 리턴
        workoutsRepository.add(newData);
        return newData.toWorkoutOutput();
    }

    /**
     * 저장되어 있는 모든 운동기록을 리턴
     * @return WorkoutOutput List
     */
    public List<WorkoutOutput> findAll(){
        // 데이터베이스의 모든 정보를 읽고 유저에게 리턴할 데이터(workoutOutput)로 맞춰 변환해 리턴
        List<WorkoutOutput> result = new ArrayList<>();
        for(Workout i: workoutsRepository){
            result.add(i.toWorkoutOutput());
        }
        return result;
    }

    /**
     * workoutLog 페이지 가져오기
     * @param howMany 페이지 크기
     * @param pageNum 페이지 번호
     * @return workoutLog 페이지 정보 반환
     */
    public WorkoutPartOutput findPart(int howMany, int pageNum){
        if(howMany==0){
            return WorkoutPartOutput.builder()
                    .workoutLogs(new ArrayList<>())
                    .maxPage(0)
                    .build();
        }
        else {
            int dataSize=workoutsRepository.size();
            int maxPage=dataSize/howMany+ (dataSize%howMany==0?0:1);
            List<WorkoutOutput> workoutLogss=new ArrayList<>();
            for(int i=pageNum*howMany;i<(pageNum+1)*howMany && i<dataSize;i++){
                workoutLogss.add(workoutsRepository.get(i).toWorkoutOutput());
            }
            return WorkoutPartOutput.builder()
                    .workoutLogs(workoutLogss)
                    .maxPage(maxPage)
                    .build();
        }
    }

    /**
     * 운동기록 수정
     * @param id 수정될 운동기록의 id
     * @param workoutInput 수정에 적용할 내용
     * @return 수정된 운동기록
     */
    public WorkoutOutput update(Long id, WorkoutInput workoutInput){
        // id에 해당하는 운동기록 조회
        Workout data = findById(id);
        if(data==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, id+" not found");    // id에 해당하는 workout을 찾을 수 없을 때

        // 수정될 내용 적용 및 리턴
        if(workoutInput.getExerciseName()!=null) data.exerciseName = workoutInput.getExerciseName();
        if(workoutInput.getDuration()!=null) data.duration = workoutInput.getDuration();
        if(workoutInput.getContent()!=null) data.content = workoutInput.getContent();
        return data.toWorkoutOutput();
    }

    /**
     * 운동기록 삭제
     * @param id 삭제될 운동기록의 id
     */
    public void delete(Long id){
        // id에 해당하는 운동기록 조회
        Workout data = findById(id);
        if(data==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, id+" not found");

        // 삭제
        workoutsRepository.remove(data);
    }

    private long getNextId(){
        if(workoutsRepository.isEmpty()) return 0;

        Workout lastData = workoutsRepository.get(workoutsRepository.size()-1);
        return lastData.id+1;
    }
    private Workout findById(Long id){
        for(Workout i: workoutsRepository){
            if(id.equals(i.id)){
                return i;
            }
        }
        return null;
    }
}

/**
 * 데이터베이스에 저장될 객체
 * (Entity라고 불리며 일반적으로 model 폴더 내에 존재)
 * (현재는 세션에서 DB 진도를 안 나가서 임시로 사용)
 */
class Workout{
    public Long id;
    public String exerciseName;
    public String duration;
    public LocalDateTime date;
    public String content;

    public WorkoutOutput toWorkoutOutput(){
        return new WorkoutOutput(this.id, this.exerciseName, this.duration, this.date, this.content);
    }
}
