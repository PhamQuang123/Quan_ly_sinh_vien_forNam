package example.qlhs.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import example.qlhs.entity.Student;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSuccess {
    private LocalDateTime timestamp;
    private String message;
    private Integer status;
    private JwtResponse jwtResponse;
    private Student student;
    private List<Student> students;
}
