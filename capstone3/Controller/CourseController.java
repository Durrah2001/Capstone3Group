package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.Model.Course;
import org.example.capstone3.OutDTO.CourseDTO;
import org.example.capstone3.Service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/motorcycle-system/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/get")
    public ResponseEntity getAllCourses(){
        List<CourseDTO> courseDTOS = courseService.getAllCourses();
        return ResponseEntity.status(200).body(courseDTOS);
    }

    @PostMapping("/add/{owner_id}")
    public ResponseEntity addBranch(@PathVariable Integer owner_id, @RequestBody @Valid Course course) {

        courseService.addCourse(owner_id, course);
        return ResponseEntity.status(200).body(new ApiResponse("Course added successfully!"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateCourse(@PathVariable Integer id, @RequestBody @Valid Course course){
        courseService.updateCourse(id, course);
        return ResponseEntity.status(200).body(new ApiResponse("Course updated successfully!"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCourse(@PathVariable Integer id){

        courseService.deleteCourse(id);
        return ResponseEntity.status(200).body(new ApiResponse("Course deleted successfully!"));

    }


}
