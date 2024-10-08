package br.com.insper.ai.Course;

import br.com.insper.ai.Course.DTOs.UpdateCourseStudentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/courses")
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(@RequestBody  Course course) {
        return courseService.createCourse(course);
    }

    @GetMapping("/courses")
    public List<Course> getCourses(@RequestParam(required = false) String courseName) {
        return courseService.getCourses(courseName);
    }

    @PutMapping("/courses/{id}/students")
    public Course updateCourseStudents(@PathVariable String id, @RequestBody UpdateCourseStudentsDTO updateCourseStudentsDTO) {
        return courseService.updateCourseStudents(id, updateCourseStudentsDTO);
    }
}
