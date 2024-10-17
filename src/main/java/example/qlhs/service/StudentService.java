package example.qlhs.service;

import example.qlhs.dto.StudentDTO;
import example.qlhs.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> findAll();
    Student findById(long id);
    Student save(StudentDTO studentDTO);
    void deleteById(long id);
}
