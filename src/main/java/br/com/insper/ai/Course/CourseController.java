package br.com.insper.ai.Course;

import br.com.insper.ai.Course.DTOs.UpdateCourseStudentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/courses")
    public Course createCourse(@RequestBody  Course course) {
        return courseService.createCourse(course);
    }

    @GetMapping("/courses")
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @PutMapping("/courses/{id}/students")
    public Course updateCourseStudents(@PathVariable String id, @RequestBody UpdateCourseStudentsDTO updateCourseStudentsDTO) {
        return courseService.updateCourseStudents(id, updateCourseStudentsDTO);
    }
}
