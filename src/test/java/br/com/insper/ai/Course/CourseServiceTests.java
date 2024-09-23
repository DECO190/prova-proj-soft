package br.com.insper.ai.Course;
import br.com.insper.ai.Course.DTOs.UpdateCourseStudentsDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTests {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void testGetAllCourses() {
        Mockito.when(courseRepository.findAll()).thenReturn(new ArrayList<>());

        Assertions.assertEquals(0, courseService.getCourses(null).size());
    }

    @Test
    public void testGetCoursesByNameWhenNameDoesNotExist() {
        String name = "dhjashdkasjhkj32h1kjh32kj1h3kj12hkj3213hk12jh3kj21";
        Mockito.when(courseRepository.findByName(name)).thenReturn(null);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            courseService.getCourses(name);
        });
    }

    @Test
    public void testGetCoursesByNameWhenNameExists() {
        String name = "test";
        Course course = new Course();
        course.setName(name);
        course.setDescription("description of the course");
        course.setTeacherCPF("123");

        Mockito.when(courseRepository.findByName(name)).thenReturn(course);

        Assertions.assertEquals(1, courseService.getCourses(name).size());
        Assertions.assertEquals(name, courseService.getCourses(name).get(0).getName());
        Assertions.assertEquals("description of the course", courseService.getCourses(name).get(0).getDescription());
        Assertions.assertEquals("123", courseService.getCourses(name).get(0).getTeacherCPF());
    }

    @Test
    public void testCreateCourseWithInvalidCPF() {
        Course course = new Course();
        course.setName("test");
        course.setDescription("description of the course");
        course.setTeacherCPF("31232103213213123213123213129");


        Assertions.assertThrows(ResponseStatusException.class, () -> {
            courseService.createCourse(course);
        });
    }

    @Test
    public void testCreateCourseWithMissingFields() {
        Course course = new Course();
        course.setName("test");
        course.setDescription("description of the course");

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            courseService.createCourse(course);
        });
    }

    @Test
    public void testCreateCourseWithValidCPF() {
        Course course = new Course();
        course.setName("test");
        course.setDescription("description of the course");
        course.setTeacherCPF("123");

        Mockito.when(courseRepository.save(course)).thenReturn(course);

        Assertions.assertEquals(course, courseService.createCourse(course));
    }

    @Test
    public void testUpdateCourseStudentsWithInvalidCPF() {
        UpdateCourseStudentsDTO updateCourseStudentsDTO = new UpdateCourseStudentsDTO();
        updateCourseStudentsDTO.setStudentCPF("31232103213213123213123213129");

        Course course = new Course();
        course.setName("test");
        course.setDescription("description of the course");
        course.setTeacherCPF("123");

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            courseService.updateCourseStudents("1", updateCourseStudentsDTO);
        });
    }

    @Test
    public void testUpdateCourseStudentsWithValidCPF() {
        UpdateCourseStudentsDTO updateCourseStudentsDTO = new UpdateCourseStudentsDTO();
        updateCourseStudentsDTO.setStudentCPF("123");

        Course course = new Course();
        course.setName("test");
        course.setDescription("description of the course");
        course.setTeacherCPF("123");
        course.setStudentsCPF(new ArrayList<>());

        Mockito.when(courseRepository.findById("1")).thenReturn(java.util.Optional.of(course));

        Assertions.assertEquals(course, courseService.updateCourseStudents("1", updateCourseStudentsDTO));
    }
}
