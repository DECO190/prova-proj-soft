package br.com.insper.ai.Course;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document
public class Course {
    @Id
    private String id;

    private String name;
    private String description;
    private String teacherCPF;
    private List<String> studentsCPF;
}
