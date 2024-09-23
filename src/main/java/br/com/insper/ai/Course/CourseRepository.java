package br.com.insper.ai.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    public  Course findByName(String name);
}