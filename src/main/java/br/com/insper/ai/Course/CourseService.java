package br.com.insper.ai.Course;

import br.com.insper.ai.Course.DTOs.UpdateCourseStudentsDTO;
import br.com.insper.ai.Course.DTOs.VerifyUserCpfDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Boolean checkCPF(String cpf) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<VerifyUserCpfDTO> response = restTemplate.getForEntity("http://184.72.80.215:8080/usuario/" + cpf, VerifyUserCpfDTO.class);

            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            return false;
        }
    }


    public Course createCourse(Course course) {
        if (course.getDescription() == null || course.getName() == null || course.getTeacherCPF() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        if (!checkCPF(course.getTeacherCPF())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid CPF");
        }

        return courseRepository.save(course);
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Course updateCourseStudents(String id, UpdateCourseStudentsDTO updateCourseStudentsDTO) {
        String studentCPF = updateCourseStudentsDTO.getStudentCPF();

        if (!checkCPF(studentCPF)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid CPF");
        }

        Optional<Course> courseQuery = courseRepository.findById(id);

        if (courseQuery.isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }

        Course course = courseQuery.get();

        if (course.getStudentsCPF().contains(studentCPF)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student already enrolled in course");
        }

        course.getStudentsCPF().add(studentCPF);

        return courseRepository.save(course);
    }
}
