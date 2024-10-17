package example.qlhs.service.impl;

import example.qlhs.dto.StudentDTO;
import example.qlhs.entity.Personal;
import example.qlhs.entity.Student;
import example.qlhs.exception.CustomValidationException;
import example.qlhs.repository.PersonalRepository;
import example.qlhs.repository.StudentRepository;
import example.qlhs.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final PersonalRepository personalRepository;



    @Override
    public List<Student> findAll() {
        List<Student> students = studentRepository.findAll();
        return students;
    }

    @Override
    public Student findById(long id) {
        Student student = (studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found")));
        return student;
    }

    @Override
    public Student save(StudentDTO studentDTO) {
        Student student = new Student();
        student.setFullName(studentDTO.getFullName());
        student.setBirthDate(studentDTO.getBirthDate());
        student.setGender(studentDTO.getGender());
        student.setGpa(studentDTO.getGpa());

        Student savedStudent = studentRepository.save(student);

        List< Personal> personals = studentDTO.getPersonalInformations();
        for (Personal personal : personals) {

            Map<String,String> err = validatePersonal(personal.getFullName(),personal.getGender(),personal.getRelationship());

            if (err.size() == 0){
            Personal newPerson = new Personal();
            newPerson.setFullName(personal.getFullName());
            newPerson.setBirthDate(personal.getBirthDate());
            newPerson.setGender(personal.getGender());
            newPerson.setRelationship(personal.getRelationship());
            newPerson.setStudent(savedStudent);
            personalRepository.save(newPerson);
            } else {
                throw new CustomValidationException(err);
            }
        }

        return findById(savedStudent.getId());
    }

    private Map<String,String> validatePersonal(String fullName, Integer gender, Integer relationship) {
        Map<String,String> errors = new HashMap<>();
        if (fullName == null || fullName.isEmpty()) {
            errors.put("Per.fullName", "Khong duoc de trong");
        }
        if (gender == null || gender < 1 || gender > 2) {
            errors.put("Per.gender", "Chi nhan gia tri 1 or 2");
        }
        if (relationship == null || relationship < 1 || relationship > 5) {
            errors.put("Per.relationship", "Chi nhan gia tri 1 or 5");
        }
        return errors;
    }


    @Override
    public void deleteById(long id) {
        studentRepository.deleteById(id);
    }
}
