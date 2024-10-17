package example.qlhs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import example.qlhs.entity.Personal;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.InitBinder;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO {
    private Long id;

    @NotBlank(message = "Khong duoc de trong")
    private String fullName;

    @JsonFormat( pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @Min(value = 1, message = "chi nhan gia tri 1 or 2")
    @Max(value = 2, message = "chi nhan gia tri 1 or 2")
    private Integer gender;

    @Min(value = 0, message = "Chi nhan gia tri 0 - 10")
    @Max(value = 10, message = "Chi nhan gia tri 0 - 10")
    private Double gpa;
    private List<Personal> personalInformations;

}
