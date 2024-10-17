package example.qlhs.controller.StudentController;

import example.qlhs.dto.StudentDTO;
import example.qlhs.dto.response.ErrorsData;
import example.qlhs.dto.response.ResponseSuccess;
import example.qlhs.entity.Student;
import example.qlhs.exception.CustomValidationException;
import example.qlhs.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/students")
@CrossOrigin("*")
@RequiredArgsConstructor
public class StudentController {
    public final StudentService studentService;

    @PostMapping
    public ResponseEntity<ResponseSuccess> createStudent(@Valid @RequestBody StudentDTO studentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            ErrorsData errorsData = ErrorsData.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            throw new CustomValidationException(errors,errorsData);
        }
        Student student = studentService.save(studentDTO);

        ResponseSuccess response = ResponseSuccess.builder()
                .timestamp(LocalDateTime.now())
                .message("successfully created student")
                .status(HttpStatus.CREATED.value())
                .student(student)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
